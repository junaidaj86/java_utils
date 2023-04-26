package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmIntegerRange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NdmIntegerRangeValidator implements ConstraintValidator<NdmIntegerRange, Integer> {

    private boolean nullAllowed;
    private int min;
    private int max;

    @Override
    public void initialize(final NdmIntegerRange constraintAnnotation) {
        nullAllowed = constraintAnnotation.nullAllowed();
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();

        checkThatMinIsLessThanMax();
    }

    private void checkThatMinIsLessThanMax() {
        if (max < min) {
            throw new IllegalStateException("max cannot be less than min");
        }
    }

    @Override
    public boolean isValid(final Integer object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            return object >= min && object <= max;
        }
    }
}
