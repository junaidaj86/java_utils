package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmSetLength;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmSetLengthValidator implements ConstraintValidator<NdmSetLength, Set> {

    private int length;
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmSetLength constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
        length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(final Set object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            return object.size() <= length;
        }
    }
}
