package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.exception.APIException;
import com.postnord.ndm.api.common.validation.constraints.NdmString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

//must be a social unit test (i.e. use the real class) as the validate() method of ModelValidatorInternal is static
@ExtendWith(MockitoExtension.class)
class ModelValidatorInternalTest {

    private ModelValidatorInternal<SocialStub> cut;

    @BeforeEach
    void setUp() {
        cut = new ModelValidatorInternal<>();
    }

    @Test
    void whenModelValidatorValidateIsCalledAndObjectHasViolationsThenAPIExceptionIsThrown() {

        final SocialStub invalidClass = new SocialStub();

        assertThrows(APIException.class, () -> cut.validate(invalidClass), "invalid class specified APIException expected");
    }

    @Test
    void whenModelValidatorValidateIsCalledAndObjectHasNoViolationsThenNoExceptionIsThrown() {
        final SocialStub validClass = new SocialStub();

        validClass.setStringParameter("i am now valid");

        assertDoesNotThrow(() -> cut.validate(validClass), "valid class specified no Exception expected");
    }

    private class SocialStub {

        @NdmString(message = "mandatory NdmString must be set")
        private String stringParameter;

        void setStringParameter(final String stringParameter) {
            this.stringParameter = stringParameter;
        }
    }
}
