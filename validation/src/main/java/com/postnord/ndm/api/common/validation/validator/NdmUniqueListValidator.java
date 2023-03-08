package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmUniqueList;

import java.util.HashSet;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmUniqueListValidator implements ConstraintValidator<NdmUniqueList, List<String>> {

    private boolean nullAllowed;

    @Override
    public void initialize(final NdmUniqueList constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
    }

    @Override
    public boolean isValid(final List<String> object, final ConstraintValidatorContext constraintContext) {

        //detect duplicate in array by comparing size of IOTList and IOTSet since IOTSet doesn't allow
        // duplicates, size of the set will be less for an array which contains duplicates

        if (object == null) {
            return nullAllowed;
        } else {
            return new HashSet<>(object).size() >= object.size();
        }
    }
}
