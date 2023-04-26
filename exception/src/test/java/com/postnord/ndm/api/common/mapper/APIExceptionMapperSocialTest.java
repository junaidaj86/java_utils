package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.exception.APIException;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import org.junit.jupiter.api.Test;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class APIExceptionMapperSocialTest {

    @Inject
    APIExceptionMapper apiExceptionMapper;

    @Test
    void shouldMapAPIExceptionToResponse() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1");
        final APIException exception = new APIException(type, BAD_REQUEST.getReasonPhrase(),
                BAD_REQUEST.getStatusCode(), BAD_REQUEST.getReasonPhrase());

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(BAD_REQUEST.getReasonPhrase())
                        .detail(BAD_REQUEST.getReasonPhrase())
                        .status(BAD_REQUEST.getStatusCode())
                        .build())
                .build();

             Response actualResponse = apiExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }
}
