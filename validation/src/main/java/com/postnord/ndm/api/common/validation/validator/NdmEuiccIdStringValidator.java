package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmEuiccIdString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmEuiccIdStringValidator implements ConstraintValidator<NdmEuiccIdString, String> {

    private static final int EID_MIN_LENGTH = 20;
    private static final int EID_MAX_RANGE_LENGTH = 30;
    private static final int EID_MAX_LENGTH = 32;
    private static final String EID_VALID_HEX_REGEX = "^[0-9a-fA-F]+$";

    private boolean nullAllowed;

    @Override
    public void initialize(final NdmEuiccIdString constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else if (isCorrectLength(object)) {
            return object.matches(EID_VALID_HEX_REGEX);
        }

        return false;
    }

    private boolean isCorrectLength(final String object) {

        return object.length() >= EID_MIN_LENGTH && object.length() <= EID_MAX_RANGE_LENGTH
                || object.length() == EID_MAX_LENGTH;
    }
}
