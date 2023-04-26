package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmAllowedStrings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NdmAllowedStringsValidatorTest {

    private NdmAllowedStringsValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder mockConstraintViolationBuilder;

    @BeforeEach
    void setUp() {
        @NdmAllowedStrings(values = {"allowed", "alsoAllowed"})
        final class SampleClass {
        }
        final NdmAllowedStrings validString = SampleClass.class.getAnnotation(NdmAllowedStrings.class);
        cut = new NdmAllowedStringsValidator();
        cut.initialize(validString);
    }

    @Test
    void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmAllowedStrings(values = {"allowed1", "allowed2"})
        final class SampleClass {
        }
        final NdmAllowedStrings validString = SampleClass.class.getAnnotation(NdmAllowedStrings.class);
        assertDoesNotThrow(() -> cut.initialize(validString), "exception should not occur during initialization");
    }

    @Test
    void whenStringIsInEnumThenItIsConsideredValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmAllowedStrings.DEFAULT_MESSAGE);

        assertTrue(cut.isValid("allowed", mockConstraintValidatorContext), "string is in array, should be valid");
    }

    @Test
    void whenStringIsInEnumButDifferentCaseThenItIsConsideredValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmAllowedStrings.DEFAULT_MESSAGE);

        assertTrue(cut.isValid("aLLOwed", mockConstraintValidatorContext), "string is in array but other case, should be valid");
    }

    @Test
    void whenStringIsNotInEnumThenItIsConsideredNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmAllowedStrings.DEFAULT_MESSAGE);

        assertFalse(cut.isValid("notInEnumString", mockConstraintValidatorContext), "string is not in array, should be not valid");
    }

    @Test
    void whenStringIsFilledWithBlankThenItIsConsideredNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmAllowedStrings.DEFAULT_MESSAGE);

        assertFalse(cut.isValid("", mockConstraintValidatorContext), "string is blank, should be invalid");
    }

    @Test
    void whenStringIsNullAndNullNotAllowedThenItIsConsideredNotValid() {
        assertFalse(cut.isValid(null, mockConstraintValidatorContext), "string is null, should be invalid");
    }

    @Test
    void whenStringIsInvalidThenAHumanReadableCustomMessageIsReturned() {
        @NdmAllowedStrings(values = {"allowed3", "allowed4"}, message = "custom")
        final class SampleClass {
        }
        final NdmAllowedStrings validString = SampleClass.class.getAnnotation(NdmAllowedStrings.class);
        cut = new NdmAllowedStringsValidator();
        cut.initialize(validString);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn("custom");
        assertFalse(cut.isValid("", mockConstraintValidatorContext), "string is blank, should be invalid");
    }

    @Test
    void whenAcceptedStringsIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmAllowedStrings(nullAllowed = true, values = {"allowed5", "allowed6"}, message = "custom")
        final class SampleClass {
        }
        final NdmAllowedStrings validString = SampleClass.class.getAnnotation(NdmAllowedStrings.class);
        cut.initialize(validString);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "acceptedStrings is null and null is allowed, should be valid");
    }
}
