package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;

import org.junit.jupiter.api.Test;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ForbiddenExceptionMapperSocialTest {

    @Inject
    ForbiddenExceptionMapper forbiddenExceptionMapper;

    @Test
    void shouldMapForbiddenExceptionToResponse() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4");
        final ForbiddenException exception = new ForbiddenException("message");

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(FORBIDDEN.getReasonPhrase())
                        .detail(FORBIDDEN.getReasonPhrase())
                        .status(FORBIDDEN.getStatusCode())
                        .build())
                .build();

             Response actualResponse = forbiddenExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }
}
