package com.tax.test.core.configuration.jwt;

import com.tax.test.database.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Builder
public class MemberPrincipal implements UserDetails {

	private MemberEntity memberEntity;

	private final String userId;

	@Setter
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		if(memberEntity == null) return null;
		return memberEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // 만료 여부
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // 잠김
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // 만료
	}

	@Override
	public boolean isEnabled() {
		return true; // 활성화
	}
}
