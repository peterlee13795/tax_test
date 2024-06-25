package com.tax.test.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public enum TaxAmountType {

	LESS_THAN_1400(taxBase -> Math.round(taxBase * 0.06)), // < 1400만, 과세표준의 6%
	LESS_THAN_5000(taxBase -> Math.round((14_000_000 - taxBase) * 0.15) + 840_000), // 1400만~5000만, 84만원 + (1,400만원 초과금액의 15%)
	LESS_THAN_8800(taxBase -> Math.round((50_000_000 - taxBase) * 0.24) + 6_240_000), // 5000만~8800만, 624만원 + (5,000만원 초과금액의 24%)
	LESS_THAN_15000(taxBase -> Math.round((88_000_000 - taxBase) * 0.35) + 15_360_000), // 8800만~15000만, 1,536만원 + (8,800만원 초과금액의 35%)
	LESS_THAN_30000(taxBase -> Math.round((150_000_000 - taxBase) * 0.38) + 37_060_000), // 15000만~30000만, 3,706만원 + (1억5천만원 초과금액의 38%)
	LESS_THAN_50000(taxBase -> Math.round((300_000_000 - taxBase) * 0.40) + 94_060_000), // 30000만~50000만, 9,406만원 + (3억원 초과금액의 40%)
	LESS_THAN_100000(taxBase -> Math.round((500_000_000 - taxBase) * 0.42) + 174_060_000), // 50000만~100000만, 17,406만원 + (5억원 초과금액의 42%)
	BIGGER_THAN_100000(taxBase -> Math.round((1_000_000_000 - taxBase) * 0.45) + 384_060_000) // > 100000만, 38,406만원 + (10억원 초과금액의 45%)
	;

	private final Function<Long, Long> calculator;


	public static TaxAmountType findCalculator(long taxBase) {
		if(taxBase < 14_000_000) return LESS_THAN_1400;
		if(taxBase < 50_000_000) return LESS_THAN_5000;
		if(taxBase < 88_000_000) return LESS_THAN_8800;
		if(taxBase < 150_000_000) return LESS_THAN_15000;
		if(taxBase < 300_000_000) return LESS_THAN_30000;
		if(taxBase < 500_000_000) return LESS_THAN_50000;
		if(taxBase < 1000_000_000) return LESS_THAN_100000;
		else return BIGGER_THAN_100000;
	}

	public static long calculateDeterminedTaxAmount(long taxBase) {
		return  findCalculator(taxBase).calculator.apply(taxBase);
	}

}
