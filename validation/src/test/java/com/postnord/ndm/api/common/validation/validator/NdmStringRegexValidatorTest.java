package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmStringRegex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmStringRegexValidatorTest {

     static final String SIMPLE_TIME_REGEX = "\\d+[wdhm]";
    private NdmStringRegexValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmStringRegexValidator();
        @NdmStringRegex
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        cut.initialize(iotaStringRegex);
    }

    @Test
     void whenStringMatchesRegexThenItShouldBeValid() {

        assertTrue(cut.isValid("valid1", mockConstraintValidatorContext),
                "String is valid (any word) matching regex");
    }

    @Test
     void whenStringContainsColonThenItShouldNotBeValid() {

        assertFalse(cut.isValid("valid1::", mockConstraintValidatorContext),
                "String should be invalid contains (colon) ");
    }

    @Test
     void whenStringContainsSpaceThenItShouldNotBeValid() {

        assertFalse(cut.isValid("  ", mockConstraintValidatorContext),
                "String should be invalid (large space only)");
    }

    @Test
     void whenStringEndsWithSpaceThenItShouldNotBeValid() {

        assertFalse(cut.isValid("xxx  ", mockConstraintValidatorContext),
                "String should be invalid (space at end)");
    }

    @Test
     void whenStringBeginsWithSpaceThenItShouldNotBeValid() {

        assertFalse(cut.isValid("   xx", mockConstraintValidatorContext),
                "String should be invalid (space at front)");
    }

    @Test
     void whenNumberAndStringRegexAsMultipleMinutesThenItIsValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertTrue(validator.isValid("19m", mockConstraintValidatorContext),
                "String should be valid (expands)");
    }

    @Test
     void whenNumberAndStringRegexAsSingleMinutesThenItIsValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertTrue(validator.isValid("1m", mockConstraintValidatorContext),
                "String should be valid");
    }

    @Test
     void whenNumberAndStringRegexWithMinutesButNoDigitThenItIsNotValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertFalse(validator.isValid("m", mockConstraintValidatorContext),
                "String should be invalid (missing decimal)");
    }

    @Test
     void whenNumberAndStringRegexWithMultiDigitDayThenItIsValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertTrue(validator.isValid("19d", mockConstraintValidatorContext),
                "should be valid");
    }

    @Test
     void whenNumberAndStringRegexWithSingleDigitDayThenItIsValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertTrue(validator.isValid("1d", mockConstraintValidatorContext),
                "should be valid (single digit)");
    }

    @Test
     void whenNumberAndStringRegexWithDayWithoutDigitThenItIsNotValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertFalse(validator.isValid("d", mockConstraintValidatorContext),
                "String matching regex when it shouldn't");
    }

    @Test
     void whenNumberAndStringRegexWithDayWithBlankThenItIsNotValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertFalse(validator.isValid(" 1d", mockConstraintValidatorContext),
                "String should be invalid (space)");
    }

    @Test
     void whenNumberAndStringRegexWithMultiDigitHourThenItIsValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertTrue(validator.isValid("19h", mockConstraintValidatorContext),
                "String should be valid (multi digit)");
    }

    @Test
     void whenNumberAndStringRegexWithSingleDigitHourThenItIsValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertTrue(validator.isValid("1h", mockConstraintValidatorContext),
                "String should be valid (singleDigit)");
    }

    @Test
     void whenNumberAndStringRegexWithOnlyHourAndNoDigitThenItIsNotValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertFalse(validator.isValid("h", mockConstraintValidatorContext),
                "String should be invalid (only hour specified)");
    }

    @Test
     void whenNumberAndStringRegexWithHourThenItIsValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertFalse(validator.isValid(" 1h", mockConstraintValidatorContext),
                "String should be invalid (leading space)");
    }

    @Test
     void whenNumberAndStringRegexWithOtherCharThenItIsValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = SIMPLE_TIME_REGEX)
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertFalse(validator.isValid("19x", mockConstraintValidatorContext),
                "String should be invalid (invalid character)");
    }

    @Test
     void whenStringHasEmptyRegexWithFilledStringThenItIsNotValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = "")
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertFalse(validator.isValid("19h", mockConstraintValidatorContext),
                "String should be invalid (contains characters)");
    }

    @Test
     void whenStringHasEmptyRegexWithEmptyInputThenItIsValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(regex = "")
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertTrue(validator.isValid("", mockConstraintValidatorContext),
                "String should be valid (empty)");
    }

    @Test
     void whenStringIsNullAndNullIsNotAllowedThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "String is null and null is not allowed, should not be valid");
    }

    @Test
     void whenStringIsNullAndNullAllowedThenItIsConsideredValid() {
        final NdmStringRegexValidator validator = new NdmStringRegexValidator();
        @NdmStringRegex(nullAllowed = true, regex = "")
        final class SampleClass {
        }
        final NdmStringRegex iotaStringRegex = SampleClass.class.getAnnotation(NdmStringRegex.class);
        validator.initialize(iotaStringRegex);

        assertTrue(validator.isValid(null, mockConstraintValidatorContext),
                "String is null and null is allowed, String should be valid");
    }
}
