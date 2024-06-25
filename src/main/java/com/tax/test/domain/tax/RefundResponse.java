package com.tax.test.domain.tax;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefundResponse(
    @JsonProperty("결정세액")
    String refund
) {}
