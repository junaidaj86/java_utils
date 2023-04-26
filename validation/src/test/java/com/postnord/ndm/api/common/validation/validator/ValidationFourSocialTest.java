package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmIntegerRange;
import com.postnord.ndm.api.common.validation.constraints.NdmLuhnArray;
import com.postnord.ndm.api.common.validation.constraints.NdmLuhnString;
import com.postnord.ndm.api.common.validation.constraints.NdmPositiveNumber;
import com.postnord.ndm.api.common.validation.constraints.NdmStringRegex;
import com.postnord.ndm.api.common.validation.constraints.NdmUniqueList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/*
Sociable unit test : This class will collaborate with its dependencies and will NOT mock them. It will use
the real collaborator jakarta.validation.Validator as part of its test scope.
 */
 class ValidationFourSocialTest {

     static final String INVALID_NULL_NOT_ALLOWED_POSITIVE_NUMBER_PARAMETER = "invalid nullNotAllowedPositiveNumberParameter";
     static final String INVALID_NULL_NOT_ALLOWED_INTEGER_RANGE_PARAMETER = "invalid nullNotAllowedIntegerRangeParameter";
     static final String INVALID_NULL_NOT_ALLOWED_UNIQUE_LIST_PARAMETER = "invalid nullNotAllowedUniqueListParameter";
     static final String VALID_LUHN_STRING = "89430300000482738077";

    private Validator validator;
    private TestStub testStub;

    @BeforeEach
     void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        testStub = initializeTestStubWithValidParameters();
    }

    private TestStub initializeTestStubWithValidParameters() {
        final TestStub ts = new TestStub();

        ts.setNullNotAllowedPositiveNumberParameter(2);
        ts.setNullAllowedPositiveNumberParameter(null);

        ts.setNullNotAllowedIntegerRangeParameter(2);
        ts.setNullAllowedIntegerRangeParameter(null);

        ts.setNullNotAllowedLuhnArrayParameter(new String[]{VALID_LUHN_STRING, "8935901990831915138F"});
        ts.setNullAllowedLuhnArrayParameter(null);

        ts.setNullNotAllowedLuhnStringParameter(VALID_LUHN_STRING);
        ts.setNullAllowedLuhnStringParameter(null);

        ts.setNullNotAllowedUniqueListParameter(Arrays.asList("one", "2", "three"));
        ts.setNullAllowedUniqueListParameter(null);

        ts.setNullNotAllowedStringRegex("10d");
        ts.setNullAllowedStringRegex(null);

        return ts;
    }

    @Test
     void whenClassHasInRangeIntegerSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedIntegerRangeParameter(5);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInvalidIntegerInRangeSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedIntegerRangeParameter(-3);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_INTEGER_RANGE_PARAMETER));
    }

    @Test
     void whenClassHasPositiveIntegerSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedPositiveNumberParameter(99);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInvalidPositiveIntegerSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedPositiveNumberParameter(-3);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_POSITIVE_NUMBER_PARAMETER));
    }

    @Test
     void whenClassHasPositiveLongSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedPositiveNumberParameter(12L);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInvalidPositiveLongSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedPositiveNumberParameter(0L);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_POSITIVE_NUMBER_PARAMETER));
    }

    @Test
     void whenClassHasPositiveFloatSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedPositiveNumberParameter(0.1);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInvalidPositiveFloatSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedPositiveNumberParameter(0.0);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_POSITIVE_NUMBER_PARAMETER));
    }

    @Test
     void whenClassHasPositiveDoubleSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedPositiveNumberParameter(Double.MAX_VALUE);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInvalidPositiveDoubleSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedPositiveNumberParameter(-Double.MAX_VALUE);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_POSITIVE_NUMBER_PARAMETER));
    }

    @Test
     void whenClassHasPositiveIntegerSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedPositiveNumberParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_POSITIVE_NUMBER_PARAMETER));
    }

    @Test
     void whenClassHasPositiveIntegerSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedPositiveNumberParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasValidLuhnStringInCollectionSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedLuhnArrayParameter(new String[]{VALID_LUHN_STRING, "8935901990831915138F"});

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInvalidLuhnStringInCollectionSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedLuhnArrayParameter(new String[]{VALID_LUHN_STRING, "1232312431523"});

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
    }

    @Test
     void whenClassHasInvalidLuhnStringOfNullInCollectionSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedLuhnArrayParameter(new String[]{VALID_LUHN_STRING, null});

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
    }

    @Test
     void whenClassHasArrayLuhnStringSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedLuhnArrayParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("invalid nullNotAllowedLuhnArrayParameter"));
    }

    @Test
     void whenClassHasArrayLuhnStringSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedLuhnArrayParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInvalidLuhnStringOfNonAlphaNumericInCollectionSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedLuhnArrayParameter(new String[]{"89430300WRONG2738077", null});

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
    }

    @Test
     void whenClassHasValidLuhnStringSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedLuhnStringParameter(VALID_LUHN_STRING);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasLuhnStringSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedLuhnStringParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("invalid nullNotAllowedLuhnStringParameter"));
    }

    @Test
     void whenClassHasLuhnStringSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedLuhnStringParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInvalidLuhnStringOfEmptySpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedLuhnStringParameter("");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
    }

    @Test
     void whenClassHasInvalidLuhnStringWrongChecksumSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedLuhnStringParameter("89123456789123456789");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
    }

    @Test
     void whenClassHasInvalidLuhnStringWrongAlphaNumericSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedLuhnStringParameter("89430300000482738077A");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
    }

    @Test
     void whenClassHasUniqueListSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedUniqueListParameter(Arrays.asList("one", "two", "three"));

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInvalidUniqueListSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedUniqueListParameter(Arrays.asList("one", "nine", "nine")); //nine: is not unique

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_UNIQUE_LIST_PARAMETER));
    }

    @Test
     void whenClassHasUniqueListSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedUniqueListParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_UNIQUE_LIST_PARAMETER));
    }

    @Test
     void whenClassHasUniqueListSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedUniqueListParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasValidRegexStringSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedStringRegex("10d");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInvalidRegexStringSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedStringRegex("10xxxx");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
    }

    @Test
     void whenClassHasRegexSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedStringRegex(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("invalid nullNotAllowedStringRegexParameter"));
    }

    @Test
     void whenClassHasRegexSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedStringRegex(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    private class TestStub {
        @NdmPositiveNumber(message = INVALID_NULL_NOT_ALLOWED_POSITIVE_NUMBER_PARAMETER)
        private Number nullNotAllowedPositiveNumberParameter;
        @NdmPositiveNumber(nullAllowed = true, message = INVALID_NULL_NOT_ALLOWED_POSITIVE_NUMBER_PARAMETER)
        private Number nullAllowedPositiveNumberParameter;
        @NdmIntegerRange(message = INVALID_NULL_NOT_ALLOWED_INTEGER_RANGE_PARAMETER, min = 1, max = 10)
        private Integer nullNotAllowedIntegerRangeParameter;
        @NdmIntegerRange(nullAllowed = true, message = "invalid nullAllowedIntegerRangeParameter", min = 1, max = 10)
        private Integer nullAllowedIntegerRangeParameter;
        @NdmLuhnArray(message = "invalid nullNotAllowedLuhnArrayParameter")
        private String[] nullNotAllowedLuhnArrayParameter;
        @NdmLuhnArray(nullAllowed = true, message = "invalid nullNotAllowedLuhnArrayParameter")
        private String[] nullAllowedLuhnArrayParameter;
        @NdmLuhnString(message = "invalid nullNotAllowedLuhnStringParameter")
        private String nullNotAllowedLuhnStringParameter;
        @NdmLuhnString(nullAllowed = true, message = "invalid nullNotAllowedLuhnStringParameter")
        private String nullAllowedLuhnStringParameter;
        @NdmUniqueList(message = INVALID_NULL_NOT_ALLOWED_UNIQUE_LIST_PARAMETER)
        private List<String> nullNotAllowedUniqueListParameter;
        @NdmUniqueList(nullAllowed = true, message = INVALID_NULL_NOT_ALLOWED_UNIQUE_LIST_PARAMETER)
        private List<String> nullAllowedUniqueListParameter;
        @NdmStringRegex(regex = "\\d+[wdhm]", message = "invalid nullNotAllowedStringRegexParameter")
        private String nullNotAllowedStringRegex;
        @NdmStringRegex(nullAllowed = true, regex = "\\d+[wdhm]", message = "invalid nullNotAllowedStringRegexParameter")
        private String nullAllowedStringRegex;

        void setNullAllowedPositiveNumberParameter(final Number nullAllowedPositiveNumberParameter) {
            this.nullAllowedPositiveNumberParameter = nullAllowedPositiveNumberParameter;
        }

        @SuppressWarnings("PMD.ArrayIsStoredDirectly")
            //required for test case
        void setNullAllowedLuhnArrayParameter(final String... nullAllowedLuhnArrayParameter) {
            this.nullAllowedLuhnArrayParameter = nullAllowedLuhnArrayParameter;
        }

        void setNullAllowedLuhnStringParameter(final String nullAllowedLuhnStringParameter) {
            this.nullAllowedLuhnStringParameter = nullAllowedLuhnStringParameter;
        }

        void setNullNotAllowedPositiveNumberParameter(final Number nullNotAllowedPositiveNumberParameter) {
            this.nullNotAllowedPositiveNumberParameter = nullNotAllowedPositiveNumberParameter;
        }

        @SuppressWarnings("PMD.ArrayIsStoredDirectly")
            //required for test case
        void setNullNotAllowedLuhnArrayParameter(final String... nullNotAllowedLuhnArrayParameter) {
            this.nullNotAllowedLuhnArrayParameter = nullNotAllowedLuhnArrayParameter;
        }

        void setNullNotAllowedLuhnStringParameter(final String nullNotAllowedLuhnStringParameter) {
            this.nullNotAllowedLuhnStringParameter = nullNotAllowedLuhnStringParameter;
        }

        void setNullAllowedUniqueListParameter(final List<String> nullAllowedUniqueListParameter) {
            this.nullAllowedUniqueListParameter = nullAllowedUniqueListParameter;
        }

        void setNullAllowedStringRegex(final String nullAllowedStringRegex) {
            this.nullAllowedStringRegex = nullAllowedStringRegex;
        }


        void setNullNotAllowedUniqueListParameter(final List<String> nullNotAllowedUniqueListParameter) {
            this.nullNotAllowedUniqueListParameter = nullNotAllowedUniqueListParameter;
        }

        void setNullNotAllowedStringRegex(final String nullNotAllowedStringRegex) {
            this.nullNotAllowedStringRegex = nullNotAllowedStringRegex;
        }

        void setNullAllowedIntegerRangeParameter(final Integer nullAllowedIntegerRange) {
            this.nullAllowedIntegerRangeParameter = nullAllowedIntegerRange;
        }

        void setNullNotAllowedIntegerRangeParameter(final Integer nullNotAllowedIntegerRange) {
            this.nullNotAllowedIntegerRangeParameter = nullNotAllowedIntegerRange;
        }
    }
}
