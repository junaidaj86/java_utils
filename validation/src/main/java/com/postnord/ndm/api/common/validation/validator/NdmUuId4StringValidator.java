package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmUuId4String;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmUuId4StringValidator implements ConstraintValidator<NdmUuId4String, String> {

    private static final String UUID_REGEX =
            "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$";

    private boolean nullAllowed;

    @Override
    public void initialize(final NdmUuId4String constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        } else if (object.isEmpty()) {
            return false;
        } else {
            return checkUUID(object);
        }
    }

    private boolean checkUUID(final String toCheck) {

        return toCheck.matches(UUID_REGEX);
    }
}
