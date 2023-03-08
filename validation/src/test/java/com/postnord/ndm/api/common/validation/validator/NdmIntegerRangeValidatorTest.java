package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmIntegerRange;

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
 class NdmIntegerRangeValidatorTest {

    private NdmIntegerRangeValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {

        cut = new NdmIntegerRangeValidator();
        @NdmIntegerRange(min = 1, max = 10)
        final class SampleClass {
        } //add prefix to be tested for this test case
        final NdmIntegerRange iotaIntegerRange = SampleClass.class.getAnnotation(NdmIntegerRange.class);
        cut.initialize(iotaIntegerRange);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmIntegerRange(min = 1, max = 10)
        final class SampleClass {
        } //inner class hack
        final NdmIntegerRange iotaIntegerRange = SampleClass.class.getAnnotation(NdmIntegerRange.class);
        assertDoesNotThrow(() -> cut.initialize(iotaIntegerRange), "exception should not occur during initialization");
    }

    @Test
     void whenValidatorIsInitializedWithSameMinAndMaxThenAnnotationIsAccepted() {
        @NdmIntegerRange(min = 1, max = 1)
        final class SampleClass {
        } //inner class hack
        final NdmIntegerRange iotaIntegerRange = SampleClass.class.getAnnotation(NdmIntegerRange.class);
        assertDoesNotThrow(() -> cut.initialize(iotaIntegerRange), "exception should not occur during initialization");
    }

    @Test
     void whenIntegerInRangeIsEnteredThenItIsConsideredValid() {

        assertTrue(cut.isValid(2, mockConstraintValidatorContext),
                "valid integer within range entered, should be valid");
    }

    @Test
     void whenIntegerOutsideRangeIsEnteredThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(-2, mockConstraintValidatorContext),
                "negative integer outside range entered, should be not valid");
    }

    @Test
     void whenIntegerOutsideRangeFencePost1IsEnteredThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(0, mockConstraintValidatorContext),
                "fencepost integer outside range entered, should be not valid");
    }

    @Test
     void whenIntegerOutsideRangeFencePost2IsEnteredThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(11, mockConstraintValidatorContext),
                "fencepost integer outside range entered, should be not valid");
    }

    @Test
     void whenIntegerIsNullAndNullNotAllowedThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "integer range contains a null value, should be not valid");
    }

    @Test
     void whenIntegerIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmIntegerRange(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmIntegerRange iotaIntegerRange = SampleClass.class.getAnnotation(NdmIntegerRange.class);
        cut.initialize(iotaIntegerRange);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "integer range is null and null is allowed, should be valid");
    }
}
