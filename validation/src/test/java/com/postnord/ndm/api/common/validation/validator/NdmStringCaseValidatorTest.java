package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmStringCaseCheck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("PMD.UnusedPrivateMethod")
        // Suppressed as the functions are called using MethodSource annotation
class NdmStringCaseValidatorTest {

    private NdmStringCaseValidator cut;
    private final static String VALID_STR = "valid string";
    private final static String INVALID_STR = "Invalid string";
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmStringCaseValidator();
        @NdmStringCaseCheck
        final class SampleClass {
        }
        final NdmStringCaseCheck iotaStringCaseCheck = SampleClass.class.getAnnotation(NdmStringCaseCheck.class);
        cut.initialize(iotaStringCaseCheck);
    }

    @ParameterizedTest
    @MethodSource("provideValidEmptyStringForLowerCase")
    void shouldBeValidStringsWithEmptyStringAndRequireLowerCaseFormatTrue(final String name, final String message) {
        @NdmStringCaseCheck(requireLowerCase = true, nullAllowed = true, emptyAllowed = true)
        final class SampleClass {
        }
        final NdmStringCaseCheck iotaStringCaseCheck = SampleClass.class.getAnnotation(NdmStringCaseCheck.class);
        cut = new NdmStringCaseValidator();
        cut.initialize(iotaStringCaseCheck);

        assertTrue(cut.isValid(name, mockConstraintValidatorContext), message);
    }

    @ParameterizedTest
    @MethodSource("provideValidLowerCaseString")
    void shouldBeValidStringsWithRequireLowerCaseFormatTrue(final String name, final String message) {
        @NdmStringCaseCheck(requireLowerCase = true)
        final class SampleClass {
        }
        final NdmStringCaseCheck iotaStringCaseCheck = SampleClass.class.getAnnotation(NdmStringCaseCheck.class);
        cut = new NdmStringCaseValidator();
        cut.initialize(iotaStringCaseCheck);

        assertTrue(cut.isValid(name, mockConstraintValidatorContext), message);
    }

    @ParameterizedTest
    @MethodSource("provideInValidMixedCaseString")
    void shouldBeInValidStringsWithRequireLowerCaseFormatTrue(final String name, final String message) {
        @NdmStringCaseCheck(requireLowerCase = true)
        final class SampleClass {
        }
        final NdmStringCaseCheck iotaStringCaseCheck = SampleClass.class.getAnnotation(NdmStringCaseCheck.class);
        cut = new NdmStringCaseValidator();
        cut.initialize(iotaStringCaseCheck);

        assertFalse(cut.isValid(name, mockConstraintValidatorContext), message);
    }

    @ParameterizedTest
    @MethodSource("provideValidUpperCaseString")
    void shouldBeValidStringsWithRequireUpperCaseFormatTrue(final String name, final String message) {
        @NdmStringCaseCheck(requireUpperCase = true)
        final class SampleClass {
        }
        final NdmStringCaseCheck iotaStringCaseCheck = SampleClass.class.getAnnotation(NdmStringCaseCheck.class);
        cut = new NdmStringCaseValidator();
        cut.initialize(iotaStringCaseCheck);

        assertTrue(cut.isValid(name, mockConstraintValidatorContext), message);
    }

    @ParameterizedTest
    @MethodSource("provideInValidMixedCaseString")
    void shouldBeInValidStringsWithRequireUpperCaseFormatTrue(final String name, final String message) {
        @NdmStringCaseCheck(requireUpperCase = true)
        final class SampleClass {
        }
        final NdmStringCaseCheck iotaStringCaseCheck = SampleClass.class.getAnnotation(NdmStringCaseCheck.class);
        cut = new NdmStringCaseValidator();
        cut.initialize(iotaStringCaseCheck);

        assertFalse(cut.isValid(name, mockConstraintValidatorContext), message);
    }

    @ParameterizedTest
    @MethodSource("provideValidMixedCaseString")
    void shouldBeValidStringsWithRequireLowerCaseFormatTrueAndUpperCaseFormatTrue(final String name, final String message) {
        //if both the values were given as true then it will accept upper case or lower case
        @NdmStringCaseCheck(requireLowerCase = true, requireUpperCase = true)
        final class SampleClass {
        }
        final NdmStringCaseCheck iotaStringCaseCheck = SampleClass.class.getAnnotation(NdmStringCaseCheck.class);
        cut = new NdmStringCaseValidator();
        cut.initialize(iotaStringCaseCheck);

        assertTrue(cut.isValid(name, mockConstraintValidatorContext), message);
    }

    private static Stream<Arguments> provideValidLowerCaseString() {
        return Stream.of(
                Arguments.of("sample", VALID_STR),
                Arguments.of("sample%%", VALID_STR),
                Arguments.of("sample@@", VALID_STR)
        );
    }

    private static Stream<Arguments> provideValidEmptyStringForLowerCase() {
        return Stream.of(
                Arguments.of(" ", VALID_STR),
                Arguments.of(null, VALID_STR),
                Arguments.of("", VALID_STR),
                Arguments.of("sample", VALID_STR)
        );
    }

    private static Stream<Arguments> provideInValidMixedCaseString() {
        return Stream.of(
                Arguments.of("Sample", INVALID_STR),
                Arguments.of("Sample%%", INVALID_STR),
                Arguments.of("#$Sample@@", INVALID_STR)
        );
    }

    private static Stream<Arguments> provideValidUpperCaseString() {
        return Stream.of(
                Arguments.of("SAMPLE", VALID_STR),
                Arguments.of("SAMPLE!", VALID_STR),
                Arguments.of("@$SAMPLE!", VALID_STR),
                Arguments.of("@$SAMPLE!", VALID_STR)
        );
    }

    private static Stream<Arguments> provideValidMixedCaseString() {
        return Stream.of(
                Arguments.of("Sample", VALID_STR),
                Arguments.of("saMple%%", VALID_STR),
                Arguments.of("samPle@@", VALID_STR),
                Arguments.of("sample", VALID_STR),
                Arguments.of("SAMPLE%%", VALID_STR)
        );
    }

}