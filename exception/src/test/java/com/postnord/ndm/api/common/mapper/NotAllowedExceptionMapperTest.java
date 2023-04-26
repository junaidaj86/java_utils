package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class NotAllowedExceptionMapperTest {

    @Inject
    NotAllowedExceptionMapper notAllowedExceptionMapper;

    @Test
    void whenMapperTranslatesFromNotAllowedExceptionThenResponseContainsCorrectStatusCodeAndMessage() {

        final String notAllowedMessage = "RESTEASY003650: No resource method found for POST, return 405 with Allow header";

        final NotAllowedException notFoundException = new NotAllowedException(notAllowedMessage,
                Response.status(METHOD_NOT_ALLOWED.getStatusCode()).entity(notAllowedMessage).build());

        try (Response response = notAllowedExceptionMapper.toResponse(notFoundException)) {

            assertNotNull(response, "response is null");

            assertEquals(response.getStatus(), METHOD_NOT_ALLOWED.getStatusCode(), "status not as expected");

            assertTrue(response.getEntity().toString().contains(METHOD_NOT_ALLOWED.getReasonPhrase()), "toString not as expected");
        }
    }
}
