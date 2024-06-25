package com.tax.test.core.configuration.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtCheckFilter extends GenericFilterBean {

	private final JwtProvider jwtProvider;

	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String jwt = resolveToken(req);
		String requestURI = req.getRequestURI();

		// 토큰 유효성 검사
		if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) { // 토큰에 이상이 없는 경우
			// 토큰에서 사용자명, 권한을 추출하여 스프링 시큐리티 사용자를 만들어 Authentication 반환
			Authentication authentication = jwtProvider.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.debug("Security Context에 %s 인증 정보를 저장했습니다. URI : %s", authentication.getName(), requestURI);
		} else {
			log.debug("유효한 JWT 토큰이 없습니다. URI: %s", requestURI);
		}

		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}

		return null;
	}
}
