package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmStringRegex;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NdmStringRegexValidator implements ConstraintValidator<NdmStringRegex, String> {

    private String regex;
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmStringRegex constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
        regex = constraintAnnotation.regex();
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            return object.matches(regex);
        }
    }
}
