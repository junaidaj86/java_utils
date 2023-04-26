package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmUuId4String;

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
 class NdmUuId4StringValidatorTest {

    private NdmUuId4StringValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmUuId4StringValidator();
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmUuId4String
        final class SampleClass {
        }
        final NdmUuId4String iotaUuId4String = SampleClass.class.getAnnotation(NdmUuId4String.class);
        assertDoesNotThrow(() -> cut.initialize(iotaUuId4String), "exception should not occur during initialization");
    }

    @Test
     void whenUuidIsNullAndNullIsNotAllowedThenItIsNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "Uuid is invalid (null not allowed)");
    }

    @Test
     void whenUuidIsEmptyThenItIsNotValid() {

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "Uuid is empty, should be considered invalid");
    }

    @Test
     void whenUuidHasInvalidCharacter1ThenItIsNotValid() {

        assertFalse(cut.isValid("111111h1-1111-1111-1111-111111111111", mockConstraintValidatorContext),
                "Uuid is not valid Uuid, should be considered invalid");
    }

    @Test
     void whenUuidHasInvalidCharacter2ThenItIsNotValid() {

        assertFalse(cut.isValid("-1111111-1111-11111111-1111-111111111111", mockConstraintValidatorContext),
                "Uuid is not a valid Uuid, should be considered invalid");
    }

    @Test
     void whenUuidHasInvalidCharacter3ThenItIsNotValid() {

        assertFalse(cut.isValid("#1111111-1111-11111111-1111-111111111111", mockConstraintValidatorContext),
                "Uuid is not valid Uuid, should be considered invalid");
    }

    @Test
     void whenUuidHasInvalidCharacter4ThenItIsNotValid() {

        assertFalse(cut.isValid("[1111111-1111-11111111-1111-111111111111", mockConstraintValidatorContext),
                "Uuid is not valid Uuid, should be considered invalid");
    }

    @Test
     void whenUuidHasInvalidLength1ThenItIsNotValid() {

        assertFalse(cut.isValid("00011111111-1111-1111-1111-111111111111", mockConstraintValidatorContext),
                "Uuid is not valid Uuid (invalid length), should be considered invalid");
    }

    @Test
     void whenUuidHasInvalidLength2ThenItIsNotValid() {

        assertFalse(cut.isValid("11111111-111111-1111-1111-111111111111", mockConstraintValidatorContext),
                "Uuid is not valid Uuid (invalid length), should be considered invalid");
    }

    @Test
     void whenUuidHasInvalidLength3ThenItIsNotValid() {

        assertFalse(cut.isValid("11111111-1111-11111111-1111-111111111111", mockConstraintValidatorContext),
                "Uuid is not valid Uuid, (invalid length middle) should be considered invalid");
    }

    @Test
     void whenUuidIsCorrectThenItIsValid() {

        assertTrue(cut.isValid("11111111-1111-1111-1111-111111111111", mockConstraintValidatorContext),
                "One correct Uuid strings, should be invalid");
    }

    @Test
     void whenUuidIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmUuId4String(nullAllowed = true)
        final class SampleClass {
        }
        final NdmUuId4String iotaUUID4String = SampleClass.class.getAnnotation(NdmUuId4String.class);
        cut.initialize(iotaUUID4String);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "uuid is null and null is allowed, should be valid");
    }
}
