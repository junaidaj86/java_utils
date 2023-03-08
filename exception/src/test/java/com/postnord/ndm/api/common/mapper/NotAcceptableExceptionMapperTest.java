package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class NotAcceptableExceptionMapperTest {

    @Inject
    NotAcceptableExceptionMapper notAcceptableExceptionMapper;

    @Test
    void whenMapperTranslatesFromNotAcceptableExceptionThenResponseContainsCorrectStatusCodeAndMessage() {

        final String notAcceptableMessage = "RESTEASY003650: No resource method found for POST, return 405 with Allow header";

        final NotAcceptableException notAcceptableException = new NotAcceptableException(notAcceptableMessage,
                Response.status(NOT_ACCEPTABLE.getStatusCode()).entity(notAcceptableMessage).build());

        try (Response response = notAcceptableExceptionMapper.toResponse(notAcceptableException)) {

            assertNotNull(response, "response is null");

            assertEquals(response.getStatus(), NOT_ACCEPTABLE.getStatusCode(), "status not as expected");

            assertTrue(response.getEntity().toString().contains(NOT_ACCEPTABLE.getReasonPhrase()), "toString not as expected");
        }
    }
}
