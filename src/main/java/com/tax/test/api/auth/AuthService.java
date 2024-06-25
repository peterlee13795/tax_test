package com.tax.test.api.auth;

import com.tax.test.core.configuration.jwt.JwtProvider;
import com.tax.test.core.configuration.jwt.MemberPrincipal;
import com.tax.test.core.exception.LogicException;
import com.tax.test.database.entity.MemberEntity;
import com.tax.test.database.repository.MemberRepository;
import com.tax.test.domain.auth.LoginRequest;
import com.tax.test.domain.auth.LoginResponse;
import com.tax.test.domain.auth.SignupRequest;
import com.tax.test.type.MemberType;
import com.tax.test.type.StatusType;
import com.tax.test.util.EncryptUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {

	private final JwtProvider jwtProvider;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	private final SignupChecker signupChecker;

	@PostConstruct
	public void init() {
		signup(new SignupRequest("lgw999", "zxcvzxcv", "관우", "681108-1582816"));
	}

	public void signup(SignupRequest request) {

		signupChecker.validateMember(request.name(), request.regNo());

		boolean exist = memberRepository.existsById(request.userId());
		if(exist) {
			throw new LogicException(StatusType.MEMBER_REGISTERED);
		}

		MemberEntity memberEntity = MemberEntity.builder()
				.userId(request.userId())
				.name(request.name())
				.password(passwordEncoder.encode(request.password()))
				.regNo(EncryptUtil.encrypt(request.regNo()))
				.memberType(MemberType.NORMAL_MEMBER)
				.build();

		memberRepository.saveAndFlush(memberEntity);
	}

	public LoginResponse login(LoginRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.userId(), request.password());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);

		String accessToken = jwtProvider.createToken(authentication);

		return new LoginResponse(accessToken);
	}

	public MemberPrincipal getMemberPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || !authentication.isAuthenticated()) return null;
		if(!(authentication instanceof UsernamePasswordAuthenticationToken)) return null;
		return (MemberPrincipal) authentication.getPrincipal();
	}
}
