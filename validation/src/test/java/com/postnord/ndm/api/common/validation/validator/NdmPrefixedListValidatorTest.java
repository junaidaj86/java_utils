package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmPrefixedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmPrefixedListValidatorTest {

    private NdmPrefixedListValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmPrefixedListValidator();
        @NdmPrefixedList(prefix = "unittest:")
        final class SampleClass {
        } //add prefix to be tested for this test case
        final NdmPrefixedList iotaPrefixedList = SampleClass.class.getAnnotation(NdmPrefixedList.class);
        cut.initialize(iotaPrefixedList);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmPrefixedList(prefix = "testinitialize:")
        final class SampleClass {
        } //inner class hack
        final NdmPrefixedList iotaPrefixedList = SampleClass.class.getAnnotation(NdmPrefixedList.class);
        assertDoesNotThrow(() -> cut.initialize(iotaPrefixedList), "exception should not occur during initialization");
    }

    @Test
     void whenPrefixedListIsFilledWithSinglePrefixedValueThenItIsValid() {

        assertTrue(cut.isValid(Collections.singletonList("unittest:valid1"), mockConstraintValidatorContext),
                "prefixed list contains a prefixed value, should be valid");
    }

    @Test
     void whenPrefixedListIsFilledWithMultiplePrefixedValuesThenItIsValid() {

        assertTrue(cut.isValid(Arrays.asList("unittest:value1", "unittest:value2"), mockConstraintValidatorContext),
                "prefixed list contains multiple prefixed values, should be valid");
    }

    @Test
     void whenPrefixedListIsFilledWithBlankValueThenItIsNotValid() {

        assertFalse(cut.isValid(Collections.singletonList(""), mockConstraintValidatorContext),
                "prefixed list does not contain a prefixed value, should be not valid");
    }

    @Test
     void whenPrefixedListIsFilledWithRandomBlankValueThenItIsNotValid() {

        assertFalse(cut.isValid(Arrays.asList("unittest:1234", "not_prefixed", "unittest:2345"),
                mockConstraintValidatorContext),
                "prefixed list does not contain a prefixed value, should be not valid");
    }


    @Test
     void whenPrefixedListIsNotFilledThenItIsNotValid() {

        assertFalse(cut.isValid(Collections.emptyList(), mockConstraintValidatorContext),
                "prefixed list does not contain a value, should be not valid");
    }

    @Test
     void whenPrefixedListIsNullAndNullNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "prefixed list is null, should be not valid");
    }

    @Test
     void whenPrefixedListIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmPrefixedList(prefix = "testinitialize:", nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmPrefixedList iotaPrefixedList = SampleClass.class.getAnnotation(NdmPrefixedList.class);
        cut.initialize(iotaPrefixedList);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "prefixed list is null and null is allowed, should be valid");
    }

}
