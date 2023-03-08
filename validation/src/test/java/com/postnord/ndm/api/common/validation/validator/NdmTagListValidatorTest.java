package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmTagList;

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
 class NdmTagListValidatorTest {

    private NdmTagListValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmTagListValidator();
        @NdmTagList(maxListLength = 3, maxListItemLength = 20)
        final class SampleClass {
        } //add prefix to be tested for this test case
        final NdmTagList iotaTagList = SampleClass.class.getAnnotation(NdmTagList.class);
        cut.initialize(iotaTagList);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmTagList(maxListLength = 10)
        final class SampleClass {
        } //inner class hack
        final NdmTagList iotaTagList = SampleClass.class.getAnnotation(NdmTagList.class);
        assertDoesNotThrow(() -> cut.initialize(iotaTagList), "exception should not occur during initialization");
    }

    @Test
     void whenTagListIsFilledWithSingleValidValueThenItIsValid() {

        assertTrue(cut.isValid(Collections.singletonList("unittest:valid1"), mockConstraintValidatorContext),
                "tag list contains a tag value, should be valid");
    }

    @Test
     void whenTagListIsFilledWithMultipleValuesFencePostThenItIsValid() {

        //allowed tag list size is 3 lets test it at the fencepost
        assertTrue(cut.isValid(Arrays.asList("u:v", "unittest:valid12345", "unittest:valid3"),
                mockConstraintValidatorContext), "tag list contains 3 tag values, should be valid");
    }

    @Test
     void whenTagListIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmTagList(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmTagList iotaTagList = SampleClass.class.getAnnotation(NdmTagList.class);
        cut.initialize(iotaTagList);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "tag list is null and null is allowed, should be valid");
    }

    @Test
     void whenTagListIsTooLargeThenItIsNotValid() {

        //allowed size for tag list in this test case is 3 lets make it 4 and ensure it fails
        assertFalse(cut.isValid(Arrays.asList("unittest:a", "unittest:b", "unittest:c", "unittest:toobig"),
                mockConstraintValidatorContext), "tag list is too large 3 items are allowed it contains 4, should not be valid");
    }

    @Test
     void whenTagListItemtIsTooLargeThenItIsNotValid() {

        //allowed size for tag list item in this test case is 20 lets make the tag size 30
        assertFalse(cut.isValid(Arrays.asList("unittest:x", "unittest:123456789012345678901", "unittest:y"),
                mockConstraintValidatorContext), "tag list item is too large can only be 20 characters, should not be valid");
    }

    @Test
     void whenTagListItemIsEmptyThenItIsNotValid() {

        assertFalse(cut.isValid(Arrays.asList("unittest:aaa", "", "unittest:bbb"),
                mockConstraintValidatorContext), "tag list item was empty, should not be valid");
    }

    @Test
     void whenTagListItemHasInvalidFormat1ThenItIsNotValid() {

        assertFalse(cut.isValid(Arrays.asList("unittest:aa", ":value", "unittest:bb"),
                mockConstraintValidatorContext), "tag list item was invalid (no key), should not be valid");
    }

    @Test
     void whenTagListItemHasInvalidFormat2ThenItIsNotValid() {

        assertFalse(cut.isValid(Arrays.asList("unittest:xxx", "invalid:", "unittest:yyy"),
                mockConstraintValidatorContext), "tag list item was invalid (no value), should not be valid");
    }

    @Test
     void whenTagListItemHasInvalidFormat3ThenItIsNotValid() {

        assertFalse(cut.isValid(Arrays.asList("unittest:a", ":invalid:b", "unittest:c"),
                mockConstraintValidatorContext), "tag list item was invalid (too many seperators ), should not be valid");
    }

    @Test
     void whenTagListItemHasInvalidFormat4ThenItIsNotValid() {

        assertFalse(cut.isValid(Arrays.asList("unittest:1", "unittest:2", "invalid:value:"),
                mockConstraintValidatorContext), "tag list item was invalid (too many seperators), should not be valid");
    }

    @Test
     void whenTagListItemHasInvalidFormat5ThenItIsNotValid() {

        assertFalse(cut.isValid(Arrays.asList("invalid::11", "unittest:22", "unittest:value3"),
                mockConstraintValidatorContext), "tag list item was invalid, should not be valid");
    }

    @Test
     void whenTagListItemHasInvalidFormat6ThenItIsNotValid() {

        assertFalse(cut.isValid(Arrays.asList("invalid::value1", "::", "unittest:value3"),
                mockConstraintValidatorContext), "tag list item was invalid (empty seperators), should not be valid");
    }


    @Test
     void whenTagListIsNotFilledThenItIsNotValid() {

        assertFalse(cut.isValid(Collections.emptyList(), mockConstraintValidatorContext),
                "tag list does not contain any items, should be not valid");
    }

    @Test
     void whenTagListIsNullAndNullIsNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "tag list is null, should be not valid");
    }
}
