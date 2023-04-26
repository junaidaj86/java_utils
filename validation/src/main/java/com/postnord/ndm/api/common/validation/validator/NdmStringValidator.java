package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmString;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NdmStringValidator implements ConstraintValidator<NdmString, String> {

    private boolean nullAllowed;
    private boolean emptyAllowed;

    @Override
    public void initialize(final NdmString constraintAnnotation) {
        nullAllowed = constraintAnnotation.nullAllowed();
        emptyAllowed = constraintAnnotation.emptyAllowed();
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        }
        if (object.length() == 0) {
            return emptyAllowed;
        }
        return true;
    }
}
