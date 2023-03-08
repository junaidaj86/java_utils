package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmArrayLength;

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
 class NdmArrayLengthValidatorTest {

    private NdmArrayLengthValidator cut;

    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        @NdmArrayLength(max = 5, min = 1)
        final class SampleClass {
        } //inner class hack
        final NdmArrayLength iotaArrayLength = SampleClass.class.getAnnotation(NdmArrayLength.class);
        cut = new NdmArrayLengthValidator();
        cut.initialize(iotaArrayLength);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmArrayLength(max = 5, min = 1)
        final class SampleClass {
        } //inner class hack
        final NdmArrayLength listLength = SampleClass.class.getAnnotation(NdmArrayLength.class);
        assertDoesNotThrow(() -> cut.initialize(listLength), "exception should not occur during initialization");
    }

    @Test
     void whenArrayIsFilledWithinBoundsThenItIsConsideredValid() {

        assertTrue(cut.isValid(new String[]{"value1"}, mockConstraintValidatorContext),
                "valid list is within bounds, should be valid");
    }

    @Test
     void whenArrayIsFilledBelowBoundsThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(new String[]{}, mockConstraintValidatorContext),
                "valid list is below bounds, should be invalid");
    }

    @Test
     void whenArrayIsFilledWithinBoundsFencePostThenItIsConsideredValid() {

        assertTrue(cut.isValid(new String[]{"value1", "value2", "value3", "value4", "value5"},
                mockConstraintValidatorContext),
                "valid list is within bounds, should be valid");
    }

    @Test
     void whenArrayIsFilledOutsideBoundsThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(new String[]{"value1", "value2", "value3", "value4", "value5", "value6"},
                mockConstraintValidatorContext),
                "valid list is outside bounds, should be invalid");
    }

    @Test
     void whenArrayIsFilledWithNullThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "valid list is null, should be invalid");
    }

    @Test
     void whenArrayIsMaximumSizeWithDefaultBoundsThenItIsConsideredValid() {
        @NdmArrayLength
        final class SampleClass {
        } //inner class hack
        final NdmArrayLength listLength = SampleClass.class.getAnnotation(NdmArrayLength.class);
        cut.initialize(listLength);

        final String[] arr = new String[100];
        assertTrue(cut.isValid(arr, mockConstraintValidatorContext),
                "valid list is on bound, should be valid");
    }

    @Test
     void whenArrayIsMinimumSizeWithDefaultBoundsThenItIsConsideredValid() {
        @NdmArrayLength
        final class SampleClass {
        } //inner class hack
        final NdmArrayLength listLength = SampleClass.class.getAnnotation(NdmArrayLength.class);
        cut.initialize(listLength);

        assertTrue(cut.isValid(new String[]{}, mockConstraintValidatorContext),
                "valid list is on bound, should be valid");
    }

    @Test
     void whenArrayIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmArrayLength(max = 5, min = 1, nullAllowed = true)
        final class SampleClass {
        }
        final NdmArrayLength listLength = SampleClass.class.getAnnotation(NdmArrayLength.class);
        cut.initialize(listLength);
        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "array is null and null is allowed, should be valid");
    }
}
