package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmStringCaseCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NdmStringCaseValidator implements ConstraintValidator<NdmStringCaseCheck, String> {

    private static final String UPPER_CASE_REGEX = "[A-Z\\s]+";
    private static final String LOWER_CASE_REGEX = "[a-z\\s]+";
    private static final String MIXED_CASE_REGEX = "^[a-zA-Z]*$";
    private boolean nullAllowed;
    private boolean emptyAllowed;
    private boolean requireLowerCase;
    private boolean requireUpperCase;

    @Override
    public void initialize(final NdmStringCaseCheck constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
        emptyAllowed = constraintAnnotation.emptyAllowed();
        requireLowerCase = constraintAnnotation.requireLowerCase();
        requireUpperCase = constraintAnnotation.requireUpperCase();
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return nullAllowed;
        }
        if (object.isBlank()) {
            return emptyAllowed;
        }
        //As our need to validate only alphabets for case check
        final String extractedStr = extractAlphabets(object);
        if (extractedStr == null) {
            return nullAllowed;
        }

        if (requireLowerCase && requireUpperCase) {
            return extractedStr.matches(MIXED_CASE_REGEX);
        } else if (requireLowerCase) {
            return extractedStr.matches(LOWER_CASE_REGEX);
        } else if (requireUpperCase) {
            return extractedStr.matches(UPPER_CASE_REGEX);
        } else {
            return false;
        }

    }

    private String extractAlphabets(final String object) {
        final StringBuilder r = new StringBuilder();
        if (object == null) {
            return null;
        } else {
            for (int j = 0; j < object.length(); j++) {
                if (Character.isLetter(object.charAt(j))) {
                    r.append(object.charAt(j));
                }
            }
            return r.toString();
        }
    }
}