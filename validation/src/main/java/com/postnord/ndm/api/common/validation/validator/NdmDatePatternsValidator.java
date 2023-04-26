package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmDatePatterns;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NdmDatePatternsValidator implements ConstraintValidator<NdmDatePatterns, String> {

    private List<String> formatList;
    private boolean nullAllowed;

    @Override
    public boolean isValid(final String date, final ConstraintValidatorContext context) {

        if (date == null) {
            return nullAllowed;
        } else if (date.isEmpty()) {
            return false;
        }

        for (final String format : formatList) {
            //if any supplied date pattern is valid allow the validation
            if (tryZonedDateTimeParse(date, format)) {
                return true;
            } else {
                if (tryLocalDateParse(date, format)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tryLocalDateParse(final String date, final String format) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
            return true;
        } catch (final DateTimeParseException e) {
            return false;
        }
    }

    private boolean tryZonedDateTimeParse(final String date, final String format) {
        try {
            ZonedDateTime.parse(date, DateTimeFormatter.ofPattern(format));
            return true;
        } catch (final DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public void initialize(final NdmDatePatterns constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();

        formatList = new ArrayList<>();
        formatList.addAll(Arrays.asList(constraintAnnotation.patterns()));
    }
}
