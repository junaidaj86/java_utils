package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmSetValidatorTest {

    private NdmSetValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmSetValidator();
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmSet
        final class SampleClass {
        } //inner class hack
        final NdmSet iotaSet = SampleClass.class.getAnnotation(NdmSet.class);
        assertDoesNotThrow(() -> cut.initialize(iotaSet), "exception should not occur during initialization");
    }

    @Test
     void whenSimpleSetIsFilledWithBlankValueThenItIsValid() {
        //the set is simple the values are not
        assertTrue(cut.isValid(Stream.of("").collect(Collectors.toSet()), mockConstraintValidatorContext),
                "simple set contains a value, should be valid");
    }

    @Test
     void whenSimpleSetIsFilledWithSingleValueThenItIsValid() {

        assertTrue(cut.isValid(Stream.of("value1").collect(Collectors.toSet()), mockConstraintValidatorContext),
                "simple set contains a value, should be valid");
    }

    @Test
     void whenSimpleSetIsFilledWithMultipleValuesThenItIsValid() {

        assertTrue(cut.isValid(Stream.of("value1", "value2").collect(Collectors.toSet()), mockConstraintValidatorContext),
                "simple set contains multiple values, should be valid");
    }

    @Test
     void whenSimpleSetIsNotFilledThenItIsNotValid() {

        assertFalse(cut.isValid(Collections.emptySet(), mockConstraintValidatorContext),
                "simple set does not contain a value, should be invalid");
    }

    @Test
     void whenSimpleSetIsNullAndNullIsNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "simple set does not contain a value, should be invalid");
    }

    @Test
     void whenSimpleSetIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmSet(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmSet iotaSet = SampleClass.class.getAnnotation(NdmSet.class);
        cut.initialize(iotaSet);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "simple set is null and null is allowed, should be valid");
    }
}
