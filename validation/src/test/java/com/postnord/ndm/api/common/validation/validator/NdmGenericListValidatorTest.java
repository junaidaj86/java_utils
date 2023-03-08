package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmGenericList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({"CPD-START"})
@ExtendWith(MockitoExtension.class)
 class NdmGenericListValidatorTest {

    private NdmGenericListValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmGenericListValidator();
    }

    @Test
     void whenListIsInitializedThenItIsInitialized() {
        @NdmGenericList
        final class SampleClass {
        } //inner class hack
        final NdmGenericList iotaGenericList = SampleClass.class.getAnnotation(NdmGenericList.class);
        assertDoesNotThrow(() -> cut.initialize(iotaGenericList), "exception should not occur during initialization");
    }

    @Test
     void whenListIsFilledWithSingleValueThenItIsValid() {

        assertTrue(cut.isValid(Collections.singletonList("value1"), mockConstraintValidatorContext),
                "list contains a value, should be valid");
    }

    @Test
     void whenListIsFilledWithMultipleValuesThenItIsValid() {

        assertTrue(cut.isValid(Arrays.asList("value1", "value2"), mockConstraintValidatorContext),
                "list contains a value, should be valid");
    }

    @Test
     void whenListIsNotNotNullAndItIsEmptyThenItIsNotValid() {

        assertFalse(cut.isValid(Collections.emptyList(), mockConstraintValidatorContext),
                "list does not contain a value, should not valid");
    }

    @Test
     void whenListIsNullAndNullIsNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "list is null, should not be valid");
    }

    @Test
     void whenListIsNullAndNullIsAllowedThenItIsValid() {

        @NdmGenericList(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmGenericList iotaGenericList = SampleClass.class.getAnnotation(NdmGenericList.class);
        cut.initialize(iotaGenericList);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "list is null and null allowed, should be valid");
    }

    @Test
     void whenListIsEmptyAndEmptyIsNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(new ArrayList<>(), mockConstraintValidatorContext),
                "list is null, should not be valid");
    }

    @SuppressWarnings({"CPD-END"})
    @Test
     void whenListIsEmptyAndEmptyIsAllowedThenItIsValid() {

        @NdmGenericList(emptyListAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmGenericList iotaGenericList = SampleClass.class.getAnnotation(NdmGenericList.class);

        cut.initialize(iotaGenericList);

        assertTrue(cut.isValid(Collections.emptyList(), mockConstraintValidatorContext),
                "list is empty but it is allowed, should be valid");
    }
}
