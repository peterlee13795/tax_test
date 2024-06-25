package com.tax.test.external.tax.domain;

public record ExternalTaxResponse<T> (
    String status,
    T data,
    ExternalTaxError errors
) {

}
