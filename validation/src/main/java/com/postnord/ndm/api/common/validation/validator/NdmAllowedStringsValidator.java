package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmAllowedStrings;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@SuppressWarnings({"PMD.UseStringBufferForStringAppends"})
public class NdmAllowedStringsValidator implements ConstraintValidator<NdmAllowedStrings, String> {

    private List<String> valueList;
    private String message = "valid values: ";
    private boolean nullAllowed;

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {

        if (value == null) {
            return nullAllowed;
        }

        if (NdmAllowedStrings.DEFAULT_MESSAGE.equals(context.getDefaultConstraintMessageTemplate())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }

        return valueList.contains(value.toUpperCase(Locale.ENGLISH));
    }

    @Override
    public void initialize(final NdmAllowedStrings constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();

        valueList = new ArrayList<>();

        int i = 0;
        message += "{";
        for (final String s : constraintAnnotation.values()) {
            valueList.add(s.toUpperCase(Locale.ENGLISH));
            final String delim = ++i == constraintAnnotation.values().length ? "" : ",";
            message += "'" + s + "'" + delim;
        }
        message += "}";
    }
}
