package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmPhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates that phone number is a valid international or national phone number according to ITU-T E.123 and E.164. By default phone number
 * is validated that it is either in international or in national notation. When requireInternationalFormat is set to true then it's
 * validated that the phone number is in international phone number format.
 *
 * @see <a href="https://howtodoinjava.com/java/regex/java-regex-validate-international-phone-numbers/">Java regex to validate international
 * phone numbers</a>
 * @see <a href="https://codingnconcepts.com/java/java-regex-to-validate-phone-number/">Java Regex to validate phone number</a>
 */
public class NdmPhoneNumberValidator implements ConstraintValidator<NdmPhoneNumber, String> {

    private static final String PHONE_NUMBER_INTERNATIONAL_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    private static final String PHONE_NUMBER_NATIONAL_REGEX = "^((\\(\\d{1,4}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{3,4}$";
    private static final String PHONE_NUMBER_COMBINED_REGEX = PHONE_NUMBER_INTERNATIONAL_REGEX + "|" + PHONE_NUMBER_NATIONAL_REGEX;
    private boolean nullAllowed;
    private boolean requireInternationalFormat;

    @Override
    public void initialize(final NdmPhoneNumber constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();
        requireInternationalFormat = constraintAnnotation.requireInternationalFormat();
    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return nullAllowed;
        }

        if (requireInternationalFormat) {
            return object.matches(PHONE_NUMBER_INTERNATIONAL_REGEX);
        }

        return object.matches(PHONE_NUMBER_COMBINED_REGEX);
    }
}