package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;

import org.junit.jupiter.api.Test;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class NotFoundExceptionMapperSocialTest {

    @Inject
    NotFoundExceptionMapper notFoundExceptionMapper;

    @Test
    void shouldMapNotFoundExceptionToResponse() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5");
        final NotFoundException exception = new NotFoundException("message");

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(NOT_FOUND.getReasonPhrase())
                        .detail(NOT_FOUND.getReasonPhrase())
                        .status(NOT_FOUND.getStatusCode())
                        .build())
                .build();

             Response actualResponse = notFoundExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }
}
