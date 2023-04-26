package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;

import org.junit.jupiter.api.Test;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class NotAcceptableExceptionMapperSocialTest {

    @Inject
    NotAcceptableExceptionMapper notAcceptableExceptionMapper;

    @Test
    void shouldMapNotAcceptableExceptionToResponse() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7");
        final NotAcceptableException exception = new NotAcceptableException("message");

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(NOT_ACCEPTABLE.getReasonPhrase())
                        .detail(NOT_ACCEPTABLE.getReasonPhrase())
                        .status(NOT_ACCEPTABLE.getStatusCode())
                        .build())
                .build();

             Response actualResponse = notAcceptableExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }
}
