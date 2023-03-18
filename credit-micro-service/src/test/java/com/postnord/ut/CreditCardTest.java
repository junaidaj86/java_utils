package com.postnord.ut;

import com.postnord.api.dto.CreditCardDto;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CreditCardTest {

    CreditCardDto credit;
    Validator validator;


    @BeforeEach
    public void init() {
        credit = new CreditCardDto("Dev Dutha", "1111-2222-3333-4444", 5, 2023, 123, 50000);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @AfterEach
    public void tearDown() {
        credit = null;
    }

    @Test
    @Order(1)
    public void whenValidCardNumber_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> {
            credit.setCardNumber("2222-3333-4444-5555");
        });
    }

    @Test
    @Order(2)
    public void whenInvalidCardNumber_thenViolation() {
        credit.setCardNumber("2222-3333-4444-55555");
        Set<ConstraintViolation<CreditCardDto>> violations
                = validator.validate(credit);
        assertEquals(violations.size(), 1);
    }

    @Test
    @Order(3)
    public void whenCardExpiryChanged_thenExceptionThrown() {
        assertAll("card_expiry_check",
                () -> assertThrows(IllegalArgumentException.class, () -> credit.setExpirationDate(2001, 2)),
                () -> assertDoesNotThrow(() -> credit.setExpirationDate(2027, 2))
        );
    }

    @Test
    @Order(4)
    public void whenSecurityCodeIsExceeded_thenViolation() {
        credit.setSecurityCode(1001);
        Set<ConstraintViolation<CreditCardDto>> violations
                = validator.validate(credit);
        assertEquals(violations.size(), 1);
    }

}
