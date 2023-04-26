package com.postnord.ndm.base.health;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ApplicationHealthServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationHealthServiceTest.class);
    private static final String NAME = "name";
    private static final String STATUS = "status";
    private static final String UP = "UP";
    private static final String CHECKS = "checks";

    @Test
    void testApplicationIsUp() {
        final Map<?, ?> result = given()
                .when()
                .get("/q/health")
                .then()
                .statusCode(200)
                .extract()
                .as(Map.class);

        LOGGER.debug("/health endpoint replied with: {}", result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(UP, result.get(STATUS), "Application overall status is " + result.get(STATUS));
        //noinspection unchecked
        final List<Map<?, ?>> checks = (List<Map<?, ?>>) result.get(CHECKS);
        Assertions.assertEquals(2, checks.size());
        for (final Map<?, ?> check : checks) {
            Assertions.assertEquals(UP, check.get(STATUS), "'" + check.get(NAME) + "' check is " + check.get(STATUS));
        }
    }
}
