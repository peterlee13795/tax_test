package com.tax.test.api.auth;

import com.tax.test.domain.BaseResponse;
import com.tax.test.domain.auth.LoginRequest;
import com.tax.test.domain.auth.LoginResponse;
import com.tax.test.domain.auth.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public BaseResponse<Void> signup(@Valid @RequestBody SignupRequest request) {
		authService.signup(request);
		return BaseResponse.OK;
	}

	@PostMapping("/login")
	public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		return new BaseResponse<>(authService.login(request));
	}

}
