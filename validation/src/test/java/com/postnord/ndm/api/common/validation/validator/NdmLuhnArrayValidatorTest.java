package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmLuhnArray;

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
 class NdmLuhnArrayValidatorTest {

     static final String VALID_LUHN = "89430300000482738077";
    private NdmLuhnArrayValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmLuhnArrayValidator();
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmLuhnArray
        final class SampleClass {
        }
        final NdmLuhnArray iotaLuhnArray = SampleClass.class.getAnnotation(NdmLuhnArray.class);
        assertDoesNotThrow(() -> cut.initialize(iotaLuhnArray), "exception should not occur during initialization");
    }

    @Test
     void whenLuhnIsNullAndNullNotAllowedThenItIsShouldBeNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "Array is null, should be considered invalid");
    }

    @Test
     void whenLuhnIsEmptyThenItIsShouldBeNotValid() {

        assertFalse(cut.isValid(new String[]{""}, mockConstraintValidatorContext),
                "String is empty, should be considered invalid");
    }

    @Test
     void whenLuhnIsEmptyArrayThenItIsShouldBeValid() {

        assertTrue(cut.isValid(new String[]{}, mockConstraintValidatorContext),
                "Array is empty, should be considered valid");
    }

    @Test
     void whenLuhnIsInvalidThenItShouldBeNotValid() {

        assertFalse(cut.isValid(new String[]{"8965880812100011149"}, mockConstraintValidatorContext),
                "String is not valid luhn, should be considered invalid");
    }

    @Test
     void whenLuhnContainsNonNumericThenItShouldBeNotValid() {

        assertFalse(cut.isValid(new String[]{VALID_LUHN, "8935901990831915138F", "8935901WRONG1915138F"}, mockConstraintValidatorContext),
                "String is NonNumeric, should be considered invalid");
    }

    @Test
     void whenMultipleLuhnIsCorrectThenItShouldBeValid() {

        assertTrue(cut.isValid(new String[]{VALID_LUHN, "8935901990831915138F", "89302370107250764577", "89302370200009357981", "8912230000083198101F", "89302720400069135288"}, mockConstraintValidatorContext),
                "Multiple correct luhn strings, should be valid");
    }

    @Test
     void whenMultipleLuhnIsCorrectAndMultipleIncorrectThenItShouldBeNotValid() {

        assertFalse(cut.isValid(new String[]{VALID_LUHN, "8935901990831915138F", "89302370107250764577", "89302370200009357981", "8151515123", "8965880812100011149"}, mockConstraintValidatorContext),
                "Two incorrect luhn strings, should be invalid");
    }

    @Test
     void whenSingleLuhnIsCorrectThenItShouldBeValid() {

        assertTrue(cut.isValid(new String[]{VALID_LUHN}, mockConstraintValidatorContext),
                "One correct luhn strings, should be valid");
    }

    @Test
     void whenSingleLuhnIsCorrectAndSingleIncorrectThenItShouldBeNotValid() {

        assertFalse(cut.isValid(new String[]{VALID_LUHN, "8151515123"}, mockConstraintValidatorContext),
                "String is valid iccid from Austria - T-Mobile, should be considered valid");
    }

    @Test
     void whenNullableSetAndNullableValueSpecifiedThenItShouldBeValid() {
        @NdmLuhnArray(nullAllowed = true)
        final class SampleClass {
        }
        final NdmLuhnArray iotaLuhnArray = SampleClass.class.getAnnotation(NdmLuhnArray.class);
        cut.initialize(iotaLuhnArray);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "Array is null, should be considered valid");
    }
}
