package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmSetLength;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmSetLengthValidatorTest {

    private NdmSetLengthValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        @NdmSetLength(length = 5)
        final class SampleClass {
        } //inner class hack
        final NdmSetLength iotaSetLength = SampleClass.class.getAnnotation(NdmSetLength.class);
        cut = new NdmSetLengthValidator();
        cut.initialize(iotaSetLength);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmSetLength(length = 5)
        final class SampleClass {
        } //inner class hack
        final NdmSetLength iotaSetLength = SampleClass.class.getAnnotation(NdmSetLength.class);
        assertDoesNotThrow(() -> cut.initialize(iotaSetLength), "exception should not occur during initialization");
    }

    @Test
     void whenSetIsFilledWithinBoundsThenItIsConsideredValid() {

        assertTrue(cut.isValid(Stream.of("value1").collect(Collectors.toSet()), mockConstraintValidatorContext),
                "valid set is within bounds, should be valid");
    }

    @Test
     void whenSetIsFilledWithinBoundsFencePostThenItIsConsideredValid() {

        assertTrue(cut.isValid(Stream.of("value1", "value2", "value3", "value4", "value5").collect(Collectors.toSet()),
                mockConstraintValidatorContext),
                "valid set is within bounds, should be valid");
    }

    @Test
     void whenSetIsFilledOutsideBoundsThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(Stream.of("value1", "value2", "value3", "value4", "value5", "value6").collect(Collectors.toSet()),
                mockConstraintValidatorContext),
                "valid set is outside bounds, should be invalid");
    }

    @Test
     void whenSetIsFilledWithNullAndNullNotAllowedThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "set is null, should be invalid");
    }

    @Test
     void whenSetIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmSetLength(nullAllowed = true, length = 5)
        final class SampleClass {
        } //inner class hack
        final NdmSetLength iotaSetLength = SampleClass.class.getAnnotation(NdmSetLength.class);
        cut = new NdmSetLengthValidator();
        cut.initialize(iotaSetLength);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "set is null and null is allowed, should be valid");
    }

}
