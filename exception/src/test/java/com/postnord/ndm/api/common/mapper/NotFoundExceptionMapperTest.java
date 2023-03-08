package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class NotFoundExceptionMapperTest {

    @Inject
    NotFoundExceptionMapper notFoundExceptionMapper;

    @Test
    void whenMapperTranslatesFromNotFoundExceptionThenResponseContainsCorrectStatusCodeAndMessage() {

        final String notFoundMessage = "RESTEASY003210: Could not find resource for full path: " +
                "http://api.ndm.postnord.com/v1/subscriptions/";

        final NotFoundException notFoundException = new NotFoundException(notFoundMessage,
                Response.status(NOT_FOUND.getStatusCode()).entity(notFoundMessage).build());

        try (Response response = notFoundExceptionMapper.toResponse(notFoundException)) {

            assertNotNull(response, "response is null");

            assertEquals(response.getStatus(), NOT_FOUND.getStatusCode(), "status not as expected");

            assertTrue(response.getEntity().toString().contains(NOT_FOUND.getReasonPhrase()), "toString not as expected");
        }
    }
}
