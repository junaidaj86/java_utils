package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmStringLength;

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
class NdmStringLengthValidatorTest {

    private NdmStringLengthValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
    void setUp() {
        @NdmStringLength(length = 5)
        final class SampleClass {
        } //inner class hack
        final NdmStringLength iotaStringLength = SampleClass.class.getAnnotation(NdmStringLength.class);
        cut = new NdmStringLengthValidator();
        cut.initialize(iotaStringLength);
    }

    @Test
    void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmStringLength(length = 5)
        final class SampleClass {
        } //inner class hack
        final NdmStringLength iotaStringLength = SampleClass.class.getAnnotation(NdmStringLength.class);
        assertDoesNotThrow(() -> cut.initialize(iotaStringLength), "exception should not occur during initialization");
    }

    @Test
    void whenStringIsWithinBoundsThenItIsConsideredValid() {

        assertTrue(cut.isValid("1234", mockConstraintValidatorContext),
                "string is within bounds, should be valid");
    }

    @Test
    void whenStringIsFilledWithinBoundsFencePostThenItIsConsideredValid() {

        assertTrue(cut.isValid("12345", mockConstraintValidatorContext),
                "string is within bounds, should be valid");
    }

    @Test
    void whenStringIsFilledOutsideBoundsThenItIsConsideredNotValid() {

        assertFalse(cut.isValid("123456", mockConstraintValidatorContext),
                "string is outside bounds, should be not valid");
    }

    @Test
    void whenStringIsFilledWithBlankThenItIsConsideredNotValid() {

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "string is blank, should be invalid");
    }

    @Test
    void whenStringIsFilledWithNullAndNullIsNotAllowedThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "string is null, should be invalid");
    }

    @Test
    void whenStringIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmStringLength(length = 5, nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmStringLength iotaStringLength = SampleClass.class.getAnnotation(NdmStringLength.class);
        cut = new NdmStringLengthValidator();
        cut.initialize(iotaStringLength);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "string is null and null is allowed, should be valid");
    }
}
