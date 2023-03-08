package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmPrefixedString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmPrefixedStringValidator implements ConstraintValidator<NdmPrefixedString, String> {

    private String prefix = "";
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmPrefixedString constraintAnnotation) {
        nullAllowed = constraintAnnotation.nullAllowed();

        prefix = constraintAnnotation.prefix();
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            return object.startsWith(prefix);
        }
    }
}
