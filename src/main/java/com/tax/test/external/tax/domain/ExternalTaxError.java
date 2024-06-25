package com.tax.test.external.tax.domain;

public record ExternalTaxError (
    String code,
    String message,
    String validations
) {}
