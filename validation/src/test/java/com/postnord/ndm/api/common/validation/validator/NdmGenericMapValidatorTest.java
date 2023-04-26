package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmGenericMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("CPD-START")
@ExtendWith(MockitoExtension.class)
 class NdmGenericMapValidatorTest {

    private static final String KEY_1 = "key1";

    private static final String VALUE_1 = "value1";

    private static final String KEY_2 = "key2";

    private static final String VALUE_2 = "value2";

    private NdmGenericMapValidator cut;

    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmGenericMapValidator();
    }

    @Test
     void whenMapIsInitializedThenItIsInitialized() {
        @NdmGenericMap
        final class SampleClass {
        } //inner class hack
        final NdmGenericMap iotaGenericMap = SampleClass.class.getAnnotation(NdmGenericMap.class);
        assertDoesNotThrow(() -> cut.initialize(iotaGenericMap), "exception should not occur during initialization");
    }

    @Test
     void whenMapIsFilledWithSingleValueThenItIsValid() {
        assertTrue(cut.isValid(Map.of(KEY_1, VALUE_1), mockConstraintValidatorContext),
                "map contains a value, should be valid");
    }

    @Test
     void whenListIsFilledWithMultipleValuesThenItIsValid() {
        assertTrue(cut.isValid(Map.of(KEY_1, VALUE_1, KEY_2, VALUE_2), mockConstraintValidatorContext),
                "map contains a value, should be valid");
    }

    @Test
     void whenListIsNotNullAndItIsEmptyThenItIsNotValid() {
        assertFalse(cut.isValid(Map.of(), mockConstraintValidatorContext),
                "map does not contain a value, should not valid");
    }

    @Test
     void whenMapIsNullAndNullIsNotAllowedThenItIsNotValid() {
        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "map is null, should not be valid");
    }

    @Test
     void whenMapIsNullAndNullIsAllowedThenItIsValid() {
        @NdmGenericMap(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmGenericMap iotaGenericMap = SampleClass.class.getAnnotation(NdmGenericMap.class);
        cut.initialize(iotaGenericMap);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "map is null and null allowed, should be valid");
    }

    @Test
     void whenListIsEmptyAndEmptyIsNotAllowedThenItIsNotValid() {
        assertFalse(cut.isValid(Map.of(), mockConstraintValidatorContext),
                "map is empty, should not be valid");
    }

    @Test
     void whenListIsEmptyAndEmptyIsAllowedThenItIsValid() {
        @NdmGenericMap(emptyAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmGenericMap iotaGenericMap = SampleClass.class.getAnnotation(NdmGenericMap.class);

        cut.initialize(iotaGenericMap);

        assertTrue(cut.isValid(Map.of(), mockConstraintValidatorContext),
                "map is empty but it is allowed, should be valid");
    }

    @Test
     void whenMapHasNullKeyAndNullKeysAreNotAllowedThenItIsNotValid() {
        final Map<String, String> map = new HashMap<>();
        map.put(null, VALUE_1);
        assertFalse(cut.isValid(map, mockConstraintValidatorContext),
                "map has null key, should not be valid");
    }

    @Test
     void whenMapHasNullKeyAndNullKeysAreAllowedThenItIsValid() {
        @NdmGenericMap(nullKeysAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmGenericMap iotaGenericCollection = SampleClass.class.getAnnotation(NdmGenericMap.class);

        cut.initialize(iotaGenericCollection);

        final Map<String, String> map = new HashMap<>();
        map.put(null, VALUE_1);
        assertTrue(cut.isValid(map, mockConstraintValidatorContext),
                "map has null key but it is allowed, should be valid");
    }

    @Test
     void whenMapHasNullValueAndNullValuesAreNotAllowedThenItIsNotValid() {
        final Map<String, String> map = new HashMap<>();
        map.put(KEY_1, null);
        assertFalse(cut.isValid(map, mockConstraintValidatorContext),
                "map has null value, should not be valid");
    }

    @SuppressWarnings("CPD-END")
    @Test
     void whenMapHasNullValueAndNullValuesAreAllowedThenItIsValid() {
        @NdmGenericMap(nullValuesAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmGenericMap iotaGenericCollection = SampleClass.class.getAnnotation(NdmGenericMap.class);

        cut.initialize(iotaGenericCollection);

        final Map<String, String> map = new HashMap<>();
        map.put(KEY_1, null);
        assertTrue(cut.isValid(map, mockConstraintValidatorContext),
                "map has null value but it is allowed, should be valid");
    }
}
