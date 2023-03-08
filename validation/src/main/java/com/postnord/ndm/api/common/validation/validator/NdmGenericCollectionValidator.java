package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmGenericCollection;

import java.util.Collection;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmGenericCollectionValidator implements ConstraintValidator<NdmGenericCollection, Collection<?>> {

    private boolean nullAllowed;
    private boolean emptyAllowed;
    private boolean nullElementsAllowed;

    @Override
    public void initialize(final NdmGenericCollection constraintAnnotation) {
        nullAllowed = constraintAnnotation.nullAllowed();
        emptyAllowed = constraintAnnotation.emptyAllowed();
        nullElementsAllowed = constraintAnnotation.nullElementsAllowed();
    }

    @Override
    public boolean isValid(final Collection<?> collection, final ConstraintValidatorContext constraintContext) {
        if (collection == null) {
            return nullAllowed;
        }
        if (collection.isEmpty()) {
            return emptyAllowed;
        }
        if (!nullElementsAllowed) {
            return collection.stream().noneMatch(Objects::isNull);
        }
        return true;
    }
}
