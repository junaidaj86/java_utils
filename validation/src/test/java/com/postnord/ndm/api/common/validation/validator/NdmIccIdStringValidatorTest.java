package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmIccIdString;

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
 class NdmIccIdStringValidatorTest {

    private NdmIccIdStringValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder mockConstraintViolationBuilder;

    @BeforeEach
     void setUp() {

        cut = new NdmIccIdStringValidator();
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmIccIdString
        final class SampleClass {
        } //inner class hack
        final NdmIccIdString iotaIccIdString = SampleClass.class.getAnnotation(NdmIccIdString.class);
        assertDoesNotThrow(() -> cut.initialize(iotaIccIdString), "exception should not occur during initialization");
    }

    @Test
     void whenLuhnIsCorrectFromAustraliaVodafoneThenItIsConsideredValid() {

        assertTrue(cut.isValid("89610300000913467525", mockConstraintValidatorContext),
                "IOTString is valid iccid from Australia - Vodafone, should be considered valid");
    }

    @Test
     void whenLuhnIsCorrectFromAustriaTMobileThenItIsConsideredValid() {

        assertTrue(cut.isValid("89430300000482738077", mockConstraintValidatorContext),
                "IOTString is valid iccid from Austria - T-Mobile, should be considered valid");
    }

    @Test
     void whenICCIDIsFilledWithNonDigitsThenItIsConsideredNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);

        assertFalse(cut.isValid("1234567890123456789A", mockConstraintValidatorContext),
                "mandatory iccid contains non digit characters, should be invalid");
    }

    @Test
     void whenICCIDIsTooSmallThenItIsConsideredNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);

        assertFalse(cut.isValid("123456789012345678", mockConstraintValidatorContext),
                "mandatory iccid contains 18 (too small) digits, should be invalid");
    }

    @Test
     void whenICCIDIsTooLargeThenItIsConsideredNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);

        assertFalse(cut.isValid("123456789012345678901", mockConstraintValidatorContext),
                "mandatory iccid contains 21 (too large) digits, should be invalid");
    }


    @Test
     void whenICCIDIsFilledWithBlankValueThenItIsNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "mandatory iccid is blank, should be invalid");
    }

    @Test
     void whenICCIDIsNullAndNullNotAllowedThenTheValueIsNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "mandatory iccid is null, should be invalid");
    }

    @Test
     void whenICCIDIsFilledInvalidLuhnStringThenItIsConsideredNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);

        assertFalse(cut.isValid("1234567890123456789", mockConstraintValidatorContext),
                "mandatory iccid in invalid luhnString, should be valid");
    }

    @Test
     void whenICCIDIsInvalidThenHumanReadableCustomMessageIsReturned() {
        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        @NdmIccIdString(message = "custom")
        final class SampleClass {
        } //inner class hack
        final NdmIccIdString iotaIccIdString = SampleClass.class.getAnnotation(NdmIccIdString.class);
        cut = new NdmIccIdStringValidator();
        cut.initialize(iotaIccIdString);

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "string is blank, should be invalid");
    }

    @Test
     void whenICCIDIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmIccIdString(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmIccIdString iotaIccIdString = SampleClass.class.getAnnotation(NdmIccIdString.class);

        cut.initialize(iotaIccIdString);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "iccid is null and null is allowed, should be valid");
    }
}
