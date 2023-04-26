package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmIccIdString;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NdmIccIdStringValidator implements ConstraintValidator<NdmIccIdString, String> {

    private static final int ICCID_MIN_LENGTH = 19;
    private static final int ICCID_MAX_LENGTH = 20;
    private static final String ICCID_VALID_REGEX = "^[0-9]{" + ICCID_MIN_LENGTH + "," + ICCID_MAX_LENGTH + "}+$";
    private String missingIccIdViolationMessage = "";
    private String defaultViolationMessage = "";
    private boolean nullAllowed;

    @Override
    public void initialize(final NdmIccIdString constraintAnnotation) {

        nullAllowed = constraintAnnotation.nullAllowed();

        final String expectedFieldName = constraintAnnotation.fieldName();

        missingIccIdViolationMessage = "Expected {" + expectedFieldName + "}.'" +
                expectedFieldName + "' is missing.";

        defaultViolationMessage = NdmIccIdString.DEFAULT_MESSAGE.replace("iccId", expectedFieldName);

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {

        constraintContext.disableDefaultConstraintViolation();

        if (object == null) {
            if (!nullAllowed) {
                constraintContext.buildConstraintViolationWithTemplate(missingIccIdViolationMessage)
                        .addConstraintViolation();
            }

            return nullAllowed;
        } else if (object.matches(ICCID_VALID_REGEX) && isLuhnString(object, constraintContext)) {
            return true;
        } else {
            constraintContext.buildConstraintViolationWithTemplate(defaultViolationMessage)
                    .addConstraintViolation();
        }

        return false;
    }

    private boolean isLuhnString(final String object, final ConstraintValidatorContext constraintContext) {
        return new NdmLuhnStringValidator().isValid(object, constraintContext);
    }
}
