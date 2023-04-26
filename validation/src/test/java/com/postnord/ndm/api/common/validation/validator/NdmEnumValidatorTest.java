package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmEnum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class NdmEnumValidatorTest {

    private NdmEnumValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder mockConstraintViolationBuilder;

    @BeforeEach
     void setUp() {
        @NdmEnum(enumClass = TestEnum.class)
        final class SampleClass {
        }

        final NdmEnum iotaEnum = SampleClass.class.getAnnotation(NdmEnum.class);

        cut = new NdmEnumValidator();
        cut.initialize(iotaEnum);
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmEnum(enumClass = TestEnum.class)
        final class SampleClass {
        }
        final NdmEnum iotaEnum = SampleClass.class.getAnnotation(NdmEnum.class);
        assertDoesNotThrow(() -> cut.initialize(iotaEnum), "exception should not occur during initialization");
    }

    @Test
     void whenEnumStringIsInEnumThenItIsValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmEnum.DEFAULT_MESSAGE);

        assertTrue(cut.isValid("dummy_Field", mockConstraintValidatorContext),
                "string is in enum, should be valid");
    }

    @Test
     void whenEnumStringIsInEnumButDifferentCaseThenItIsValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmEnum.DEFAULT_MESSAGE);

        assertTrue(cut.isValid("duMMy_FiELd", mockConstraintValidatorContext),
                "string is in enum but other case, should be valid");
    }

    @Test
     void whenEnumStringIsNotInEnumThenItIsNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmEnum.DEFAULT_MESSAGE);

        assertFalse(cut.isValid("notInEnumString", mockConstraintValidatorContext),
                "string is not in enum, should be not valid");
    }

    @Test
     void whenEnumStringIsFilledWithBlankThenItIsNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmEnum.DEFAULT_MESSAGE);

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "string is blank, should be invalid");
    }

    @Test
     void whenEnumStringIsNullAndNullNotAllowedThenItIsNotValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmEnum.DEFAULT_MESSAGE);

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "string is null, should be invalid");
    }

    @Test
     void whenEnumStringIsInvalidThenAHumanReadableCustomMessageIsReturned() {
        @NdmEnum(enumClass = TestEnum.class, message = "custom")
        final class SampleClass {
        }
        final NdmEnum anNdmEnum = SampleClass.class.getAnnotation(NdmEnum.class);
        cut = new NdmEnumValidator();
        cut.initialize(anNdmEnum);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn("custom");
        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "string is blank, should be invalid");
    }

    @Test
     void whenEnumStringIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmEnum(nullAllowed = true, enumClass = TestEnum.class)
        final class SampleClass {
        }
        final NdmEnum anNdmEnum = SampleClass.class.getAnnotation(NdmEnum.class);
        cut.initialize(anNdmEnum);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "string is null and null is allowed, should be valid");
    }

    enum TestEnum {
        DUMMY_FIELD,
        ANOTHER_FIELD
    }
}
