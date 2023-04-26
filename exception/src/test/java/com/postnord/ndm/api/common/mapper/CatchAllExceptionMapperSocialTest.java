package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.exception.APIException;
import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;

import org.junit.jupiter.api.Test;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class CatchAllExceptionMapperSocialTest {

    @Inject
    CatchAllExceptionMapper catchAllExceptionMapper;

    @Test
    void shouldMapAPIExceptionToResponse() {

        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1");
        final APIException exception = new APIException(type, INTERNAL_SERVER_ERROR.getReasonPhrase(),
                INTERNAL_SERVER_ERROR.getStatusCode());

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .detail(INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .status(INTERNAL_SERVER_ERROR.getStatusCode())
                        .build())
                .build();

             Response actualResponse = catchAllExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }
}
