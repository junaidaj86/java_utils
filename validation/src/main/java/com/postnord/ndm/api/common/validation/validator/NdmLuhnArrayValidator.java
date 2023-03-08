package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmLuhnArray;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmLuhnArrayValidator implements ConstraintValidator<NdmLuhnArray, String[]> {

    private static final char FILLER = 'F';
    private static final int TWO = 2;
    private static final int NINE = 9;
    private static final int TEN = 10;
    private static final String LUHN_REGEX = "^[0-9]+$";
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmLuhnArray constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
    }

    @Override
    public boolean isValid(final String[] luhnArray, final ConstraintValidatorContext constraintContext) {

        if (luhnArray == null) {
            return nullAllowed;
        }

        for (final String s : luhnArray) {
            if (isEmpty(s)) {
                return false;
            }

            if (!checkLuhn(strip(s))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkLuhn(final String toCheck) {

        if (!toCheck.matches(LUHN_REGEX)) {
            return false;
        }

        int sum = 0;
        boolean alternate = false;
        for (int i = toCheck.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(toCheck.substring(i, i + 1));
            if (alternate) {
                n *= TWO;
                if (n > NINE) {
                    n = n % TEN + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return sum % TEN == 0;
    }

    private String strip(final String toStrip) {
        if (null != toStrip && toStrip.length() > 0
                && toStrip.charAt(toStrip.length() - 1) == FILLER) {
            return toStrip.substring(0, toStrip.length() - 1);
        } else {
            return toStrip;
        }
    }

    private boolean isEmpty(final String toCheck) {
        return null == toCheck || toCheck.isEmpty();
    }
}
