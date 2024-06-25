package com.tax.test.core.configuration.jwt;

import com.tax.test.core.exception.LogicException;
import com.tax.test.database.entity.MemberEntity;
import com.tax.test.database.repository.MemberRepository;
import com.tax.test.type.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberDetailServiceImpl implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberEntity memberEntity = memberRepository.findById(username)
				.orElseThrow(() -> new LogicException(StatusType.MEMBER_NOT_FOUND));

		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(memberEntity.getMemberType().name()));

		return MemberPrincipal.builder()
				.memberEntity(memberEntity)
				.authorities(authorities)
				.userId(memberEntity.getUserId())
				.build();
	}
}
