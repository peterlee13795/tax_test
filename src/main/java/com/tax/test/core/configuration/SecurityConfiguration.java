package com.tax.test.core.configuration;

import com.tax.test.core.configuration.jwt.JwtCheckFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtCheckFilter jwtCheckFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);

		http
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.securityMatcher("/**")
			.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(registry -> registry
				.requestMatchers("/szs/signup", "/szs/login").permitAll()
				// .requestMatchers("/3o3/**", "/3o3/swagger.html", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
					.requestMatchers("/3o3/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll()
				.anyRequest().authenticated()
			);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
