package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmCollectionStringRegex;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class NdmCollectionStringRegexValidator implements ConstraintValidator<NdmCollectionStringRegex, Collection<String>> {

    private String regex;
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmCollectionStringRegex constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();

        regex = constraintAnnotation.regex();
    }

    @Override
    public boolean isValid(final Collection<String> object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            return object.stream().allMatch(s -> s.matches(regex));
        }
    }
}
