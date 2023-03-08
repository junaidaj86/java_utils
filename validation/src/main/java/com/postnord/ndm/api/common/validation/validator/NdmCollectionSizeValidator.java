package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmCollectionSize;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmCollectionSizeValidator implements ConstraintValidator<NdmCollectionSize, Collection> {

    private int size;
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmCollectionSize constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();

        size = constraintAnnotation.size();
    }

    @Override
    public boolean isValid(final Collection object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            return object.size() <= size;
        }
    }
}
