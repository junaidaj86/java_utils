package com.postnord.ndm.base.test_utils.model;

import org.junit.jupiter.api.BeforeEach;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * AbstractConstraintValidationTest builds Validator object for usage in tests.
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractConstraintValidationTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    protected Validator validator;

    @BeforeEach
    void setUp() {
        validator = factory.getValidator();
    }
}
