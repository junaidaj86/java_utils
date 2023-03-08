package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmEuiccIdString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmEuiccIdStringValidatorTest {

    private NdmEuiccIdStringValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmEuiccIdStringValidator();
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmEuiccIdString
        final class SampleClass {
        } //inner class hack
        final NdmEuiccIdString iotaEUICCIDString = SampleClass.class.getAnnotation(NdmEuiccIdString.class);
        assertDoesNotThrow(() -> cut.initialize(iotaEUICCIDString), "exception should not occur during initialization");
    }

    @Test
     void whenEIDIsFilledWith20HEXThenItIsConsideredValid() {

        assertTrue(cut.isValid("123456789012345ABCDE", mockConstraintValidatorContext),
                "mandatory eid contains 20 HEX characters, should be valid");
    }

    @Test
     void whenEIDIsFilledWith30HEXThenItIsConsideredValid() {

        assertTrue(cut.isValid("1234567890123456789012345ABCDE", mockConstraintValidatorContext),
                "mandatory eid contains 30 HEX characters, should be valid");
    }

    @Test
     void whenEIDIsFilledWith32HEXThenItIsConsideredValid() {

        assertTrue(cut.isValid("12345678901234567890123456789012", mockConstraintValidatorContext),
                "mandatory eid contains 32 HEX characters, should be valid");
    }

    @Test
     void whenEIDIsFilledWith31HEXThenItIsConsideredNotValid() {

        assertFalse(cut.isValid("1234567890123456789012345678901", mockConstraintValidatorContext),
                "mandatory eid contains 31 HEX characters, should be invalid");
    }

    @Test
     void whenEIDIsFilledWithNonHEXThenItIsConsideredNotValid() {

        assertFalse(cut.isValid("12345678901234567890GHI", mockConstraintValidatorContext),
                "mandatory eid contains 31 HEX characters, should be invalid");
    }

    @Test
     void whenEIDIsTooSmallThenItIsConsideredNotValid() {

        assertFalse(cut.isValid("1234567890123456789", mockConstraintValidatorContext),
                "mandatory eid contains 19 (too small) HEX characters, should be invalid");
    }

    @Test
     void whenEIDIsTooLargeThenItIsConsideredNotValid() {

        assertFalse(cut.isValid("123456789012345678901234567890123", mockConstraintValidatorContext),
                "mandatory eid contains 33 (too large) HEX characters, should be invalid");
    }


    @Test
     void whenEIDIsFilledWithBlankValueThenItIsNotValid() {

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "mandatory eid is blank, should be invalid");
    }

    @Test
     void whenEIDIsNullAndNullNotAllowedThenTheValueIsNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "mandatory eid is null, should be invalid");
    }

    @Test
     void whenEIDIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmEuiccIdString(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmEuiccIdString iotaEUICCIDString = SampleClass.class.getAnnotation(NdmEuiccIdString.class);
        cut.initialize(iotaEUICCIDString);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "eid is null and null is allowed, should be valid");
    }

}
