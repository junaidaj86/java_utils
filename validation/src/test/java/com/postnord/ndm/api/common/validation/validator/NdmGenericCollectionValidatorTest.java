package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmGenericCollection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("CPD-START")
@ExtendWith(MockitoExtension.class)
 class NdmGenericCollectionValidatorTest {

    private static final String VALUE_1 = "value1";

    private static final String VALUE_2 = "value2";

    private NdmGenericCollectionValidator cut;

    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmGenericCollectionValidator();
    }

    @Test
     void whenListIsInitializedThenItIsInitialized() {
        @NdmGenericCollection
        final class SampleClass {
        } //inner class hack
        final NdmGenericCollection iotaGenericCollection = SampleClass.class.getAnnotation(NdmGenericCollection.class);
        assertDoesNotThrow(() -> cut.initialize(iotaGenericCollection), "exception should not occur during initialization");
    }

    @Test
     void whenListIsFilledWithSingleValueThenItIsValid() {
        assertTrue(cut.isValid(List.of(VALUE_1), mockConstraintValidatorContext),
                "list contains a value, should be valid");
    }

    @Test
     void whenSetIsFilledWithSingleValueThenItIsValid() {
        assertTrue(cut.isValid(Set.of(VALUE_1), mockConstraintValidatorContext),
                "set contains a value, should be valid");
    }

    @Test
     void whenListIsFilledWithMultipleValuesThenItIsValid() {
        assertTrue(cut.isValid(List.of(VALUE_1, VALUE_2), mockConstraintValidatorContext),
                "list contains a value, should be valid");
    }

    @Test
     void whenSetIsFilledWithMultipleValuesThenItIsValid() {
        assertTrue(cut.isValid(Set.of(VALUE_1, VALUE_2), mockConstraintValidatorContext),
                "set contains a value, should be valid");
    }

    @Test
     void whenListIsNotNullAndItIsEmptyThenItIsNotValid() {
        assertFalse(cut.isValid(List.of(), mockConstraintValidatorContext),
                "list does not contain a value, should not valid");
    }

    @Test
     void whenListIsNullAndNullIsNotAllowedThenItIsNotValid() {
        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "list is null, should not be valid");
    }

    @Test
     void whenListIsNullAndNullIsAllowedThenItIsValid() {
        @NdmGenericCollection(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmGenericCollection iotaGenericCollection = SampleClass.class.getAnnotation(NdmGenericCollection.class);
        cut.initialize(iotaGenericCollection);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "list is null and null allowed, should be valid");
    }

    @Test
     void whenListIsEmptyAndEmptyIsNotAllowedThenItIsNotValid() {
        assertFalse(cut.isValid(List.of(), mockConstraintValidatorContext),
                "list is empty, should not be valid");
    }

    @Test
     void whenListIsEmptyAndEmptyIsAllowedThenItIsValid() {
        @NdmGenericCollection(emptyAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmGenericCollection iotaGenericCollection = SampleClass.class.getAnnotation(NdmGenericCollection.class);

        cut.initialize(iotaGenericCollection);

        assertTrue(cut.isValid(List.of(), mockConstraintValidatorContext),
                "list is empty but it is allowed, should be valid");
    }

    @Test
     void whenSetHasNullElementAndNullElementsAreNotAllowedThenItIsNotValid() {
        final Set<String> set = new HashSet<>();
        set.add(null);
        assertFalse(cut.isValid(set, mockConstraintValidatorContext),
                "set has null, should not be valid");
    }

    @SuppressWarnings("CPD-END")
    @Test
     void whenSetHasNullElementAndNullElementsAreAllowedThenItIsValid() {
        @NdmGenericCollection(nullElementsAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmGenericCollection iotaGenericCollection = SampleClass.class.getAnnotation(NdmGenericCollection.class);

        cut.initialize(iotaGenericCollection);

        final Set<String> set = new HashSet<>();
        set.add(null);
        assertTrue(cut.isValid(set, mockConstraintValidatorContext),
                "set has null but it is allowed, should be valid");
    }
}
