package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmGenericList;

import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmGenericListValidator implements ConstraintValidator<NdmGenericList, List<?>> {

    private boolean nullAllowed;
    private boolean emptyListAllowed;
    private boolean nullElementsAllowed;

    @Override
    public void initialize(final NdmGenericList constraintAnnotation) {
        nullAllowed = constraintAnnotation.nullAllowed();
        emptyListAllowed = constraintAnnotation.emptyListAllowed();
        nullElementsAllowed = constraintAnnotation.nullElementsAllowed();
    }

    @Override
    public boolean isValid(final List<?> object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return nullAllowed;
        }
        if (object.isEmpty()) {
            return emptyListAllowed;
        }
        if (!nullElementsAllowed) {
            return object.stream().noneMatch(Objects::isNull);
        }
        return true;
    }
}
