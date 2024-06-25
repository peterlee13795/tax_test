package com.tax.test.api.tax;

import com.tax.test.api.member.MemberService;
import com.tax.test.core.configuration.jwt.MemberPrincipal;
import com.tax.test.core.exception.LogicException;
import com.tax.test.database.entity.MemberEntity;
import com.tax.test.database.entity.TaxScrapEntity;
import com.tax.test.database.repository.TaxScrapRepository;
import com.tax.test.domain.tax.RefundResponse;
import com.tax.test.domain.tax.TaxDetail;
import com.tax.test.external.tax.client.TaxClient;
import com.tax.test.external.tax.domain.ExternalTaxResponse;
import com.tax.test.external.tax.domain.ExternalTaxScrapRequest;
import com.tax.test.type.StatusType;
import com.tax.test.type.TaxAmountType;
import com.tax.test.util.EncryptUtil;
import com.tax.test.util.JsonUtil;
import com.tax.test.util.MoneyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxService {

	private final TaxClient taxClient;

	private final TaxScrapRepository taxScrapRepository;

	private final MemberService memberService;

	@Value("${external.tax.x-api-key}")
	private String clientKey;

	public void scrap(MemberPrincipal memberPrincipal) {
		MemberEntity member = this.memberService.findMemberBy(memberPrincipal.getUserId());
		ExternalTaxScrapRequest externalRequest = new ExternalTaxScrapRequest(member.getName(), EncryptUtil.decrypt(member.getRegNo()));
		ExternalTaxResponse<TaxDetail> result = taxClient.scrapTax(clientKey, externalRequest);
		if(!"success".equals(result.status())) {
			throw new LogicException(StatusType.EXTERNAL_API_ERROR);
		}

		String json = JsonUtil.objectToJson(result.data());

		taxScrapRepository.saveAndFlush(new TaxScrapEntity(member.getUserId(), json));
	}

	public RefundResponse findRefund(MemberPrincipal memberPrincipal) {
		TaxScrapEntity scrapEntity = taxScrapRepository.findById(memberPrincipal.getUserId()).orElseThrow(() -> new LogicException(StatusType.DATABASE_ENTITY_NOT_FOUND, "scrap required"));
		TaxDetail result = JsonUtil.jsonToObject(scrapEntity.getJson(), TaxDetail.class);

		long taxBase = result.calculateTaxBase();

		long taxAmount = TaxAmountType.calculateDeterminedTaxAmount(taxBase);

		return new RefundResponse(MoneyUtil.toStringWithComma(taxAmount));
	}
}
