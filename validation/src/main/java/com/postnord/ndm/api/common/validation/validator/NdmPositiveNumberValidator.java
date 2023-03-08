package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmPositiveNumber;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmPositiveNumberValidator implements ConstraintValidator<NdmPositiveNumber, Number> {

    private boolean nullAllowed;

    @Override
    public void initialize(final NdmPositiveNumber constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
    }

    @Override
    public boolean isValid(final Number object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            // Number can be long, int, float, double, ... Converting to BigDecimal before comparing will work in all cases.
            // compareTo returns -1 if object is less than 0, 0 if object equals 0, and +1 if object is greater than 0.
            return new BigDecimal(object.toString()).compareTo(BigDecimal.ZERO) > 0;
        }
    }
}
