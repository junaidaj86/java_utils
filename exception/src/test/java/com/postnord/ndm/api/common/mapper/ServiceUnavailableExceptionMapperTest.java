package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class ServiceUnavailableExceptionMapperTest {

    @Inject
    ServiceUnavailableExceptionMapper serviceUnavailableExceptionMapper;

    @Test
    void whenMapperTranslatesFromServiceUnavailableExceptionThenResponseContainsCorrectStatusCodeAndMessage() {

        final String serviceUnavailableMessage = "RESTEASY003650: No resource method found for POST, return 405 with Allow header";

        final ServiceUnavailableException serviceUnavailableException = new ServiceUnavailableException(serviceUnavailableMessage,
                Response.status(SERVICE_UNAVAILABLE.getStatusCode()).entity(serviceUnavailableMessage).build());

        try (Response response = serviceUnavailableExceptionMapper.toResponse(serviceUnavailableException)) {

            assertNotNull(response, "response is null");

            assertEquals(response.getStatus(), SERVICE_UNAVAILABLE.getStatusCode(), "status not as expected");

            assertTrue(response.getEntity().toString().contains(SERVICE_UNAVAILABLE.getReasonPhrase()), "toString not as expected");
        }
    }
}
