package com.tax.test.external.tax.client;

import com.tax.test.core.configuration.FeignConfiguration;
import com.tax.test.domain.tax.TaxDetail;
import com.tax.test.external.tax.domain.ExternalTaxResponse;
import com.tax.test.external.tax.domain.ExternalTaxScrapRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "taxClient", url = "${external.tax.host}", configuration = FeignConfiguration.class)
public interface TaxClient {

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @PostMapping("/scrap")
    ExternalTaxResponse<TaxDetail> scrapTax(@RequestHeader("X-API-KEY") String key, @RequestBody ExternalTaxScrapRequest request);
}
