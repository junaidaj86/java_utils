package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/*
Sociable unit test : This class will collaborate with its dependencies and will NOT mock them. It will use
the real collaborator jakarta.validation.Validator as part of its test scope.
 */
@SuppressWarnings({"CPD-START"})
 class ValidationTwoSocialTest {

     static final String INVALID_NULL_NOT_ALLOWED_STRING_SIZE_PARAMETER = "invalid nullNotAllowedStringSizeParameter";
     static final String INVALID_NULL_NOT_ALLOWED_LIST_LENGTH_PARAMETER = "invalid nullNotAllowedListLengthParameter";
     static final String INVALID_NULL_NOT_ALLOWED_SIMPLE_LIST_PARAMETER = "invalid nullNotAllowedSimpleListParameter";
     static final String INVALID_NULL_NOT_ALLOWED_PREFIXED_LIST_PARAMETER = "invalid nullNotAllowedPrefixedListParameter";
     static final String INVALID_NULL_NOT_ALLOWED_TAG_LIST_PARAMETER = "invalid nullNotAllowedTagListParameter";
     static final String INVALID_NULL_NOT_ALLOWED_PREFIXED_STRING_PARAMETER = "invalid nullNotAllowedPrefixedStringParameter";
     static final String INVALID_NULL_NOT_ALLOWED_ACCOUNTID_PARAMETER = "invalid nullNotAllowedAccountIdStringParameter";
     static final String INVALID_ICC_ID_IT_SHOULD_BE_19_20_CHARACTERS_IN_LENGTH = "Invalid 'iccId'. It should be 19-20 characters in length.";

    private Validator validator;
    private TestStub testStub;

    @BeforeEach
     void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        testStub = initializeTestStubWithValidParameters();
    }

    private TestStub initializeTestStubWithValidParameters() {
        final TestStub ts = new TestStub();
        ts.setNullNotAllowedStringSizeParameter("13");
        ts.setNullNotAllowedListLengthParameter(Arrays.asList("a", "b"));
        ts.setNullAllowedListLengthParameter(null);

        ts.setNullNotAllowedSimpleListParameter(Arrays.asList("one", "two"));
        ts.setNullAllowedSimpleListParameter(null);

        ts.setNullNotAllowedPrefixedListParameter(Arrays.asList("nnait:one", "nnait:two"));
        ts.setNullAllowedPrefixedListParameter(null);

        ts.setNullNotAllowedTagListParameter(Arrays.asList("v:one", "v:two"));
        ts.setNullAllowedTagListParameter(null);

        ts.setNullNotAllowedPrefixedStringParameter("nnait:one");
        ts.setNullAllowedPrefixedStringParameter(null);

        ts.setNullNotAllowedIccId("89430300000482738077");
        ts.setNullAllowedIccId(null);

        return ts;
    }

    @Test
    void whenClassHasStringSizeSetToLowerBoundThenClassShouldBeValid() {

        testStub.setNullNotAllowedStringSizeParameter("12");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasStringSizeSetToUpperBoundThenClassShouldBeValid() {

        testStub.setNullNotAllowedStringSizeParameter("123456789012345");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasStringSizeBelowLowerBoundThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedStringSizeParameter("1"); //out of range size should be 2 to 15

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_STRING_SIZE_PARAMETER));
    }

    @Test
    void whenClassHasStringSizeAboveUpperBoundThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedStringSizeParameter("1234567890123456"); //out of range size should be 2 to 15

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_STRING_SIZE_PARAMETER));
    }

    @Test
    void whenClassHasListLengthSetToUpperBoundThenClassShouldBeValid() {

        testStub.setNullNotAllowedListLengthParameter(Arrays.asList("1", "2", "3")); //list length of 3 should be ok

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasListLengthAboveUpperBoundThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedListLengthParameter(Arrays.asList("one", "two", "three", "four")); //list length of 4 out of range

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_LIST_LENGTH_PARAMETER));
    }

    @Test
    void whenClassHasListSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedSimpleListParameter(Arrays.asList("one", "two", "three")); //mandatory list of 3 should be ok

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasEmptyListSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedSimpleListParameter(Collections.emptyList()); //empty list should be invalid

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_SIMPLE_LIST_PARAMETER));
    }

    @Test
    void whenClassHasListSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedSimpleListParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_SIMPLE_LIST_PARAMETER));
    }

    @Test
    void whenClassHasListSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedSimpleListParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasValidPrefixedListSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedPrefixedListParameter(Arrays.asList("nnait:a", "nnait:b", "nnait:c")); //nnait: must prefix all items

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInvalidPrefixedListSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedPrefixedListParameter(Arrays.asList("nnait:a", "oh:b", "nnait:c")); //oh: instead of nnait:

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_PREFIXED_LIST_PARAMETER));
    }

    @Test
    void whenClassHasPrefixedListSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedPrefixedListParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_PREFIXED_LIST_PARAMETER));
    }

    @Test
    void whenClassHasPrefixedListSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedPrefixedListParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasValidTagListSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedTagListParameter(Arrays.asList("key:a", "key:b", "key:c"));

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInvalidTagListSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedTagListParameter(Arrays.asList("k:a", ":b", "k:c")); //:b is not a valid tag

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_TAG_LIST_PARAMETER));
    }

    @Test
    void whenClassHasTagListSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedTagListParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_TAG_LIST_PARAMETER));
    }

    @Test
    void whenClassHasTagListSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedTagListParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasValidPrefixedStringSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedPrefixedStringParameter("nnait:one"); //nnait: must prefix string

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInvalidPrefixedStringSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedPrefixedStringParameter("oh:one"); //oh: instead of nnait:

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_PREFIXED_STRING_PARAMETER));
    }

    @Test
    void whenClassHasPrefixedStringSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedPrefixedStringParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_PREFIXED_STRING_PARAMETER));
    }

    @Test
    void whenClassHasPrefixedStringSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedPrefixedStringParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInvalidICCIDOfTooLargeSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedIccId("894303000004827380777");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertEquals(INVALID_ICC_ID_IT_SHOULD_BE_19_20_CHARACTERS_IN_LENGTH, result.iterator().next().getMessage());
    }

    @Test
    void whenClassHasInvalidICCIDOfTooSmallSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedIccId("894303000004827380");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertEquals(INVALID_ICC_ID_IT_SHOULD_BE_19_20_CHARACTERS_IN_LENGTH, result.iterator().next().getMessage());
    }

    @Test
    void whenClassHasValidICCIDSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedIccId("89430300000482738077");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInvalidICCIDOfNullSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedIccId(null);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertEquals("Expected {iccId}.'iccId' is missing.", result.iterator().next().getMessage());
    }

    @Test
    void whenClassHasInvalidICCIDOfEmptyStringSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedIccId("");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertEquals(INVALID_ICC_ID_IT_SHOULD_BE_19_20_CHARACTERS_IN_LENGTH, result.iterator().next().getMessage());
    }

    @Test
    void whenClassHasInvalidICCIDOfNonNumericsStringSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullAllowedIccId("891234WRONG123456789");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertEquals(INVALID_ICC_ID_IT_SHOULD_BE_19_20_CHARACTERS_IN_LENGTH, result.iterator().next().getMessage());
    }

    @Test
    void whenClassHasInvalidICCIDOfRandomStringSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullAllowedIccId("89123456789123456789");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertEquals(INVALID_ICC_ID_IT_SHOULD_BE_19_20_CHARACTERS_IN_LENGTH, result.iterator().next().getMessage());
    }

    @Test
    void whenClassHasIccidSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedIccId(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("Expected {iccId}.'iccId' is missing."));
    }

    @Test
    void whenClassHasIccidSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedIccId(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    private class TestStub {
        @Size(min = 2, max = 15, message = INVALID_NULL_NOT_ALLOWED_STRING_SIZE_PARAMETER)
        private String nullNotAllowedStringSizeParameter;
        @NdmListLength(length = 3, message = INVALID_NULL_NOT_ALLOWED_LIST_LENGTH_PARAMETER)
        private List<String> nullNotAllowedListLengthParameter;
        @NdmListLength(nullAllowed = true, length = 3, message = INVALID_NULL_NOT_ALLOWED_LIST_LENGTH_PARAMETER)
        private List<String> nullAllowedListLengthParameter;
        @NdmList(message = INVALID_NULL_NOT_ALLOWED_SIMPLE_LIST_PARAMETER)
        private List<String> nullNotAllowedSimpleListParameter;
        @NdmList(nullAllowed = true, message = INVALID_NULL_NOT_ALLOWED_SIMPLE_LIST_PARAMETER)
        private List<String> nullAllowedSimpleListParameter;
        @NdmPrefixedList(prefix = "nnait:", message = INVALID_NULL_NOT_ALLOWED_PREFIXED_LIST_PARAMETER)
        private List<String> nullNotAllowedPrefixedListParameter;
        @NdmPrefixedList(nullAllowed = true, prefix = "nait:", message = INVALID_NULL_NOT_ALLOWED_PREFIXED_LIST_PARAMETER)
        private List<String> nullAllowedPrefixedListParameter;
        @NdmTagList(message = INVALID_NULL_NOT_ALLOWED_TAG_LIST_PARAMETER)
        private List<String> nullNotAllowedTagListParameter;
        @NdmTagList(nullAllowed = true, message = INVALID_NULL_NOT_ALLOWED_TAG_LIST_PARAMETER)
        private List<String> nullAllowedTagListParameter;
        @NdmPrefixedString(prefix = "nnait:", message = INVALID_NULL_NOT_ALLOWED_PREFIXED_STRING_PARAMETER)
        private String nullNotAllowedPrefixedStringParameter;
        @NdmPrefixedString(nullAllowed = true, prefix = "nait:", message = INVALID_NULL_NOT_ALLOWED_PREFIXED_STRING_PARAMETER)
        private String nullAllowedPrefixedStringParameter;
        @NdmIccIdString(message = "invalid IccId")
        private String nullNotAllowedIccId;
        @NdmIccIdString(nullAllowed = true, message = "invalid IccId")
        private String nullAllowedIccId;

        void setNullAllowedListLengthParameter(final List<String> nullAllowedListLengthParameter) {
            this.nullAllowedListLengthParameter = nullAllowedListLengthParameter;
        }

        void setNullAllowedSimpleListParameter(final List<String> nullAllowedSimpleListParameter) {
            this.nullAllowedSimpleListParameter = nullAllowedSimpleListParameter;
        }

        void setNullAllowedPrefixedListParameter(final List<String> nullAllowedPrefixedListParameter) {
            this.nullAllowedPrefixedListParameter = nullAllowedPrefixedListParameter;
        }

        void setNullAllowedTagListParameter(final List<String> nullAllowedTagListListParameter) {
            this.nullAllowedTagListParameter = nullAllowedTagListParameter;
        }

        void setNullAllowedPrefixedStringParameter(final String nullAllowedPrefixedStringParameter) {
            this.nullAllowedPrefixedStringParameter = nullAllowedPrefixedStringParameter;
        }

        void setNullNotAllowedIccId(final String nullNotAllowedIccId) {
            this.nullNotAllowedIccId = nullNotAllowedIccId;
        }

        void setNullAllowedIccId(final String nullAllowedIccId) {
            this.nullAllowedIccId = nullAllowedIccId;
        }

        void setNullNotAllowedStringSizeParameter(final String nullNotAllowedStringSizeParameter) {
            this.nullNotAllowedStringSizeParameter = nullNotAllowedStringSizeParameter;
        }

        void setNullNotAllowedListLengthParameter(final List<String> nullNotAllowedListLengthParameter) {
            this.nullNotAllowedListLengthParameter = nullNotAllowedListLengthParameter;
        }

        void setNullNotAllowedSimpleListParameter(final List<String> nullNotAllowedSimpleListParameter) {
            this.nullNotAllowedSimpleListParameter = nullNotAllowedSimpleListParameter;
        }

        void setNullNotAllowedPrefixedListParameter(final List<String> nullNotAllowedPrefixedListParameter) {
            this.nullNotAllowedPrefixedListParameter = nullNotAllowedPrefixedListParameter;
        }

        void setNullNotAllowedTagListParameter(final List<String> nullNotAllowedTagListParameter) {
            this.nullNotAllowedTagListParameter = nullNotAllowedTagListParameter;
        }

        @SuppressWarnings({"CPD-END"})
        void setNullNotAllowedPrefixedStringParameter(final String nullNotAllowedPrefixedStringParameter) {
            this.nullNotAllowedPrefixedStringParameter = nullNotAllowedPrefixedStringParameter;
        }
    }
}
