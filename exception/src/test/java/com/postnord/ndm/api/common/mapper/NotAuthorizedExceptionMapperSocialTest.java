package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;

import org.junit.jupiter.api.Test;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class NotAuthorizedExceptionMapperSocialTest {

    @Inject
    NotAuthorizedExceptionMapper notAuthorizedExceptionMapper;

    @Test
    void shouldMapNotAuthorizedExceptionToResponse() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2");
        final NotAuthorizedException exception = new NotAuthorizedException("message");

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(UNAUTHORIZED.getReasonPhrase())
                        .detail(UNAUTHORIZED.getReasonPhrase())
                        .status(UNAUTHORIZED.getStatusCode())
                        .build())
                .build();

             Response actualResponse = notAuthorizedExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }
}
