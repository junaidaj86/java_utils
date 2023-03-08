package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmListLength;

import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmListLengthValidator implements ConstraintValidator<NdmListLength, List<?>> {

    private int length;
    private boolean nullAllowed;
    private boolean emptyListAllowed;
    private boolean nullElementsAllowed;

    @Override
    public void initialize(final NdmListLength constraintAnnotation) {

        length = constraintAnnotation.length();
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
        if (!nullElementsAllowed && object.stream().anyMatch(Objects::isNull)) {
            return false;
        }
        return object.size() <= length;
    }
}
