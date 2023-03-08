package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmUniqueList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmUniqueListValidatorTest {

    private NdmUniqueListValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmUniqueListValidator();
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmUniqueList
        final class SampleClass {
        } //inner class hack
        final NdmUniqueList iotaUniqueList = SampleClass.class.getAnnotation(NdmUniqueList.class);
        assertDoesNotThrow(() -> cut.initialize(iotaUniqueList), "exception should not occur during initialization");
    }

    @Test
     void whenUniqueListIsFilledWithBlankValueThenItIsValid() {
        //the list is mandatory the values are not
        assertTrue(cut.isValid(Collections.singletonList(""), mockConstraintValidatorContext),
                "unique list contains an empty value, should be valid");
    }

    @Test
     void whenUniqueListIsFilledWithSingleValueThenItIsValid() {

        assertTrue(cut.isValid(Collections.singletonList("value1"), mockConstraintValidatorContext),
                "unique list contains a unique value, should be valid");
    }

    @Test
     void whenUniqueListIsEmptyThenItIsValid() {

        assertTrue(cut.isValid(Collections.emptyList(), mockConstraintValidatorContext),
                "unique list empty, should be valid");
    }

    @Test
     void whenUniqueListIsFilledWithMultipleValuesThenItIsValid() {

        assertTrue(cut.isValid(Arrays.asList("value1", "value2"), mockConstraintValidatorContext),
                "unique list contains multiple unique values, should be valid");
    }

    @Test
     void whenUniqueListIsFilledWithMultipleNonUniqueValuesThenItIsNotValid() {

        assertFalse(cut.isValid(Arrays.asList("1", "2", "1", "4"), mockConstraintValidatorContext),
                "unique list contains multiple non-unique values, should be not valid");
    }

    @Test
     void whenUniqueListIsNullAndNullIsNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "unique list is null, should be invalid");
    }

    @Test
     void whenStringIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmUniqueList(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmUniqueList iotaUniqueList = SampleClass.class.getAnnotation(NdmUniqueList.class);
        cut.initialize(iotaUniqueList);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "string is null and null is allowed, should be valid");
    }
}
