package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmDynamicList;

import jakarta.validation.ConstraintValidator;

public abstract class NdmDynamicListValidator implements ConstraintValidator<NdmDynamicList, String> {

    protected abstract boolean validate(String value);

}
