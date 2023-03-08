package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmNotNullParam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmNotNullParamValidator implements ConstraintValidator<NdmNotNullParam, Object> {

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintContext) {

        //value cannot be null.
        return object != null;
    }
}
