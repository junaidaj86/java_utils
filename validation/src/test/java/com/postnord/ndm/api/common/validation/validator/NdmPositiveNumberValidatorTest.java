package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmPositiveNumber;

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
 class NdmPositiveNumberValidatorTest {

    private NdmPositiveNumberValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmPositiveNumberValidator();
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmPositiveNumber
        final class SampleClass {
        } //inner class hack
        final NdmPositiveNumber iotaPositiveNumber = SampleClass.class.getAnnotation(NdmPositiveNumber.class);
        assertDoesNotThrow(() -> cut.initialize(iotaPositiveNumber), "exception should not occur during initialization");
    }

    @Test
     void whenPositiveIntegerIsEnteredThenItIsConsideredValid() {

        assertTrue(cut.isValid(1, mockConstraintValidatorContext),
                "valid positive number contains a positive integer value, should be valid");
    }

    @Test
     void whenPositiveLongIsEnteredThenItIsConsideredValid() {

        assertTrue(cut.isValid(Long.MAX_VALUE, mockConstraintValidatorContext),
                "valid positive number contains a positive long value, should be valid");
    }

    @Test
     void whenPositiveFloatIsEnteredThenItIsConsideredValid() {

        assertTrue(cut.isValid(4.6, mockConstraintValidatorContext),
                "valid positive number contains a positive float value, should be valid");
    }

    @Test
     void whenPositiveDoubleIsEnteredThenItIsConsideredValid() {

        assertTrue(cut.isValid(0.1D, mockConstraintValidatorContext),
                "valid positive number contains a positive double value, should be valid");
    }

    @Test
     void whenNegativeIntegerIsEnteredThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(-2, mockConstraintValidatorContext),
                "valid positive number contains a negative integer value, should be not valid");
    }

    @Test
     void whenZeroLongIsEnteredThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(0L, mockConstraintValidatorContext),
                "valid positive number contains a negative long value, should be not valid");
    }

    @Test
     void whenNegativeFloatIsEnteredThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(-0.1, mockConstraintValidatorContext),
                "valid positive number contains a negative float value, should be not valid");
    }

    @Test
     void whenZeroDoubleIsEnteredThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(0.0D, mockConstraintValidatorContext),
                "valid positive number contains a negative double value, should be not valid");
    }

    @Test
     void whenPositiveNumberIsNullAndNullNotAllowedThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "valid positive number contains a null value, should be not valid");
    }

    @Test
     void whenPositiveNumberIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmPositiveNumber(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmPositiveNumber iotaPositiveNumber = SampleClass.class.getAnnotation(NdmPositiveNumber.class);
        cut.initialize(iotaPositiveNumber);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "positive number is null and null is allowed, should be valid");
    }
}
