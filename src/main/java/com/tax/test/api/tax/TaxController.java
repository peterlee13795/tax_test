package com.tax.test.api.tax;

import com.tax.test.core.configuration.jwt.MemberPrincipal;
import com.tax.test.domain.BaseResponse;
import com.tax.test.domain.tax.RefundResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class TaxController {

	private final TaxService taxService;

	@PostMapping("/scrap")
	public BaseResponse<Void> scrap(@Parameter(hidden = true) MemberPrincipal memberPrincipal) {
		taxService.scrap(memberPrincipal);
		return BaseResponse.OK;
	}

	@GetMapping("/refund")
	public BaseResponse<RefundResponse> findRefund(@Parameter(hidden = true) MemberPrincipal memberPrincipal) {
		return new BaseResponse<>(taxService.findRefund(memberPrincipal));
	}


}
