package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmStringLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmStringLengthValidator implements ConstraintValidator<NdmStringLength, String> {

    private int length;
    private int minLength;
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmStringLength constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
        length = constraintAnnotation.length();
        minLength = constraintAnnotation.minLength();
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            return object.length() >= minLength && object.length() <= length;
        }
    }
}
