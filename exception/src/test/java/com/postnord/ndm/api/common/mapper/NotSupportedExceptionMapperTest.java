package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static javax.ws.rs.core.Response.Status.UNSUPPORTED_MEDIA_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class NotSupportedExceptionMapperTest {

    @Inject
    NotSupportedExceptionMapper notSupportedExceptionMapper;

    @Test
    void whenMapperTranslatesFromNotSupportedExceptionThenResponseContainsCorrectStatusCodeAndMessage() {

        final String notSupportedMessage = "RESTEASY003650: No resource method found for POST, return 405 with Allow header";

        final NotSupportedException notSupportedException = new NotSupportedException(notSupportedMessage,
                Response.status(UNSUPPORTED_MEDIA_TYPE.getStatusCode()).entity(notSupportedMessage).build());

        try (Response response = notSupportedExceptionMapper.toResponse(notSupportedException)) {

            assertNotNull(response, "response is null");

            assertEquals(response.getStatus(), UNSUPPORTED_MEDIA_TYPE.getStatusCode(), "status not as expected");

            assertTrue(response.getEntity().toString().contains(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()),
                    "toString not as expected");
        }
    }
}
