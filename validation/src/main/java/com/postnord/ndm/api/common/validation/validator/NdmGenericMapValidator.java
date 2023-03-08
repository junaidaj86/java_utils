package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmGenericMap;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmGenericMapValidator implements ConstraintValidator<NdmGenericMap, Map<?, ?>> {

    private boolean nullAllowed;
    private boolean emptyAllowed;
    private boolean nullKeysAllowed;
    private boolean nullValuesAllowed;

    @Override
    public void initialize(final NdmGenericMap constraintAnnotation) {
        nullAllowed = constraintAnnotation.nullAllowed();
        emptyAllowed = constraintAnnotation.emptyAllowed();
        nullKeysAllowed = constraintAnnotation.nullKeysAllowed();
        nullValuesAllowed = constraintAnnotation.nullValuesAllowed();
    }

    @Override
    public boolean isValid(final Map<?, ?> map, final ConstraintValidatorContext constraintContext) {
        if (map == null) {
            return nullAllowed;
        }
        if (map.isEmpty()) {
            return emptyAllowed;
        }
        if (nullKeysAllowed && nullValuesAllowed) {
            return true;
        }
        return map.entrySet().stream().noneMatch(e ->
                !nullKeysAllowed && e.getKey() == null || !nullValuesAllowed && e.getValue() == null
        );
    }
}
