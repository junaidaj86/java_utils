package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class ForbiddenExceptionMapperTest {

    @Inject
    ForbiddenExceptionMapper forbiddenExceptionMapper;

    @Test
    void whenMapperTranslatesFromForbiddenExceptionThenResponseContainsCorrectStatusCodeAndMessage() {

        final String forbiddenMessage = "RESTEASY003650: No resource method found for POST, return 405 with Allow header";

        final ForbiddenException forbiddenException = new ForbiddenException(forbiddenMessage,
                Response.status(FORBIDDEN.getStatusCode()).entity(forbiddenMessage).build());

        try (Response response = forbiddenExceptionMapper.toResponse(forbiddenException)) {
            assertNotNull(response, "response is null");

            assertEquals(response.getStatus(), FORBIDDEN.getStatusCode(), "status not as expected");

            assertTrue(response.getEntity().toString().contains(FORBIDDEN.getReasonPhrase()), "toString not as expected");
        }
    }
}
