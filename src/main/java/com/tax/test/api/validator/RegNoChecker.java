package com.tax.test.api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegNoChecker.RegNoValidator.class)
public @interface RegNoChecker {

    String message() default "유효한 regNO가 아닙니다.";

    Class[] groups() default {};

    Class[] payload() default {};

	class RegNoValidator implements ConstraintValidator<RegNoChecker, String> {

        @Override
        public boolean isValid(String regNo, ConstraintValidatorContext constraintValidatorContext) {
			String pattern = "^\\d{6}-\\d{7}$";
            boolean matches = Pattern.compile(pattern).matcher(regNo).matches();
            if(!matches) {
                return false;
                // throw new LogicException(StatusType.INVALID_PARAMETER, "regNo는 {6자리 생년월일}-{7자리 일련번호}로 입력되어야 합니다.");
            }

            String[] split = regNo.split("-");
            String yymmdd = split[0];

            try {
                LocalDate.parse(yymmdd, DateTimeFormatter.ofPattern("yyMMdd"));
            } catch (Exception e) {
                return false;
                // throw new LogicException(StatusType.INVALID_PARAMETER, "유효한 날짜가 아닙니다.");
            }

            int gender = Integer.parseInt(split[1].substring(0, 1));
            if(gender > 4 && gender < 9) { // 18xx년생, [9,0] 19xx년생: [1,2], 20xx년생: [3,4]
                return false;
                // throw new LogicException(StatusType.INVALID_PARAMETER, "성별 유형이 맞지 않습니다.");
            }

            return true;
        }
    }
}
