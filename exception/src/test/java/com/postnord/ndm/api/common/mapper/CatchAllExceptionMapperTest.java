package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class CatchAllExceptionMapperTest {

    @Inject
    CatchAllExceptionMapper catchAllExceptionMapper;

    @Test
    void whenMapperTranslatesFromExceptionThenResponseContainsCorrectStatusCode() {

        final Exception exception = new Exception("Don't panic just testing uncaught exception");
        try (Response response = catchAllExceptionMapper.toResponse(exception)) {

            assertNotNull(response, "response is null");
            assertTrue(response.getEntity().toString().contains(String.valueOf(
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())), "toString not as expected");
        }
    }
}
