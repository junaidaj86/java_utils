package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmCollectionAllowedStrings;

import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmCollectionAllowedStringsValidator implements ConstraintValidator<NdmCollectionAllowedStrings, Collection<String>> {

    private Set<String> values;
    private boolean nullAllowed;
    private boolean emptyAllowed;

    @Override
    public void initialize(final NdmCollectionAllowedStrings constraintAnnotation) {
        nullAllowed = constraintAnnotation.nullAllowed();
        emptyAllowed = constraintAnnotation.emptyAllowed();
        values = Set.of(constraintAnnotation.values());
    }

    @Override
    public boolean isValid(final Collection<String> collection, final ConstraintValidatorContext context) {
        if (collection == null) {
            return nullAllowed;
        }
        if (collection.isEmpty()) {
            return emptyAllowed;
        }
        return collection.stream().allMatch(value -> value != null && values.contains(value));
    }
}
