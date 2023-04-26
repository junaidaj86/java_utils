package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmArrayLength;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NdmArrayLengthValidator implements ConstraintValidator<NdmArrayLength, Object[]> {

    private int min;
    private int max;
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmArrayLength constraintAnnotation) {
        nullAllowed = constraintAnnotation.nullAllowed();

        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(final Object[] arr, final ConstraintValidatorContext constraintContext) {

        if (arr == null) {
            return nullAllowed;
        } else {
            return arr.length <= max && arr.length >= min;
        }
    }

}
