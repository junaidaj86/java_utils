package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmString;

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
class NdmStringValidatorTest {

    private NdmStringValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmStringValidator();
    }

    @Test
    void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmString
        final class SampleClass {
        } //inner class hack
        final NdmString iotaString = SampleClass.class.getAnnotation(NdmString.class);
        assertDoesNotThrow(() -> cut.initialize(iotaString), "exception should not occur during initialization");
    }

    @Test
    void whenStringIsFilledWithSingleValueThenItIsConsideredValid() {

        assertTrue(cut.isValid("value1", mockConstraintValidatorContext),
                "simple string contains a value, should be valid");
    }

    @Test
    void whenStringIsEmptyAndEmptyStringIsNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "simple string is blank, should be invalid");
    }

    @Test
    void whenStringIsEmptyAndEmptyStringIsAllowedThenItIsConsideredValid() {
        @NdmString(emptyAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmString iotaString = SampleClass.class.getAnnotation(NdmString.class);
        cut.initialize(iotaString);

        assertTrue(cut.isValid("", mockConstraintValidatorContext),
                "simple string is blank and empty is allowed, should be valid");
    }

    @Test
    void whenStringIsNullAndNullIsNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "simple string is null, should be invalid");
    }

    @Test
    void whenSimpleStringIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmString(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmString iotaString = SampleClass.class.getAnnotation(NdmString.class);
        cut.initialize(iotaString);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "simple string is null and null is allowed, should be valid");
    }

}
