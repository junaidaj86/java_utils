package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmListLength;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// CPD-OFF
@ExtendWith(MockitoExtension.class)
 class NdmListLengthValidatorTest {

    private NdmListLengthValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        @NdmListLength(length = 5)
        final class SampleClass {
        } //inner class hack
        final NdmListLength iotaListLength = SampleClass.class.getAnnotation(NdmListLength.class);
        cut = new NdmListLengthValidator();
        cut.initialize(iotaListLength);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmListLength(length = 5)
        final class SampleClass {
        } //inner class hack
        final NdmListLength iotaListLength = SampleClass.class.getAnnotation(NdmListLength.class);
        assertDoesNotThrow(() -> cut.initialize(iotaListLength), "exception should not occur during initialization");
    }

    @Test
     void whenListIsFilledWithinBoundsThenItIsConsideredValid() {

        assertTrue(cut.isValid(Collections.singletonList("value1"), mockConstraintValidatorContext),
                "valid list is within bounds, should be valid");
    }

    @Test
     void whenListIsFilledWithinBoundsFencePostThenItIsConsideredValid() {

        assertTrue(cut.isValid(Arrays.asList("value1", "value2", "value3", "value4", "value5"),
                mockConstraintValidatorContext),
                "valid list is within bounds, should be valid");
    }

    @Test
     void whenListIsFilledOutsideBoundsThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(Arrays.asList("value1", "value2", "value3", "value4", "value5", "value6"),
                mockConstraintValidatorContext),
                "valid list is outside bounds, should be invalid");
    }

    @Test
     void whenListIsNullAndNullNotAllowedThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "valid list is null, should be invalid");
    }

    @Test
     void whenListIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmListLength(nullAllowed = true, length = 5)
        final class SampleClass {
        } //inner class hack
        final NdmListLength iotaListLength = SampleClass.class.getAnnotation(NdmListLength.class);
        cut.initialize(iotaListLength);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "list is null and null is allowed, should be valid");
    }

    @Test
     void whenListIsNotNotNullAndItIsEmptyThenItIsNotValid() {

        assertFalse(cut.isValid(Collections.emptyList(), mockConstraintValidatorContext),
                "list does not contain a value, should not be valid");
    }

    @Test
     void whenListIsNullAndNullIsNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "list is null, should not be valid");
    }

    @Test
     void whenListIsNullAndNullIsAllowedThenItIsValid() {

        @NdmListLength(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmListLength iotaListLength = SampleClass.class.getAnnotation(NdmListLength.class);
        cut.initialize(iotaListLength);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "list is null and null allowed, should be valid");
    }

    @Test
     void whenListIsEmptyAndEmptyIsNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(new ArrayList<>(), mockConstraintValidatorContext),
                "list is null, should not be valid");
    }

    @Test
     void whenListIsEmptyAndEmptyIsAllowedThenItIsValid() {

        @NdmListLength(emptyListAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmListLength iotaListLength = SampleClass.class.getAnnotation(NdmListLength.class);

        cut.initialize(iotaListLength);

        assertTrue(cut.isValid(Collections.emptyList(), mockConstraintValidatorContext),
                "list is empty but it is allowed, should be valid");
    }
}
