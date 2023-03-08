package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmList;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmListValidator implements ConstraintValidator<NdmList, List<String>> {

    private boolean nullAllowed;
    private boolean emptyListAllowed;

    @Override
    public void initialize(final NdmList constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
        emptyListAllowed = constraintAnnotation.emptyListAllowed();
    }

    @Override
    public boolean isValid(final List<String> object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else {
            if (object.isEmpty()) {
                return emptyListAllowed;
            } else {

                for (final String s : object) {
                    if (s == null || s.isEmpty()) {
                        return false;
                    }
                }

                return true;
            }
        }
    }
}
