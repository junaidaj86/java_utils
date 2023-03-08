package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmSet;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmSetValidator implements ConstraintValidator<NdmSet, Set> {

    private boolean nullAllowed;

    @Override
    public void initialize(final NdmSet constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
    }

    @Override
    public boolean isValid(final Set object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            return !object.isEmpty();
        }
    }
}
