package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmCollectionStringLength;

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
 class NdmCollectionStringLengthValidatorTest {

    private NdmCollectionStringLengthValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmCollectionStringLengthValidator();
        @NdmCollectionStringLength(length = 5)
        final class SampleClass {
        }
        final NdmCollectionStringLength iotaCollectionStringLength = SampleClass.class.getAnnotation(NdmCollectionStringLength.class);
        cut.initialize(iotaCollectionStringLength);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmCollectionStringLength()
        final class SampleClass {
        }
        final NdmCollectionStringLength iotaCollectionStringLength = SampleClass.class.getAnnotation(NdmCollectionStringLength.class);
        assertDoesNotThrow(() -> cut.initialize(iotaCollectionStringLength), "exception should not occur during initialization");
    }

    @Test
     void whenCollectionIsFilledWithSingleItemWithinBoundsThenItShouldBeValid() {

        assertTrue(cut.isValid(Stream.of("123").collect(Collectors.toSet())
                , mockConstraintValidatorContext), "Collection should be valid contains items within bounds");
    }

    @Test
     void whenCollectionIsFilledWithSingleItemWithinBoundsFencePostThenItShouldBeValid() {

        assertTrue(cut.isValid(Stream.of("12345").collect(Collectors.toSet())
                , mockConstraintValidatorContext), "Fence Post collection should be valid contains items within bounds");
    }

    @Test
     void whenCollectionIsFilledWithSingleItemOutsideBoundsThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("thisIsMoreThan5:1234567890123456789012345678901234567890").collect(Collectors.toSet())
                , mockConstraintValidatorContext), "Collection invalid contains items outside bounds");
    }

    @Test
     void whenCollectionIsFilledWithMultipleItemsWithinBoundsThenItShouldBeValid() {

        assertTrue(cut.isValid(Stream.of("1", "12", "123", "1234", "12345").collect(Collectors.toSet())
                , mockConstraintValidatorContext), "Collection valid multiple items within bounds");
    }

    @Test
     void whenCollectionIsFilledWithMultipleItemsAndSomeAreOutsideBoundsThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("1", "12", "123", "thisIsMoreThan5", "12345").collect(Collectors.toSet())
                , mockConstraintValidatorContext), "Collection invalid some items are outside bounds");
    }

    @Test
     void whenCollectionIsFilledWithMultipleItemsAndAllAreOutsideBoundsThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("123456", "1234567", "123456789").collect(Collectors.toSet())
                , mockConstraintValidatorContext), "Collection invalid multiple items outside bounds");
    }

    @Test
     void whenCollectionIsFilledWithSingleItemWithinBoundsWithNewConfiguredValidValueThenItShouldBeValid() {

        @NdmCollectionStringLength(length = 6)
        final class SampleClass {
        }
        final NdmCollectionStringLength iotaCollectionStringLength = SampleClass.class.getAnnotation(NdmCollectionStringLength.class);
        cut.initialize(iotaCollectionStringLength);

        assertTrue(cut.isValid(Stream.of("aaaaaa").collect(Collectors.toSet())
                , mockConstraintValidatorContext), "Collection invalid single item outside bounds");
    }

    @Test
     void whenCollectionIsFilledWithMultipleItemWithinBoundsWithNewConfiguredValidValueThenItShouldBeValid() {

        @NdmCollectionStringLength(length = 7)
        final class SampleClass {
        }
        final NdmCollectionStringLength iotaCollectionStringLength = SampleClass.class.getAnnotation(NdmCollectionStringLength.class);
        cut.initialize(iotaCollectionStringLength);

        assertTrue(cut.isValid(Stream.of("1", "1234567", "123456").collect(Collectors.toSet())
                , mockConstraintValidatorContext), "Collection valid default size was overridden");
    }

    @Test
     void whenCollectionIsFilledWithMultipleItemOutsideBoundsWithNewConfiguredValidValueThenItShouldNotBeValid() {

        @NdmCollectionStringLength(length = 6)
        final class SampleClass {
        }
        final NdmCollectionStringLength iotaCollectionStringLength = SampleClass.class.getAnnotation(NdmCollectionStringLength.class);
        cut.initialize(iotaCollectionStringLength);

        assertFalse(cut.isValid(Stream.of("1", "thisIsMoreThan6", "123456").collect(Collectors.toSet())
                , mockConstraintValidatorContext), "Collection invalid item outside overridden bounds");
    }

    @Test
     void whenCollectionIsFilledWithBlankThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("").collect(Collectors.toSet())
                , mockConstraintValidatorContext), "Collection does not contain any item");
    }

    @Test
     void whenCollectionIsEmptyThenItShouldBeValid() {

        assertTrue(cut.isValid(Collections.emptyList(), mockConstraintValidatorContext),
                "Collection is empty, should be valid");
    }

    @Test
     void whenConnectionIsFilledWithNullValueThenItShouldBeValid() {
        assertFalse(cut.isValid(Stream.of((String) null).collect(Collectors.toSet()), mockConstraintValidatorContext),
                "Collection contains null value, should be invalid");
    }

    @Test
     void whenCollectionIsFilledWithMultipleItemsAndOneNullItemThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("1", "2", null, "3").collect(Collectors.toSet())
                , mockConstraintValidatorContext),
                "Collection contains null value, should be invalid");
    }

    @Test
     void whenCollectionIsNullAndNullNotAllowedThenItShouldNotBeValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "Collection is null, should not be invalid");
    }

    @Test
     void whenCollectionIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmCollectionStringLength(nullAllowed = true, length = 6)
        final class SampleClass {
        }
        final NdmCollectionStringLength iotaCollectionStringLength = SampleClass.class.getAnnotation(NdmCollectionStringLength.class);
        cut.initialize(iotaCollectionStringLength);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "collection is null and null is allowed, should be valid");
    }
}
