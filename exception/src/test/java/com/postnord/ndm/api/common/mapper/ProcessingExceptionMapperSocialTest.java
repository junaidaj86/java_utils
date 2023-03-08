package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;

import org.junit.jupiter.api.Test;

import java.net.URI;

import javax.inject.Inject;
import javax.json.bind.JsonbException;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ProcessingExceptionMapperSocialTest {

    @Inject
    ProcessingExceptionMapper processingExceptionMapper;

    @Test
    void shouldMapProcessingExceptionWithJsonParsingExceptionToResponse() {
        final String exceptionMessage = "Unexpected char 102 at (line no=3, column no=14, offset=27), expecting 'a'";
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1");
        final ProcessingException exception =
                new ProcessingException(exceptionMessage, new JsonbException(exceptionMessage, new JsonParsingException(exceptionMessage, null)));

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(BAD_REQUEST.getReasonPhrase())
                        .detail(exceptionMessage)
                        .status(BAD_REQUEST.getStatusCode())
                        .build())
                .build();

             Response actualResponse = processingExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }

    @Test
    void shouldMapProcessingExceptionWithoutJsonParsingExceptionToResponse() {
        final String exceptionMessage = "Something went wrong";
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1");
        final ProcessingException exception = new ProcessingException(exceptionMessage, new JsonbException(exceptionMessage));

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .detail(INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .status(INTERNAL_SERVER_ERROR.getStatusCode())
                        .build())
                .build();

             Response actualResponse = processingExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }
}
