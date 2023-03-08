package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmCollectionStringLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class NdmCollectionStringLengthValidator implements ConstraintValidator<NdmCollectionStringLength, Collection<String>> {

    private int length;
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmCollectionStringLength constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();

        length = constraintAnnotation.length();
    }

    @Override
    public boolean isValid(final Collection<String> object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            return object.stream().allMatch(s -> s != null && s.length() > 0 && s.length() <= length);
        }
    }
}
