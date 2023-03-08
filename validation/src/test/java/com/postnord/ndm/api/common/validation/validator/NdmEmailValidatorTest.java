package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmEmail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmEmailValidatorTest {

    private NdmEmailValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmEmailValidator();
        @NdmEmail
        final class SampleClass {
        }
        final NdmEmail iotaEmail = SampleClass.class.getAnnotation(NdmEmail.class);
        cut.initialize(iotaEmail);
    }

    @Test
     void whenEmailMatchesPatternThenItShouldBeValid() {

        assertTrue(cut.isValid("test@firstmail.com", mockConstraintValidatorContext),
                "Email is valid as it is matching regex");
    }

    @Test
     void whenEmailContainsDotInLocalportThenItShouldBeValid() {

        assertTrue(cut.isValid("test.test1@firstmail.com", mockConstraintValidatorContext),
                "Email is valid when local part contains dot ");
    }

    @Test
     void whenEmailContainsDotInDomainportThenItShouldBeValid() {

        assertTrue(cut.isValid("test@first.mail.com", mockConstraintValidatorContext),
                "Email is valid when domain part contains dot ");
    }

    @Test
     void whenEmailContainsPlusInLocalportThenItShouldBeValid() {

        assertTrue(cut.isValid("test+test1@firstmail.com", mockConstraintValidatorContext),
                "Email is valid when local part contains plus ");
    }

    @Test
     void whenEmailContainsOnlyNumbersInLocalportThenItShouldBeValid() {

        assertTrue(cut.isValid("123456789@firstmail.com", mockConstraintValidatorContext),
                "Email is valid when local part contains only numbers ");
    }

    @Test
     void whenEmailContainsHyphenInLocalportThenItShouldBeValid() {

        assertTrue(cut.isValid("test-test1@firstmail.com", mockConstraintValidatorContext),
                "Email is valid when local part contains hyphen ");
    }

    @Test
     void whenEmailContainsHyphenInDomainportThenItShouldBeValid() {

        assertTrue(cut.isValid("test@first-mail.com", mockConstraintValidatorContext),
                "Email is valid when domain part contains hyphen ");
    }

    @Test
     void whenEmailContainsHyphenAsFirstCharacterInDomainportThenItShouldBeValid() {

        assertTrue(cut.isValid("test@-firstmail.com", mockConstraintValidatorContext),
                "Email is valid when domain part contains hyphen as first character ");
    }

    @Test
     void whenEmailContainsOnlyHyphensInLocalportThenItShouldBeValid() {

        assertTrue(cut.isValid("123456789@firstmail.com", mockConstraintValidatorContext),
                "Email is valid when local part contains only hyphens ");
    }


    @Test
     void whenEmailContainsPlusInDomainPartThenItShouldNotBeValid() {

        assertFalse(cut.isValid("test@first+mail.com", mockConstraintValidatorContext),
                "Email should be invalid when domain part contains plus");
    }

    @Test
     void whenEmailContainsIpAddressInDomainPartThenItShouldNotBeValid() {

        assertFalse(cut.isValid("test@192.168.0.1", mockConstraintValidatorContext),
                "Email should be invalid when domain part contains IP address");
    }

    @Test
     void whenEmailContainsIpAddressInDomainPartWithSquareBracesThenItShouldNotBeValid() {

        assertFalse(cut.isValid("test@[192.168.0.1]", mockConstraintValidatorContext),
                "Email should be invalid when domain part contains IP address in square braces");
    }

    @Test
     void whenEmailContainsDoubleQuotesInLocalPartThenItShouldNotBeValid() {

        assertFalse(cut.isValid("\"test\"@firstmail.com", mockConstraintValidatorContext),
                "Email should be invalid when local part contains double quotes");
    }

    @Test
     void whenEmailContainsDoubleQuotesInDomainPartThenItShouldNotBeValid() {

        assertFalse(cut.isValid("test@\"first\"mail.com", mockConstraintValidatorContext),
                "Email should be invalid when domain part contains double quotes");
    }

    @Test
     void whenEmailContainsOnlyStringThenItShouldNotBeValid() {

        assertFalse(cut.isValid("asdfghjkl", mockConstraintValidatorContext),
                "Email should be invalid when it contains only string");
    }

    @Test
     void whenEmailContainsOnlyDomainPartThenItShouldNotBeValid() {

        assertFalse(cut.isValid("@firstmail.com", mockConstraintValidatorContext),
                "Email should be invalid when it contains only domain part");
    }

    @Test
     void whenEmailContainsDisplayNameThenItShouldNotBeValid() {

        assertFalse(cut.isValid("First User <first@firstmail.com>", mockConstraintValidatorContext),
                "Email should be invalid when it contains display name");
    }

    @Test
     void whenEmailDoesntContainsAtSignThenItShouldNotBeValid() {

        assertFalse(cut.isValid("first.firstmail.com", mockConstraintValidatorContext),
                "Email should be invalid when it doesn't contains At sign");
    }

    @Test
     void whenEmailContainsMoreThanOneAtSignThenItShouldNotBeValid() {

        assertFalse(cut.isValid("first@first@mail.com", mockConstraintValidatorContext),
                "Email should be invalid when it contains more than one At sign");
    }

    @Test
     void whenEmailContainsDotAsFirstCharacterThenItShouldNotBeValid() {

        assertFalse(cut.isValid(".first@firstmail.com", mockConstraintValidatorContext),
                "Email should be invalid when it contains dot as first character");
    }

    @Test
     void whenEmailContainsDotAsLastLocalPartCharacterThenItShouldNotBeValid() {

        assertFalse(cut.isValid("first.@firstmail.com", mockConstraintValidatorContext),
                "Email should be invalid when it contains dot as last character in localpart");
    }

    @Test
     void whenEmailContainsDotAsConsequtiveLettersInLocalPartThenItShouldNotBeValid() {

        assertFalse(cut.isValid("fir..st@firstmail.com", mockConstraintValidatorContext),
                "Email should be invalid when it contains dot as consequtive character in localpart");
    }

    @Test
     void whenEmailContainsNonAsciiCharactersInLocalPartThenItShouldNotBeValid() {

        assertFalse(cut.isValid("あいうえお@firstmail.com", mockConstraintValidatorContext),
                "Email should be invalid when it contains non ascii characters localpart");
    }

    @Test
     void whenEmailDoesntContainsDomainIdentifierThenItShouldNotBeValid() {

        assertFalse(cut.isValid("first@firstmail", mockConstraintValidatorContext),
                "Email should be invalid when it doesn't contains domain identifier");
    }

    @Test
     void whenEmailContainsDotAsConsequtiveLettersInDomainPartThenItShouldNotBeValid() {

        assertFalse(cut.isValid("first@firstmail..com", mockConstraintValidatorContext),
                "Email should be invalid when it contains dot as consequtive character in domainpart");
    }

    @Test
     void whenEmailContainsMoreThan254CharactersThenItShouldNotBeValid() {
        assertFalse(cut.isValid("firstnameofthemailwhosecharacteraremorethantwohunderdandfiftyfourcharactersincludinglocalpartanddomainpartanddomainnameandatsymbol" +
                        "@firstnameofthemailwhosecharacteraremorethantwohunderdandfiftyfourcharactersincludinglocalpartanddomainpartanddomainnameandatsymbol.com", mockConstraintValidatorContext),
                "Email should be invalid when it contains more than 254 characters");
    }
}