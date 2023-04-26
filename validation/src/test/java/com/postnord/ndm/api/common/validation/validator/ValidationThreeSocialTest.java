package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmDatePatterns;
import com.postnord.ndm.api.common.validation.constraints.NdmEnum;
import com.postnord.ndm.api.common.validation.constraints.NdmSet;
import com.postnord.ndm.api.common.validation.constraints.NdmSetLength;
import com.postnord.ndm.api.common.validation.constraints.NdmString;
import com.postnord.ndm.api.common.validation.constraints.NdmStringLength;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Size;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/*
Sociable unit test : This class will collaborate with its dependencies and will NOT mock them. It will use
the real collaborator jakarta.validation.Validator as part of its test scope.
 */
@SuppressWarnings({"CPD-START"})
 class ValidationThreeSocialTest {

     static final String INVALID_NULL_NOT_ALLOWED_STRING_SIZE_PARAMETER = "invalid nullNotAllowedStringSizeParameter";
     static final String INVALID_NULL_NOT_ALLOWED_SET_LENGTH_PARAMETER = "invalid nullNotAllowedSetLengthParameter";
     static final String INVALID_NULL_NOT_ALLOWED_SIMPLE_SET_PARAMETER = "invalid nullNotAllowedSimpleSetParameter";
     static final String INVALID_NULL_NOT_ALLOWED_SIMPLE_STRING_PARAMETER = "invalid nullNotAllowedSimpleStringParameter";
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

        ts.setNullNotAllowedSetLengthParameter(Stream.of("one:tag", "two:tag").collect(Collectors.toSet()));
        ts.setNullAllowedSetLengthParameter(null);

        ts.setNullNotAllowedSimpleSetParameter(Stream.of("one:tag", "two:tag").collect(Collectors.toSet()));
        ts.setNullAllowedSimpleSetParameter(null);

        ts.setNullNotAllowedSimpleStringParameter("i exist");
        ts.setNullAllowedSimpleStringParameter(null);

        ts.setNullNotAllowedStringLengthParameter("1234");
        ts.setNullAllowedStringLengthParameter(null);

        ts.setNullNotAllowedSimpleEnumParameter("ACCEPTED_ENUM_STRING");
        ts.setNullAllowedSimpleEnumParameter(null);

        ts.setNullNotAllowedSimpleEnumParameterWithDefaultMessage("ACCEPTED_ENUM_STRING");
        ts.setNullAllowedSimpleEnumParameterWithDefaultMessage(null);

        ts.setNullNotAllowedDatePatterns("2012-11-12");
        ts.setNullAllowedDatePatterns(null);

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
     void whenClassHasSetLengthSetToUpperBoundThenClassShouldBeValid() {

        testStub.setNullNotAllowedSetLengthParameter(Stream.of("one", "two", "three").collect(Collectors.toSet())); //set length of 3 should be ok

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasSetLengthAboveUpperBoundThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedSetLengthParameter(Stream.of("one", "two", "three", "four").collect(Collectors.toSet())); //set length of 4 out of range

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_SET_LENGTH_PARAMETER));
    }

    @Test
     void whenClassHasSetLengthSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedSetLengthParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_SET_LENGTH_PARAMETER));
    }

    @Test
     void whenClassHasSetLengthSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedSetLengthParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasSetSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedSimpleSetParameter(
                Stream.of("one:tag", "two:tag", "three:tag").collect(Collectors.toSet())); //mandatory set of 3 should be ok

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasEmptySetSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedSimpleSetParameter(Collections.emptySet()); //empty set should be invalid

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_SIMPLE_SET_PARAMETER));
    }

    @Test
     void whenClassHasSetSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedSimpleSetParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_SIMPLE_SET_PARAMETER));
    }

    @Test
     void whenClassHasSetSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedSimpleSetParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasStringSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedSimpleStringParameter("one");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasEmptyStringSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedSimpleStringParameter(""); //empty string should be invalid

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_SIMPLE_STRING_PARAMETER));
    }

    @Test
     void whenClassHasStringSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedSimpleStringParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_SIMPLE_STRING_PARAMETER));
    }

    @Test
     void whenClassHasStringSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedSimpleStringParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }


    @Test
     void whenClassHasValidEnumParamsSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedSimpleEnumParameter("ACCEPTED_ENUM_STRING");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInValidEnumParamsSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedSimpleEnumParameter("NOT_ACCEPTED_ENUM_STRING");
        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("invalid nullNotAllowedSimpleEnumParameter"));
    }

    @Test
     void whenClassHasInValidEnumParamsSpecifiedThenErrorMessageReturnedShouldContainAllowedValues() {

        testStub.setNullNotAllowedSimpleEnumParameterWithDefaultMessage("NOT_ACCEPTED_ENUM_STRING");
        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("valid values: {'ACCEPTED_ENUM_STRING','ANOTHER'}"));
    }

    @Test
     void whenClassHasEnumSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedSimpleEnumParameterWithDefaultMessage(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("valid values: {'ACCEPTED_ENUM_STRING','ANOTHER'}"));
    }

    @Test
     void whenClassHasEnumSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedSimpleEnumParameterWithDefaultMessage(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasValidDateSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedDatePatterns("2017-12-11");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasValidDateTimeSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedDatePatterns("2017-12-11T00:00:00.000Z");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
     void whenClassHasInValidDateSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedDatePatterns("2017/12/11");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("invalid nullNotAllowedDatePatterns pattern"));
    }

    @Test
     void whenClassHasDateSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedDatePatterns(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("invalid nullNotAllowedDatePatterns"));
    }

    @Test
     void whenClassHasDateSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedDatePatterns(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    private class TestStub {
        @Size(min = 2, max = 15, message = INVALID_NULL_NOT_ALLOWED_STRING_SIZE_PARAMETER)
        private String nullNotAllowedStringSizeParameter;
        @NdmSetLength(length = 3, message = INVALID_NULL_NOT_ALLOWED_SET_LENGTH_PARAMETER)
        private Set<String> nullNotAllowedSetLengthParameter;
        @NdmSetLength(nullAllowed = true, length = 3, message = INVALID_NULL_NOT_ALLOWED_SET_LENGTH_PARAMETER)
        private Set<String> nullAllowedSetLengthParameter;
        @NdmString(message = INVALID_NULL_NOT_ALLOWED_SIMPLE_STRING_PARAMETER)
        private String nullNotAllowedSimpleStringParameter;
        @NdmString(nullAllowed = true, message = INVALID_NULL_NOT_ALLOWED_SIMPLE_STRING_PARAMETER)
        private String nullAllowedSimpleStringParameter;
        @NdmStringLength(nullAllowed = true, length = 5, message = "invalid nullNotAllowedStringLengthParameter")
        private String nullNotAllowedStringLengthParameter;
        @NdmStringLength(nullAllowed = true, length = 5, message = "invalid nullNotAllowedStringLengthParameter")
        private String nullAllowedStringLengthParameter;
        @NdmSet(message = INVALID_NULL_NOT_ALLOWED_SIMPLE_SET_PARAMETER)
        private Set<String> nullNotAllowedSimpleSetParameter;
        @NdmSet(nullAllowed = true, message = INVALID_NULL_NOT_ALLOWED_SIMPLE_SET_PARAMETER)
        private Set<String> nullAllowedSimpleSetParameter;
        @NdmEnum(nullAllowed = true, enumClass = ValidationOneSocialTest.TestEnum.class, message = "invalid nullNotAllowedSimpleEnumParameter")
        private String nullNotAllowedSimpleEnumParameter;
        @NdmEnum(nullAllowed = true, enumClass = ValidationOneSocialTest.TestEnum.class, message = "invalid nullNotAllowedSimpleEnumParameter")
        private String nullAllowedSimpleEnumParameter;
        @NdmEnum(enumClass = ValidationOneSocialTest.TestEnum.class)
        private String nullNotAllowedSimpleEnumParameterWithDefaultMessage;
        @NdmEnum(nullAllowed = true, enumClass = ValidationOneSocialTest.TestEnum.class)
        private String nullAllowedSimpleEnumParameterWithDefaultMessage;
        @NdmDatePatterns(patterns = {"yyyy-MM-dd'T'hh:mm:ss.SSSz", "yyyy-MM-dd"}, message = "invalid nullNotAllowedDatePatterns pattern")
        private String nullNotAllowedDatePatterns;
        @NdmDatePatterns(nullAllowed = true, patterns = {"yyyy-MM-dd'T'hh:mm:ss.SSSz", "yyyy-MM-dd"}, message = "invalid nullNotAllowedDatePatterns pattern")
        private String nullAllowedDatePatterns;

        void setNullAllowedSimpleEnumParameter(final String nullAllowedSimpleEnumParameter) {
            this.nullAllowedSimpleEnumParameter = nullAllowedSimpleEnumParameter;
        }

        void setNullAllowedSimpleEnumParameterWithDefaultMessage(final String nullAllowedSimpleEnumParameterWithDefaultMessage) {
            this.nullAllowedSimpleEnumParameterWithDefaultMessage = nullAllowedSimpleEnumParameterWithDefaultMessage;
        }

        void setNullAllowedDatePatterns(final String nullAllowedDatePatterns) {
            this.nullAllowedDatePatterns = nullAllowedDatePatterns;
        }

        void setNullNotAllowedSimpleEnumParameter(final String nullNotAllowedSimpleEnumParameter) {
            this.nullNotAllowedSimpleEnumParameter = nullNotAllowedSimpleEnumParameter;
        }

        void setNullNotAllowedSimpleEnumParameterWithDefaultMessage(final String nullNotAllowedSimpleEnumParameterWithDefaultMessage) {
            this.nullNotAllowedSimpleEnumParameterWithDefaultMessage = nullNotAllowedSimpleEnumParameterWithDefaultMessage;
        }

        void setNullNotAllowedDatePatterns(final String nullNotAllowedDatePatterns) {
            this.nullNotAllowedDatePatterns = nullNotAllowedDatePatterns;
        }

        void setNullAllowedSetLengthParameter(final Set<String> nullAllowedSetLengthParameter) {
            this.nullAllowedSetLengthParameter = nullAllowedSetLengthParameter;
        }

        void setNullAllowedSimpleStringParameter(final String nullAllowedSimpleStringParameter) {
            this.nullAllowedSimpleStringParameter = nullAllowedSimpleStringParameter;
        }

        void setNullAllowedStringLengthParameter(final String nullAllowedStringLengthParameter) {
            this.nullAllowedStringLengthParameter = nullAllowedStringLengthParameter;
        }

        void setNullAllowedSimpleSetParameter(final Set<String> nullAllowedSimpleSetParameter) {
            this.nullAllowedSimpleSetParameter = nullAllowedSimpleSetParameter;
        }


        void setNullNotAllowedStringSizeParameter(final String nullNotAllowedStringSizeParameter) {
            this.nullNotAllowedStringSizeParameter = nullNotAllowedStringSizeParameter;
        }

        void setNullNotAllowedSetLengthParameter(final Set<String> nullNotAllowedSetLengthParameter) {
            this.nullNotAllowedSetLengthParameter = nullNotAllowedSetLengthParameter;
        }

        void setNullNotAllowedSimpleSetParameter(final Set<String> nullNotAllowedSimpleSetParameter) {
            this.nullNotAllowedSimpleSetParameter = nullNotAllowedSimpleSetParameter;
        }

        void setNullNotAllowedSimpleStringParameter(final String nullNotAllowedSimpleStringParameter) {
            this.nullNotAllowedSimpleStringParameter = nullNotAllowedSimpleStringParameter;
        }

        @SuppressWarnings({"CPD-END"})
        void setNullNotAllowedStringLengthParameter(final String nullNotAllowedStringLengthParameter) {
            this.nullNotAllowedStringLengthParameter = nullNotAllowedStringLengthParameter;
        }
    }
}
