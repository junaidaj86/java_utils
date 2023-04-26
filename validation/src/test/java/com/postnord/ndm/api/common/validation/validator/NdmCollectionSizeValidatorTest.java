package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmCollectionSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmCollectionSizeValidatorTest {

    private NdmCollectionSizeValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        @NdmCollectionSize(size = 3)
        final class SampleClass {
        } //inner class hack
        final NdmCollectionSize iotaCollectionSize = SampleClass.class.getAnnotation(NdmCollectionSize.class);
        cut = new NdmCollectionSizeValidator();
        cut.initialize(iotaCollectionSize);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmCollectionSize()
        final class SampleClass {
        } //inner class hack
        final NdmCollectionSize iotaCollectionSize = SampleClass.class.getAnnotation(NdmCollectionSize.class);
        assertDoesNotThrow(() -> cut.initialize(iotaCollectionSize), "exception should not occur during initialization");
    }

    @Test
     void whenCollectionIsFilledWithinBoundsThenItIsConsideredValid() {

        assertTrue(cut.isValid(Stream.of("value1").collect(Collectors.toList()), mockConstraintValidatorContext),
                "Collection should be within bounds");
    }

    @Test
     void whenCollectionIsFilledWithinBoundsFencePostThenItIsConsideredValid() {

        assertTrue(cut.isValid(Stream.of("value10", "value11", "value12").collect(Collectors.toSet()),
                mockConstraintValidatorContext),
                "Collection should be within bounds");
    }

    @Test
     void whenCollectionIsFilledOutsideBoundsThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(Stream.of("value13", "value14", "value15", "value16").collect(Collectors.toSet()),
                mockConstraintValidatorContext),
                "Collection is outside bounds, should be invalid");
    }

    @Test
     void whenCollectionIsEmptyThenItIsConsideredValid() {

        assertTrue(cut.isValid(Collections.EMPTY_LIST,
                mockConstraintValidatorContext),
                "Collection is empty, should be valid");
    }

    @Test
     void whenCollectionIsNullAndNullNotAllowedThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "Collection is null, should be invalid");
    }

    @Test
     void whenCollectionIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmCollectionSize(nullAllowed = true, size = 15)
        final class SampleClass {
        } //inner class hack
        final NdmCollectionSize iotaCollectionSize = SampleClass.class.getAnnotation(NdmCollectionSize.class);
        cut.initialize(iotaCollectionSize);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "Collection is null and null is allowed, should be valid");
    }
}
