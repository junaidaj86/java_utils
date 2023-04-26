package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class NotAuthorizedExceptionMapperTest {

    @Inject
    NotAuthorizedExceptionMapper notAuthorizedExceptionMapper;

    @Test
    void whenMapperTranslatesFromNotAuthorizedExceptionThenResponseContainsCorrectStatusCodeAndMessage() {

        final String notAuthorizedMessage = "RESTEASY003650: No resource method found for POST, return 405 with Allow header";

        final NotAuthorizedException notAuthorizedException = new NotAuthorizedException(notAuthorizedMessage,
                Response.status(UNAUTHORIZED.getStatusCode()).entity(notAuthorizedMessage).build());

        try (Response response = notAuthorizedExceptionMapper.toResponse(notAuthorizedException)) {

            assertNotNull(response, "response is null");

            assertEquals(response.getStatus(), UNAUTHORIZED.getStatusCode(), "status not as expected");

            assertTrue(response.getEntity().toString().contains(UNAUTHORIZED.getReasonPhrase()), "toString not as expected");
        }
    }
}
