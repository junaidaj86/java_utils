package com.postnord.ndm.base.test_utils.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import javax.validation.ConstraintViolation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationTest extends AbstractConstraintValidationTest {
    @Test
    void testValidationNoViolationsTest() {
        final TestDataV1 testDataV1 = TestDataV1.builder().id("12345678").name("john.smith").build();
        final Set<ConstraintViolation<TestDataV1>> violations = validator.validate(testDataV1);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationOneViolationTest() {
        final TestDataV1 testDataV1 = TestDataV1.builder().id("1").name("john.smith").build();
        final Set<ConstraintViolation<TestDataV1>> violations = validator.validate(testDataV1);
        assertEquals(1, violations.size());
    }
}
