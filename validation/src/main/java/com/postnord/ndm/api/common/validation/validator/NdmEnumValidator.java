package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.postnord.ndm.api.common.validation.constraints.NdmEnum.DEFAULT_MESSAGE;

@SuppressWarnings({"PMD.UseStringBufferForStringAppends"})
public class NdmEnumValidator implements ConstraintValidator<NdmEnum, String> {

    private List<String> valueList;
    private boolean nullAllowed;
    private String message = "valid values: ";

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {


        if (value == null && nullAllowed) {
            return true;
        } else {
            if (context.getDefaultConstraintMessageTemplate().equals(DEFAULT_MESSAGE)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            }
            return value != null && valueList.contains(value.toUpperCase(Locale.ENGLISH));
        }

    }

    @Override
    public void initialize(final NdmEnum constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();

        valueList = new ArrayList<>();
        final Class<? extends Enum> enumClass = constraintAnnotation.enumClass();

        final Enum[] enumValArr = enumClass.getEnumConstants();

        int i = 0;
        message += "{";
        for (final Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase(Locale.ENGLISH));
            final String delim = (++i == enumValArr.length) ? "" : ",";
            message += "'" + enumVal + "'" + delim;
        }
        message += "}";
    }
}
