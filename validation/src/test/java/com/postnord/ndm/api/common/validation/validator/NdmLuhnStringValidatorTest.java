package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmLuhnString;

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
 class NdmLuhnStringValidatorTest {

    private NdmLuhnStringValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmLuhnStringValidator();
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmLuhnString
        final class SampleClass {
        } //inner class hack
        final NdmLuhnString iotaLuhnString = SampleClass.class.getAnnotation(NdmLuhnString.class);
        assertDoesNotThrow(() -> cut.initialize(iotaLuhnString), "exception should not occur during initialization");
    }

    @Test
     void whenLuhnIsNullAndNullIsNotAllowedThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "String is null, should be considered invalid");
    }

    @Test
     void whenLuhnIsEmptyThenItIsConsideredNotValid() {

        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "String is empty, should be considered invalid");
    }

    @Test
     void whenLuhnContainsNonNumericThenItIsConsideredNotValid() {

        assertFalse(cut.isValid("123NonNumeric456", mockConstraintValidatorContext),
                "String is NonNumeric, should be considered invalid");
    }

    @Test
     void whenLuhnIsInvalidThenItIsConsideredNotValid() {

        assertFalse(cut.isValid("8965880812100011149", mockConstraintValidatorContext),
                "String is not valid luhn, should be considered invalid");
    }

    @Test
     void whenLuhnIsCorrect1ThenItIsConsideredValid() {

        assertTrue(cut.isValid("89374101107054446111", mockConstraintValidatorContext),
                "String is valid iccid from Armenia - Orange, should be considered valid");

        assertTrue(cut.isValid("89610300000913467525", mockConstraintValidatorContext),
                "String is valid iccid from Australia - Vodafone, should be considered valid");

        assertTrue(cut.isValid("89430300000482738077", mockConstraintValidatorContext),
                "String is valid iccid from Austria - T-Mobile, should be considered valid");

        assertTrue(cut.isValid("8935901990831915138F", mockConstraintValidatorContext),
                "String is valid iccid from Belgium - MTel, should be considered valid");

        assertTrue(cut.isValid("89302370107250764577", mockConstraintValidatorContext),
                "String is valid iccid from Canada - Fido - 1, should be considered valid");
    }

    @Test
     void whenLuhnIsCorrect2ThenItIsConsideredValid() {

        assertTrue(cut.isValid("8912230000083198101F", mockConstraintValidatorContext),
                "String is valid iccid from Canada - Koodo mobile, should be considered valid");

        assertTrue(cut.isValid("89302720400069135288", mockConstraintValidatorContext),
                "String is valid iccid from Canada - Rogers - 1, should be considered valid");

        assertTrue(cut.isValid("89302720400054365635", mockConstraintValidatorContext),
                "String is valid iccid from Canada - Rogers - 2, should be considered valid");

        assertTrue(cut.isValid("8912230000016694630F", mockConstraintValidatorContext),
                "String is valid iccid from Canada - Telus, should be considered valid");

        assertTrue(cut.isValid("8920016102106180380F", mockConstraintValidatorContext),
                "String is valid iccid from Egypt - Mobinil, should be considered valid");
    }

    @Test
     void whenLuhnIsCorrect3ThenItIsConsideredValid() {

        assertTrue(cut.isValid("89302370200009357981", mockConstraintValidatorContext),
                "String is valid iccid from Canada - Fido - 2, should be considered valid");

        assertTrue(cut.isValid("8933202008004664218F", mockConstraintValidatorContext),
                "String is valid iccid from France - Bouygues - 1, should be considered valid");

        assertTrue(cut.isValid("8933201509510610042F", mockConstraintValidatorContext),
                "String is valid iccid from France - Bouygues - 2, should be considered valid");

        assertTrue(cut.isValid("89331036100249024406", mockConstraintValidatorContext),
                "String is valid iccid from France - SFR, should be considered valid");

        assertTrue(cut.isValid("89995010905205678256", mockConstraintValidatorContext),
                "String is valid iccid from Georgia - Geocell, should be considered valid");
    }

    @Test
     void whenLuhnIsCorrect4ThenItIsConsideredValid() {

        assertTrue(cut.isValid("89490240001560917838", mockConstraintValidatorContext),
                "String is valid iccid from Germany - T-Mobile - 1, should be considered valid");

        assertTrue(cut.isValid("89490240001449542484", mockConstraintValidatorContext),
                "String is valid iccid from Germany - T-Mobile - 2, should be considered valid");

        assertTrue(cut.isValid("8936200003650812489F", mockConstraintValidatorContext),
                "String is valid iccid from Hungary - Telenor, should be considered valid");

        assertTrue(cut.isValid("8936303411030088744F", mockConstraintValidatorContext),
                "String is valid iccid from Hungary - T-Mobile, should be considered valid");

        assertTrue(cut.isValid("89367031551050308826", mockConstraintValidatorContext),
                "String is valid iccid from Hungary - Vodafone, should be considered valid");
    }

    @Test
     void whenLuhnIsCorrect5ThenItIsConsideredValid() {

        assertTrue(cut.isValid("89390100001351053257", mockConstraintValidatorContext),
                "String is valid iccid from Italy – TIM, should be considered valid");

        assertTrue(cut.isValid("89373011203035680646", mockConstraintValidatorContext),
                "String is valid iccid from Moldova - Orange, should be considered valid");

        assertTrue(cut.isValid("89351060000401155070", mockConstraintValidatorContext),
                "String is valid iccid from Portugal - TMN, should be considered valid");

        assertTrue(cut.isValid("8940100902090012514F", mockConstraintValidatorContext),
                "String is valid iccid from Romania - Orange - 1, should be considered valid");

        assertTrue(cut.isValid("8940100707122080961F", mockConstraintValidatorContext),
                "String is valid iccid from Romania - Orange - 2, should be considered valid");
    }

    @Test
     void whenLuhnIsCorrect6ThenItIsConsideredValid() {

        assertTrue(cut.isValid("8934072100223180806F", mockConstraintValidatorContext),
                "String is valid iccid from Spain - Movistar - 1, should be considered valid");

        assertTrue(cut.isValid("8934071100153825611F", mockConstraintValidatorContext),
                "String is valid iccid from Spain - Movistar - 2, should be considered valid");

        assertTrue(cut.isValid("8934076100042173285F", mockConstraintValidatorContext),
                "String is valid iccid from Spain - Movistar- 3, should be considered valid");

        assertTrue(cut.isValid("89460800000105696662", mockConstraintValidatorContext),
                "String is valid iccid from Sweden - PostNord - 1, should be considered valid");

        assertTrue(cut.isValid("89450421160216002687", mockConstraintValidatorContext),
                "String is valid iccid from Sweden - PostNord - 2, should be considered valid");
    }

    @Test
     void whenLuhnIsCorrect7ThenItIsConsideredValid() {

        assertTrue(cut.isValid("89430301721711120568", mockConstraintValidatorContext),
                "String is valid iccid from Sweden - Telia - 1, should be considered valid");

        assertTrue(cut.isValid("89450421161209000076", mockConstraintValidatorContext),
                "String is valid iccid from Sweden - Telia - 2, should be considered valid");

        assertTrue(cut.isValid("89450421160216006415", mockConstraintValidatorContext),
                "String is valid iccid from Sweden - Telia - 3, should be considered valid");

        assertTrue(cut.isValid("89462044110049592083", mockConstraintValidatorContext),
                "String is valid iccid from Sweden - Tele 2, should be considered valid");

        assertTrue(cut.isValid("8981200008280088190", mockConstraintValidatorContext),
                "String is valid iccid from Switzerland - Swisscom, should be considered valid");
    }

    @Test
     void whenLuhnIsCorrect8ThenItIsConsideredValid() {

        assertTrue(cut.isValid("8944110065012251410F", mockConstraintValidatorContext),
                "String is valid iccid from UK - O2 - 1, should be considered valid");

        assertTrue(cut.isValid("8944110065364359399F", mockConstraintValidatorContext),
                "String is valid iccid from UK - O2 - 2, should be considered valid");

        assertTrue(cut.isValid("8944125212080147905F", mockConstraintValidatorContext),
                "String is valid iccid from UK - Orange - 1, should be considered valid");

        assertTrue(cut.isValid("8944121215169237673F", mockConstraintValidatorContext),
                "String is valid iccid from UK - Orange - 2, should be considered valid");

        assertTrue(cut.isValid("8944110063835210853F", mockConstraintValidatorContext),
                "String is valid iccid from UK - Tesco, should be considered valid");
    }


    @Test
     void whenLuhnIsCorrect9ThenItIsConsideredValid() {

        assertTrue(cut.isValid("8944010000105997299F", mockConstraintValidatorContext),
                "String is valid iccid from UK - Vectone Mobile, should be considered valid");

        assertTrue(cut.isValid("89441000300522866692", mockConstraintValidatorContext),
                "String is valid iccid from UK - Vodafone - 1, should be considered valid");

        assertTrue(cut.isValid("89441000300691741692", mockConstraintValidatorContext),
                "String is valid iccid from UK - Vodafone - 2, should be considered valid");

        assertTrue(cut.isValid("9810100080774107449F", mockConstraintValidatorContext),
                "String is valid iccid from USA - AT&T, should be considered valid");

        assertTrue(cut.isValid("8901010008116017080F", mockConstraintValidatorContext),
                "String is valid iccid from USA - Sprint - 1, should be considered valid");
    }

    @Test
     void whenLuhnIsCorrect10ThenItIsConsideredValid() {

        assertTrue(cut.isValid("8901010008773150752F", mockConstraintValidatorContext),
                "String is valid iccid from USA - Sprint - 2, should be considered valid");

        assertTrue(cut.isValid("8901010008894439704F", mockConstraintValidatorContext),
                "String is valid iccid from USA - Sprint - 3, should be considered valid");

        assertTrue(cut.isValid("8931440880649553246F", mockConstraintValidatorContext),
                "String is valid iccid from Verizon, should be considered valid");

        assertTrue(cut.isValid("8965880812100011146", mockConstraintValidatorContext),
                "String is valid iccid from Ireland - 3, should be considered valid");
    }

    @Test
     void whenLuhnIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmLuhnString(nullAllowed = true)
        final class SampleClass {
        } //inner class hack
        final NdmLuhnString iotaLuhnString = SampleClass.class.getAnnotation(NdmLuhnString.class);
        cut.initialize(iotaLuhnString);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "luhn is null and null is allowed, should be valid");
    }
}
