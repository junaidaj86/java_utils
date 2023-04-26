package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.json.bind.JsonbException;
import jakarta.json.stream.JsonParsingException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({"PMD.TooManyStaticImports", "PMD.AvoidDuplicateLiterals"})
@QuarkusTest
class ProcessingExceptionMapperTest {

    @Inject
    ProcessingExceptionMapper processingExceptionMapper;

    @Test
    void whenMapperTranslatesFromProcessingExceptionWithJsonParsingExceptionThenResponseContainsCorrectStatusCodeAndMessage() {

        final String exceptionMessage = "Unexpected char 102 at (line no=3, column no=14, offset=27), expecting 'a'";

        final ProcessingException processingException =
                new ProcessingException(exceptionMessage, new JsonbException(exceptionMessage, new JsonParsingException(exceptionMessage, null)));

        try (Response response = processingExceptionMapper.toResponse(processingException)) {
            assertNotNull(response, "response is null");

            assertEquals(response.getStatus(), BAD_REQUEST.getStatusCode(), "status not as expected");

            assertTrue(response.getEntity().toString().contains(BAD_REQUEST.getReasonPhrase()), "toString not as expected");

            assertTrue(response.getEntity().toString().contains(exceptionMessage), "toString not as expected");
        }
    }

    @Test
    void whenMapperTranslatesFromProcessingExceptionWithoutJsonParsingExceptionThenResponseContainsCorrectStatusCodeAndMessage() {

        final String exceptionMessage = "Something went wrong";

        final ProcessingException processingException = new ProcessingException(exceptionMessage, new JsonbException(exceptionMessage));

        try (Response response = processingExceptionMapper.toResponse(processingException)) {
            assertNotNull(response, "response is null");

            assertEquals(response.getStatus(), INTERNAL_SERVER_ERROR.getStatusCode(), "status not as expected");

            assertTrue(response.getEntity().toString().contains(INTERNAL_SERVER_ERROR.getReasonPhrase()), "toString not as expected");

            assertFalse(response.getEntity().toString().contains(exceptionMessage), "toString not as expected");
        }
    }
}
