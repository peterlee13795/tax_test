package com.tax.test.domain.tax;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tax.test.core.exception.LogicException;
import com.tax.test.type.StatusType;
import com.tax.test.util.MoneyUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
public record TaxDetail (
    @JsonProperty("종합소득금액")
    Long totalIncome,
    @JsonProperty("이름")
    String name,
    @JsonProperty("소득공제")
    IncomeCredit incomeCredit
) {
    public record IncomeCredit(
        @JsonProperty("국민연금")
        List<CitizenPension> citizenPensions,
        @JsonProperty("신용카드소득공제")
        CreditCardDeduction creditCardDeduction,
        @JsonProperty("세액공제")
        String taxCredit
    ) {}

    public record CitizenPension(
        @JsonProperty("월")
        String month,
        @JsonProperty("공제액")
        String deduction
    ) {}

    public record CreditCardDeduction(
        List<Map<String, String>> month,
        int year
    ) {}

    // 소득공제
    public long calculateIncomeDeduction() {

        try {
            long totalCitizenPensionDeduction = Optional.ofNullable(incomeCredit.citizenPensions).orElseGet(Collections::emptyList)
                    .stream()
                    .mapToLong(citizenPension -> MoneyUtil.toNumber(citizenPension.deduction))
                    .sum();

            long totalCreditCardDeduction = incomeCredit.creditCardDeduction.month.stream().mapToLong(creditCardMonthly -> creditCardMonthly.values().stream().mapToLong(MoneyUtil::toNumber).sum()).sum();

            return totalCitizenPensionDeduction + totalCreditCardDeduction;

        } catch (Exception e) {
            throw new LogicException(StatusType.UNKNOWN, e.getMessage());
        }
    }

    // 과세표준 => 종합소득금액 - 소득공제
    public long calculateTaxBase() {
        return totalIncome - calculateIncomeDeduction();
    }
}

