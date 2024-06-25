package com.tax.test.domain.auth;

import com.tax.test.api.validator.RegNoChecker;
import jakarta.validation.constraints.NotBlank;


public record SignupRequest (
	@NotBlank
	String userId,

	@NotBlank
	String password,

	@NotBlank
	String name,

	@NotBlank @RegNoChecker
	String regNo
) {}
