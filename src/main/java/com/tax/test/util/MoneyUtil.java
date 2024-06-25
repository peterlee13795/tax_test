package com.tax.test.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class MoneyUtil {

	public static long toNumber(String value) {

		try {
			value = value.replace(",", "");

			return Math.round(Double.parseDouble(value));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static String toStringWithComma(long value) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		return formatter.format(value);
	}


}
