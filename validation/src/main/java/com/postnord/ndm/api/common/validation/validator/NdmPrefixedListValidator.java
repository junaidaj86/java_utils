package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmPrefixedList;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmPrefixedListValidator implements ConstraintValidator<NdmPrefixedList, List<String>> {

    private String prefix = "";
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmPrefixedList constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
        prefix = constraintAnnotation.prefix();
    }

    @Override
    public boolean isValid(final List<String> object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else if (object.isEmpty()) {
            return false;
        } else {
            //all addresses must be prefixed with tel:
            for (final String address : object) {
                if (!address.startsWith(prefix)) {
                    return false;
                }
            }

            return true;
        }
    }
}
