package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmLuhnString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmLuhnStringValidator implements ConstraintValidator<NdmLuhnString, String> {

    private static final char FILLER = 'F';
    private static final int TWO = 2;
    private static final int NINE = 9;
    private static final int TEN = 10;
    private static final String LUHN_REGEX = "^[0-9]+$";
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmLuhnString constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else if (object.isEmpty()) {
            return false;
        } else {
            return checkLuhn(strip(object));
        }
    }

    private boolean checkLuhn(final String toCheck) {

        if (!toCheck.matches(LUHN_REGEX)) {
            return false;
        }

        return isLuhnStringValidAccordingToCheckDigit(toCheck);
    }

    private boolean isLuhnStringValidAccordingToCheckDigit(final String toCheck) {
        int sum = 0;
        boolean flip = false;
        for (int i = toCheck.length() - 1; i >= 0; i--) {
            int x = Integer.parseInt(toCheck.substring(i, i + 1));
            if (flip) {
                x *= TWO;
                if (x > NINE) {
                    x = x % TEN + 1;
                }
            }
            sum += x;
            flip = !flip;
        }

        return sum % TEN == 0;
    }

    private String strip(final String toStrip) {
        if (null != toStrip && toStrip.length() > 0 && toStrip.charAt(toStrip.length() - 1) == FILLER) {
            return toStrip.substring(0, toStrip.length() - 1);
        } else {
            return toStrip;
        }
    }
}
