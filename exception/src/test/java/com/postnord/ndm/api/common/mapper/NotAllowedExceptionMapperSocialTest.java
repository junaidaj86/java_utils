package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;

import org.junit.jupiter.api.Test;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class NotAllowedExceptionMapperSocialTest {

    @Inject
    NotAllowedExceptionMapper notAllowedExceptionMapper;

    @Test
    void shouldMapNotAllowedExceptionToResponse() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6");
        final NotAllowedException exception = new NotAllowedException("message");

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(METHOD_NOT_ALLOWED.getReasonPhrase())
                        .detail(METHOD_NOT_ALLOWED.getReasonPhrase())
                        .status(METHOD_NOT_ALLOWED.getStatusCode())
                        .build())
                .build();

             Response actualResponse = notAllowedExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }
}
