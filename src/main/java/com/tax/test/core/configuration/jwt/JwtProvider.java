package com.tax.test.core.configuration.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider {

	private static final String AUTHORITIES_KEY = "auth";

	private final Key key;

	@Value("${jwt.expiration}")
	private Long expiration;

	@Autowired
	private MemberDetailServiceImpl memberDetailService;

	public JwtProvider(@Value("${jwt.secret}") String secret) {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity = new Date(now + this.expiration * 1000);
		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.HS512) // HMAC + SHA512
				.setExpiration(validity)
				.compact();
	}

	/**
	 * 토큰을 받아 클레임을 생성
	 * 클레임에서 권한 정보를 가져와서 시큐리티 UserDetails 객체를 만들고
	 * Authentication 객체 반환
	 *
	 * @param token
	 * @return
	 */
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token)
				.getBody();

		List<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());

		MemberPrincipal memberPrincipal = (MemberPrincipal)memberDetailService.loadUserByUsername(claims.getSubject());
		memberPrincipal.setAuthorities(authorities);

		return new UsernamePasswordAuthenticationToken(memberPrincipal, token, authorities);
	}

	/**
	 * 토큰 유효성 체크
	 *
	 * @param token
	 * @return
	 */
	public boolean validateToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("wrong JWT clame");
		} catch (ExpiredJwtException e) {
			log.info("expired JWT token.");
		} catch (UnsupportedJwtException e) {
			log.info("not supported JWT.");
		} catch (IllegalArgumentException e) {
			log.info("wrong JWT Token");
			e.printStackTrace();
		}

		return false;
	}

}
