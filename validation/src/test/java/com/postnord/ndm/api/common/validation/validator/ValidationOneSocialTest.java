package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmAllowedStrings;
import com.postnord.ndm.api.common.validation.constraints.NdmArrayLength;
import com.postnord.ndm.api.common.validation.constraints.NdmCollectionSize;
import com.postnord.ndm.api.common.validation.constraints.NdmCollectionStringLength;
import com.postnord.ndm.api.common.validation.constraints.NdmCollectionStringRegex;
import com.postnord.ndm.api.common.validation.constraints.NdmField;
import com.postnord.ndm.api.common.validation.constraints.NdmJsonMergePatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.json.JsonMergePatch;
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
class ValidationOneSocialTest {

    static final String INVALID_NULL_NOT_ALLOWED_STRING_SIZE_PARAMETER = "invalid nullNotAllowedStringSizeParameter";
    static final String INVALID_NULL_NOT_ALLOWED_COLLECTION_SIZE_PARAMETER = "invalid nullNotAllowedCollectionSizeParameter";
    static final String INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_LENGTH_PARAMETER = "invalid nullNotAllowedCollectionStringLengthParameter";
    static final String INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_REGEX_PARAMETER = "invalid nullNotAllowedCollectionStringRegexParameter";

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

        ts.setNullNotAllowedAcceptedStringsParameter("acceptedString1");
        ts.setNullAllowedAcceptedStringsParameter(null);

        ts.setNullNotAllowedAcceptedStringsParameterWithDefaultMessage("acceptedString3");
        ts.setNullAllowedAcceptedStringsParameterWithDefaultMessage(null);


        ts.setNullNotAllowedCollectionSizeParameter(Stream.of("one:tag", "two:tag").collect(Collectors.toSet()));
        ts.setNullAllowedCollectionSizeParameter(null);

        ts.setNullNotAllowedCollectionStringLengthParameter(Stream.of("fineLength").collect(Collectors.toList()));
        ts.setNullAllowedCollectionStringLengthParameter(null);

        ts.setNullNotAllowedCollectionStringRegexParameter(Stream.of("one:tag", "two:tag").collect(Collectors.toSet()));
        ts.setNullAllowedCollectionStringRegexParameter(null);

        ts.setNullNotAllowedArrayLengthParameter("89430300000482738077", "8935901990831915138F");
        ts.setNullAllowedArrayLengthParameter((String) null);

        ts.setJsonMergePatchParameter(NdmJsonMergePatchValidatorTest.createJsonMergePatch("acceptedField1"));
        ts.setNullAllowedJsonMergePatchParameter(null);
        ts.setEmptyAllowedJsonMergePatchParameter(NdmJsonMergePatchValidatorTest.createJsonMergePatch());

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
    void whenClassHasValidAcceptedStringThenClassShouldBeValid() {

        testStub.setNullNotAllowedAcceptedStringsParameter("acceptedString1");

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInvalidAcceptedStringThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedAcceptedStringsParameter("notAcceptedString");
        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("invalid nullNotAllowedAcceptedStringsParameter"));
    }

    @Test
    void whenClassHasInvalidAcceptedStringThenErrorMessageReturnedShouldContainAllowedValues() {

        testStub.setNullNotAllowedAcceptedStringsParameterWithDefaultMessage("notAcceptedString");
        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("valid values: {'acceptedString3'}"));
    }

    @Test
    void whenClassHasAcceptedStringSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedAcceptedStringsParameterWithDefaultMessage(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("Value not allowed."));
    }

    @Test
    void whenClassHasAcceptedStringSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedAcceptedStringsParameterWithDefaultMessage(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }


    @Test
    void whenClassHasValidCollectionAtUpperLimitSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedCollectionSizeParameter(Stream.of("one", "two", "three").collect(Collectors.toSet())); //set length of 3 should be ok

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInValidCollectionAboveUpperLimitSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedCollectionSizeParameter(Stream.of("one", "two", "three", "four").collect(Collectors.toSet())); //set length of 4 out of range

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_COLLECTION_SIZE_PARAMETER));
    }

    @Test
    void whenClassHasValidCollectionContentsAtUpperLimitSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedCollectionStringLengthParameter(Stream.of("1234567890").collect(Collectors.toList()));

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInvalidCollectionContentsAboveUpperLimitSpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedCollectionStringLengthParameter(Stream.of("1234567890", "1234567890it:two,tag-it:oneTag").collect(Collectors.toSet()));

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_LENGTH_PARAMETER));
    }

    @Test
    void whenClassHasInvalidCollectionContentsNullValueSpecifiedThenClassShouldNotBeValid() {
        testStub.setNullNotAllowedCollectionStringLengthParameter(Stream.of("123", null, "78901").collect(Collectors.toSet()));

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_LENGTH_PARAMETER));
    }

    @Test
    void whenClassHasCollectionSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedCollectionStringLengthParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_LENGTH_PARAMETER));
    }

    @Test
    void whenClassHasCollectionSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedCollectionStringLengthParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasValidCollectionRegexContentsSpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedCollectionStringRegexParameter(Stream.of("it:oneTag").collect(Collectors.toList()));

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInvalidCollectionRegexContentsOfEmptySpecifiedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedCollectionStringRegexParameter(Stream.of("it:oneTag", " ").collect(Collectors.toSet()));

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_REGEX_PARAMETER));
    }

    @Test
    void whenClassHasCollectionRegexSetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedCollectionStringRegexParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains(INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_REGEX_PARAMETER));
    }

    @Test
    void whenClassHasCollectionRegexSetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedCollectionStringRegexParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasValidArraySpecifiedThenClassShouldBeValid() {

        testStub.setNullNotAllowedArrayLengthParameter(new String[]{"89430300000482738077", "1232312431523"});

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInvalidArraySpecifiedBelowLowerBoundThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedArrayLengthParameter(new String[]{});

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
    }

    @Test
    void whenClassHasInvalidArraySpecifiedBelowAboveUpperBoundThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedArrayLengthParameter(new String[]{"1", "2", "3", "4"});

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
    }

    @Test
    void whenClassHasInvalidArraySpecifiedOfNullThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedArrayLengthParameter(null);

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
    }

    @Test
    void whenClassHasArraySetToNullAndNullNotAllowedThenClassShouldNotBeValid() {

        testStub.setNullNotAllowedArrayLengthParameter(null); //null not allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("Array length is out of bounds"));
    }

    @Test
    void whenClassHasArraySetToNullAndNullAllowedThenClassShouldBeValid() {

        testStub.setNullAllowedArrayLengthParameter(null); //null is allowed

        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);
        assertEquals(0, result.size());
    }

    @Test
    void whenClassHasInvalidJsonMergePatchThenClassShouldNotBeValid() {

        testStub.setJsonMergePatchParameter(NdmJsonMergePatchValidatorTest.createJsonMergePatch("notAcceptedField"));
        final Set<ConstraintViolation<TestStub>> result = validator.validate(testStub);

        assertEquals(1, result.size());
        assertTrue(result.iterator().next().getMessage().contains("invalid jsonMergePatchParameter"));
    }

    enum TestEnum {
        ACCEPTED_ENUM_STRING,
        ANOTHER
    }

    @SuppressWarnings("PMD.TooManyFields")
    private class TestStub {
        @Size(min = 2, max = 15, message = INVALID_NULL_NOT_ALLOWED_STRING_SIZE_PARAMETER)
        private String nullNotAllowedStringSizeParameter;
        @NdmAllowedStrings(values = {"acceptedString1"}, message = "invalid nullNotAllowedAcceptedStringsParameter")
        private String nullNotAllowedAcceptedStringsParameter;
        @NdmAllowedStrings(nullAllowed = true, values = {"acceptedString2"}, message = "invalid nullNotAllowedAcceptedStringsParameter")
        private String nullAllowedAcceptedStringsParameter;
        @NdmAllowedStrings(values = {"acceptedString3"})
        private String nullNotAllowedAcceptedStringsParameterWithDefaultMessage;
        @NdmAllowedStrings(nullAllowed = true, values = {"acceptedString4"})
        private String nullAllowedAcceptedStringsParameterWithDefaultMessage;
        @NdmCollectionSize(size = 3, message = INVALID_NULL_NOT_ALLOWED_COLLECTION_SIZE_PARAMETER)
        private Collection<String> nullNotAllowedCollectionSizeParameter;
        @NdmCollectionSize(nullAllowed = true, size = 3, message = INVALID_NULL_NOT_ALLOWED_COLLECTION_SIZE_PARAMETER)
        private Collection<String> nullAllowedCollectionSizeParameter;
        @NdmCollectionStringLength(length = 10, message = INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_LENGTH_PARAMETER)
        private Collection<String> nullNotAllowedCollectionStringLengthParameter;
        @NdmCollectionStringLength(nullAllowed = true, length = 10, message = INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_LENGTH_PARAMETER)
        private Collection<String> nullAllowedCollectionStringLengthParameter;
        @NdmCollectionStringRegex(message = INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_REGEX_PARAMETER)
        private Collection<String> nullNotAllowedCollectionStringRegexParameter;
        @NdmCollectionStringRegex(nullAllowed = true, message = INVALID_NULL_NOT_ALLOWED_COLLECTION_STRING_REGEX_PARAMETER)
        private Collection<String> nullAllowedCollectionStringRegexParameter;
        @NdmArrayLength(min = 1, max = 3)
        private String[] nullNotAllowedArrayLengthParameter;
        @NdmArrayLength(nullAllowed = true, min = 1, max = 3)
        private String[] nullAllowedArrayLengthParameter;
        @NdmJsonMergePatch(fields = @NdmField(name = "acceptedField1"), message = "invalid jsonMergePatchParameter")
        private JsonMergePatch jsonMergePatchParameter;
        @NdmJsonMergePatch(nullAllowed = true, fields = @NdmField(name = "acceptedField2"), message = "invalid nullAllowedJsonMergePatchParameter")
        private JsonMergePatch nullAllowedJsonMergePatchParameter;
        @NdmJsonMergePatch(emptyAllowed = true, fields = @NdmField(name = "acceptedField3"), message = "invalid emptyAllowedJsonMergePatchParameter")
        private JsonMergePatch emptyAllowedJsonMergePatchParameter;

        void setNullAllowedAcceptedStringsParameter(final String nullAllowedAcceptedStringsParameter) {
            this.nullAllowedAcceptedStringsParameter = nullAllowedAcceptedStringsParameter;
        }

        void setNullAllowedAcceptedStringsParameterWithDefaultMessage(final String nullAllowedAcceptedStringsParameterWithDefaultMessage) {
            this.nullAllowedAcceptedStringsParameterWithDefaultMessage = nullAllowedAcceptedStringsParameterWithDefaultMessage;
        }

        void setNullAllowedCollectionSizeParameter(final Collection<String> nullAllowedCollectionSizeParameter) {
            this.nullAllowedCollectionSizeParameter = nullAllowedCollectionSizeParameter;
        }

        void setNullAllowedCollectionStringLengthParameter(final Collection<String> nullAllowedCollectionStringLengthParameter) {
            this.nullAllowedCollectionStringLengthParameter = nullAllowedCollectionStringLengthParameter;
        }

        void setNullAllowedCollectionStringRegexParameter(final Collection<String> nullAllowedCollectionStringRegexParameter) {
            this.nullAllowedCollectionStringRegexParameter = nullAllowedCollectionStringRegexParameter;
        }

        @SuppressWarnings("PMD.ArrayIsStoredDirectly")
            //required to fail validation in test case
        void setNullAllowedArrayLengthParameter(final String... nullAllowedArrayLengthParameter) {

            this.nullAllowedArrayLengthParameter = nullAllowedArrayLengthParameter;
        }

        void setNullNotAllowedStringSizeParameter(final String nullNotAllowedStringSizeParameter) {
            this.nullNotAllowedStringSizeParameter = nullNotAllowedStringSizeParameter;
        }

        void setNullNotAllowedAcceptedStringsParameter(final String nullNotAllowedAcceptedStringsParameter) {
            this.nullNotAllowedAcceptedStringsParameter = nullNotAllowedAcceptedStringsParameter;
        }

        void setNullNotAllowedAcceptedStringsParameterWithDefaultMessage(final String nullNotAllowedAcceptedStringsParameterWithDefaultMessage) {
            this.nullNotAllowedAcceptedStringsParameterWithDefaultMessage = nullNotAllowedAcceptedStringsParameterWithDefaultMessage;
        }

        void setNullNotAllowedCollectionSizeParameter(final Collection<String> nullNotAllowedCollectionSizeParameter) {
            this.nullNotAllowedCollectionSizeParameter = nullNotAllowedCollectionSizeParameter;
        }

        void setNullNotAllowedCollectionStringLengthParameter(final Collection<String> nullNotAllowedCollectionStringLengthParameter) {
            this.nullNotAllowedCollectionStringLengthParameter = nullNotAllowedCollectionStringLengthParameter;
        }

        @SuppressWarnings({"CPD-END"})
        void setNullNotAllowedCollectionStringRegexParameter(final Collection<String> nullNotAllowedCollectionStringRegexParameter) {
            this.nullNotAllowedCollectionStringRegexParameter = nullNotAllowedCollectionStringRegexParameter;
        }

        @SuppressWarnings("PMD.ArrayIsStoredDirectly")
            //required to fail validation in test case
        void setNullNotAllowedArrayLengthParameter(final String... nullNotAllowedArrayLengthParameter) {
            this.nullNotAllowedArrayLengthParameter = nullNotAllowedArrayLengthParameter;
        }

        void setJsonMergePatchParameter(final JsonMergePatch jsonMergePatchParameter) {
            this.jsonMergePatchParameter = jsonMergePatchParameter;
        }

        void setNullAllowedJsonMergePatchParameter(final JsonMergePatch nullAllowedJsonMergePatchParameter) {
            this.nullAllowedJsonMergePatchParameter = nullAllowedJsonMergePatchParameter;
        }

        void setEmptyAllowedJsonMergePatchParameter(final JsonMergePatch emptyAllowedJsonMergePatchParameter) {
            this.emptyAllowedJsonMergePatchParameter = emptyAllowedJsonMergePatchParameter;
        }
    }
}
