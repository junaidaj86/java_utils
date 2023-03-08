package com.postnord.ndm.base.openapi;

import com.postnord.ndm.base.openapi.model.Problem;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.Validator;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@SuppressWarnings("PMD.LiteralsFirstInComparisons")
class ValidationTest extends AbstractOpenApiTest {

    @Inject
    Validator validator;

    @Test
    void testValidationOfItemDetailsShouldSucceed() {
        final var violations = validator.validate(buildItemDetails());
        assertEquals(0, violations.size());
    }

    @Test
    void testValidationOfItemDetailsWithEmptyTagsShouldSucceed() {
        final var violations = validator.validate(buildItemDetails().tags(List.of()));
        assertEquals(0, violations.size());
    }

    @Test
    void testValidationOfItemDetailsWithNullTagShouldFail() {
        final var violations = validator.validate(buildItemDetails().addTagsItem(null));
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("tags element must not be null")));
    }

    @Test
    void testValidationOfItemDetailsWithNullAdditionalDataShouldSucceed() {
        final var violations = validator.validate(buildItemDetails().putAdditionalDataItem(null, null));
        assertEquals(0, violations.size());
    }

    @Test
    void testValidationOfItemDetailsWithNullStatusShouldFail() {
        final var violations = validator.validate(buildItemDetails().status(null));
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("status must not be null")));
    }

    @Test
    void testValidationOfItemDetailsWithInvalidStatusShouldFail() {
        final var violations = validator.validate(buildItemDetails().status("INVALID"));
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("status value not allowed")));
    }

    @Test
    void testValidationOfItemDetailsWithNullPropertiesShouldSucceed() {
        final var violations = validator.validate(buildItemDetails().properties(null));
        assertEquals(0, violations.size());
    }

    @Test
    void testValidationOfItemDetailsWithEmptyPropertiesShouldFail() {
        final var violations = validator.validate(buildItemDetails().properties(Set.of()));
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("size must be between 1 and 100")));
    }

    @Test
    void testValidationOfItemDetailsWithInvalidPropertyShouldFail() {
        final var violations = validator.validate(buildItemDetails().addPropertiesItem("INVALID"));
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("properties value not allowed")));
    }

    @Test
    void testValidationOfItemDetailsWithNullIdShouldFail() {
        final var violations = validator.validate(buildItemDetails().id(null));
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("id must not be null")));
    }

    @Test
    void testValidationOfProblemWithNullParameterKeyShouldSucceed() {
        final var violations = validator.validate(new Problem().putParametersItem(null, ADDITIONAL_DATA_VALUE));
        assertEquals(0, violations.size());
    }

    @Test
    void testValidationOfProblemWithNullParameterValueShouldSucceed() {
        final var violations = validator.validate(new Problem().putParametersItem(ADDITIONAL_DATA_KEY, null));
        assertEquals(0, violations.size());
    }
}
