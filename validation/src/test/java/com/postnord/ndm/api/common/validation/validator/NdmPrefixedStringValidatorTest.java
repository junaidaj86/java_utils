package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmPrefixedString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmPrefixedStringValidatorTest {

    private NdmPrefixedStringValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmPrefixedStringValidator();
        @NdmPrefixedString(prefix = "unittest:")
        final class SampleClass {
        } //add prefix to be tested for this test case
        final NdmPrefixedString iotaPrefixedString = SampleClass.class.getAnnotation(NdmPrefixedString.class);
        cut.initialize(iotaPrefixedString);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmPrefixedString(prefix = "testinitialize:")
        final class SampleClass {
        } //inner class hack
        final NdmPrefixedString iotaPrefixedString = SampleClass.class.getAnnotation(NdmPrefixedString.class);
        assertDoesNotThrow(() -> cut.initialize(iotaPrefixedString), "exception should not occur during initialization");
    }

    @Test
     void whenStringIsPrefixedWithValidValueThenItIsConsideredValid() {

        assertTrue(cut.isValid("unittest:valid1", mockConstraintValidatorContext),
                "prefixed string contains a prefixed value, should be valid");
    }

    @Test
     void whenPrefixedStringIsPrefixedWithBlankValueThenItIsNotValid() {

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "prefixed string does not contain a prefixed value, should be not valid");
    }

    @Test
     void whenPrefixedStringIsPrefixedWithIncorrectValueThenItIsNotValid() {

        assertFalse(cut.isValid("invalid_prefix:1234", mockConstraintValidatorContext),
                "prefixed string does not contain a prefixed value, should be not valid");
    }

    @Test
     void whenPrefixedStringIsNullAndNullNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "prefixed string is null, should be not valid");
    }

    @Test
     void whenPrefixedStringIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmPrefixedString(nullAllowed = true, prefix = "testinitialize:")
        final class SampleClass {
        } //inner class hack
        final NdmPrefixedString iotaPrefixedString = SampleClass.class.getAnnotation(NdmPrefixedString.class);
        cut.initialize(iotaPrefixedString);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "prefixed string is null and null is allowed, should be valid");
    }
}
