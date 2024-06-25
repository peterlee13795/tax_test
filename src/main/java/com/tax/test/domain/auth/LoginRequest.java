package com.tax.test.domain.auth;

import jakarta.validation.constraints.NotBlank;


public record LoginRequest (

	@NotBlank
	String userId,

	@NotBlank
	String password
) {}
