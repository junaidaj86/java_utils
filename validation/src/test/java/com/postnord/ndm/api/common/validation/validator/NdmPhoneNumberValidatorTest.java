package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmPhoneNumber;

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
class NdmPhoneNumberValidatorTest {

    private NdmPhoneNumberValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmPhoneNumberValidator();
        @NdmPhoneNumber
        final class SampleClass {
        }
        final NdmPhoneNumber iotaPhoneNumber = SampleClass.class.getAnnotation(NdmPhoneNumber.class);
        cut.initialize(iotaPhoneNumber);
    }

    @ParameterizedTest
    @MethodSource("provideValidInternationalPhoneNumbers")
    void shouldBeValidInternationalPhoneNumbersWithRequireInternationalFormatTrue(final String phoneNumber, final String message) {
        @NdmPhoneNumber(requireInternationalFormat = true)
        final class SampleClass {
        }
        final NdmPhoneNumber iotaPhoneNumber = SampleClass.class.getAnnotation(NdmPhoneNumber.class);
        cut = new NdmPhoneNumberValidator();
        cut.initialize(iotaPhoneNumber);

        assertTrue(cut.isValid(phoneNumber, mockConstraintValidatorContext), message);
    }

    @ParameterizedTest
    @MethodSource("provideValidNationalPhoneNumbers")
    void shouldBeInvalidNationalNumbersWithRequireInternationalFormatTrue(final String phoneNumber, final String message) {
        @NdmPhoneNumber(requireInternationalFormat = true)
        final class SampleClass {
        }
        final NdmPhoneNumber iotaPhoneNumber = SampleClass.class.getAnnotation(NdmPhoneNumber.class);
        cut = new NdmPhoneNumberValidator();
        cut.initialize(iotaPhoneNumber);

        assertFalse(cut.isValid(phoneNumber, mockConstraintValidatorContext), message);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPhoneNumbers")
    void shouldBeInvalidNumbersWithRequireInternationalFormatTrue(final String phoneNumber, final String message) {
        @NdmPhoneNumber(requireInternationalFormat = true)
        final class SampleClass {
        }
        final NdmPhoneNumber iotaPhoneNumber = SampleClass.class.getAnnotation(NdmPhoneNumber.class);
        cut = new NdmPhoneNumberValidator();
        cut.initialize(iotaPhoneNumber);

        assertFalse(cut.isValid(phoneNumber, mockConstraintValidatorContext), message);
    }

    @ParameterizedTest
    @MethodSource("provideValidInternationalPhoneNumbers")
    void shouldBeValidInternationalPhoneNumbersWithRequireInternationalFormatFalse(final String phoneNumber, final String message) {
        assertTrue(cut.isValid(phoneNumber, mockConstraintValidatorContext), message);
    }

    @ParameterizedTest
    @MethodSource("provideValidNationalPhoneNumbers")
    void shouldBeValidNationalPhoneNumbersWithRequireInternationalFormatFalse(final String phoneNumber, final String message) {
        assertTrue(cut.isValid(phoneNumber, mockConstraintValidatorContext), message);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPhoneNumbers")
    void shouldBeInvalidPhoneNumbersWithRequireInternationalFormatFalse(final String phoneNumber, final String message) {
        assertFalse(cut.isValid(phoneNumber, mockConstraintValidatorContext), message);
    }

    private static Stream<Arguments> provideValidInternationalPhoneNumbers() {
        return Stream.of(
                Arguments.of("+995 442 123456", "valid international number for Abkhazia"),
                Arguments.of("+93 30 539 0605", "valid international number for Afghanistan"),
                Arguments.of("+61 2 1255 3456", "valid international number for Australia"),
                Arguments.of("+61 2 9192 0995", "valid international number for Australia"),
                Arguments.of("+22 607 123 4567", "valid international number for Belgium from E.123"),
                Arguments.of("+551155256325", "valid international number for Brazil"),
                Arguments.of("+86 20 1255 3456", "valid international number for China"),
                Arguments.of("+86 755 1234 5678", "valid international number for China"),
                Arguments.of("+186 1234 5678", "valid international number for China Mobile"),
                Arguments.of("+49 351 125 3456", "valid international number for Germany"),
                Arguments.of("+49 5678 123456", "another valid international number for Germany"),
                Arguments.of("+91 1234 123456", "valid international mobile number for India"),
                Arguments.of("+62 21 6539 0605", "valid international number for Indonesia"),
                Arguments.of("+98 515 539 0605", "valid international number for Iran"),
                Arguments.of("+39 06 5398 0605", "valid international number for Italy"),
                Arguments.of("+64 3 539 0605", "valid international number for New Zealand"),
                Arguments.of("+64 9 887 6986", "valid international number for New Zealand"),
                Arguments.of("+63 35 539 0605", "valid international number for Philippines"),
                Arguments.of("+65 6396 0605", "valid international number for Singapore"),
                Arguments.of("+66 2 123 4567", "valid international number for Thailand"),
                Arguments.of("+44 141 222 3344", "valid international number for UK"),
                Arguments.of("+44 20 8759 9036", "valid international number for UK"),
                Arguments.of("+442071838750", "valid international number for UK"),
                Arguments.of("+1 212 555 3456", "valid international number for USA"),
                Arguments.of("+1 914 232 9901", "valid international number for USA"),
                Arguments.of("+1302 1234567", "valid international number for USA from E.123"),
                Arguments.of("+84 35 539 0605", "valid international number for Vietnam"),
                Arguments.of("+358411234567", "valid international number for Finland mobile phone"),
                Arguments.of("+358 41 123 4567", "valid international number for Finland mobile phone with spaces"),
                Arguments.of("+1 8856324256", "Phone number is valid as it is having 10 digits with country code and space"),
                Arguments.of("+18856324256", "Phone number is valid as it is having 10 digits with country code without space"),
                Arguments.of("+918856324256", "Phone number is valid as it is having 10 digits with 2 digits country code"),
                Arguments.of("+9968856324256", "Phone number is valid as it is having 10 digits with 3 digits country code"),
                Arguments.of("+1 1234567890123", "Phone number is valid international number with 1 digit country code"),
                Arguments.of("+12 123456789", "Phone number is valid international number with 2 digit country code"),
                Arguments.of("+123 123456", "Phone number is valid international number with 3 digit country code"),
                Arguments.of("+111 636 85 67 89", "Phone number is valid imaginary international number")
        );
    }

    private static Stream<Arguments> provideValidNationalPhoneNumbers() {
        return Stream.of(
                Arguments.of("(0607) 123 4567", "valid national number for Belgium from E.123"),
                Arguments.of("041 504 1234", "valid national mobile number for Finland"),
                Arguments.of("0415041234", "valid national mobile number for Finland without spaces"),
                Arguments.of("885-632-4256", "Phone number is valid as it is having 10 digits with hyphen formatting"),
                Arguments.of("08856324256", "valid national number with zero in front"),
                Arguments.of("8856324256", "valid national number without spaces or hyphens"),
                Arguments.of("(302)1234567", "valid national number for USA from E.123"),
                Arguments.of("02 9192 0995", "valid national number for Australia"),
                Arguments.of("09 887 6986", "valid national number for New Zealand"),
                Arguments.of("09 123 456", "valid national number for Finland"),
                Arguments.of("11 1234 5678", "valid national number for India landline with 2 digit area code"),
                Arguments.of("712 123 4567", "valid national number for India landline with 3 digit area code"),
                Arguments.of("2055550125", "valid national number for Canada"),
                Arguments.of("202 555 0125", "valid national number for Canada with spaces"),
                Arguments.of("(202) 555-0125", "valid national number for Canada with hyphens and brackets"),
                Arguments.of("636 856 789", "valid national test number")
        );
    }

    private static Stream<Arguments> provideInvalidPhoneNumbers() {
        return Stream.of(
                Arguments.of("358415041234", "invalid phone number as it has country code without +"),
                Arguments.of("784596321452", "Phone number is not valid as it is having more than 11 digits"),
                Arguments.of("784596&452", "Phone number is not valid as it is having special characters"),
                Arguments.of("78459 63452", "Phone number is not valid as it is having space"),
                Arguments.of("+1 800       ", "Phone number is not valid as it is having space"),
                Arguments.of("          ", "Phone number is not valid as it is having blank spaces"),
                Arguments.of("aaa nnn nnnn", "Phone number is not valid as it has characters"),
                Arguments.of("1234", "Phone number is too short")
        );
    }
}