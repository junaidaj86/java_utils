package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmDatePatterns;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmDatePatternsValidatorTest {

    private NdmDatePatternsValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        @NdmDatePatterns(patterns = {"yyyy-MM-dd'T'HH:mm:ss.SSSz", "yyyy-MM-dd"})
        final class SampleClass {
        }
        final NdmDatePatterns iotaDatePatterns = SampleClass.class.getAnnotation(NdmDatePatterns.class);
        cut = new NdmDatePatternsValidator();
        cut.initialize(iotaDatePatterns);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmDatePatterns(patterns = {"yyyy-MM-dd'T'HH:mm:ss.SSSz", "yyyy-MM-dd"})
        final class SampleClass {
        }
        final NdmDatePatterns validString = SampleClass.class.getAnnotation(NdmDatePatterns.class);
        assertDoesNotThrow(() -> cut.initialize(validString), "exception should not occur during initialization");
    }

    @Test
     void whenDatePatternIsValidThenItShouldBeValid() {

        assertTrue(cut.isValid("2017-10-12", mockConstraintValidatorContext),
                "short form date pattern is in array, should be valid");
    }

    @Test
     void whenDatePatternWithTimeIsValidThenItShouldBeValid() {

        assertTrue(cut.isValid("2017-10-12T00:00:00.000Z", mockConstraintValidatorContext),
                "logn form date pattern is in array, should be valid");
    }

    @Test
     void whenDatePatternIsNotInListThenItShouldNotBeValid() {

        assertFalse(cut.isValid("2015-12-14 12:05:22.000Z", mockConstraintValidatorContext),
                "pattern is not in array (non ISO), should be invalid");

        assertFalse(cut.isValid("2011-12-20 12:05", mockConstraintValidatorContext),
                "pattern is not in array (no millis), should be invalid");

        assertFalse(cut.isValid("2017/12/20T12:05:20.000", mockConstraintValidatorContext),
                "pattern is not in array (no dashes long), should be invalid");

        assertFalse(cut.isValid("2017/12/15", mockConstraintValidatorContext),
                "pattern is not in array (no dashes short), should be invalid");

        assertFalse(cut.isValid("14-OKT-2017", mockConstraintValidatorContext),
                "pattern is not in array (bad month), should be invalid");

        assertFalse(cut.isValid("14-12-2017", mockConstraintValidatorContext),
                "pattern is not in array (wrong format), should be invalid");
    }

    @Test
     void whenNotADateThenItShouldNotBeValid() {
        assertFalse(cut.isValid("yyyy", mockConstraintValidatorContext),
                "pattern is not in array (garbage), should be invalid");
    }

    @Test
     void whenDatePatternTimeIsEmptyThenItShouldNotBeValid() {

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "pattern is not in array (empty), should be invalid");

        assertFalse(cut.isValid(" ", mockConstraintValidatorContext),
                "pattern is not in array (empty with space), should be invalid");
    }

    @Test
     void whenDatePatternIsBlankThenItShouldNotBeValid() {

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "string is blank (blank), should be invalid");
    }

    @Test
     void whenDatePatternIsNullAndNullNotAllowedThenItShouldNotBeValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "string is null, should be invalid");
    }

    @Test
     void whenStringIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmDatePatterns(nullAllowed = true, patterns = {"yyyy-MM-dd'T'HH:mm:ss.SSSz", "yyyy-MM-dd"})
        final class SampleClass {
        }
        final NdmDatePatterns validString = SampleClass.class.getAnnotation(NdmDatePatterns.class);
        cut = new NdmDatePatternsValidator();
        cut.initialize(validString);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "date patterns is null and null is allowed, should be valid");
    }
}
