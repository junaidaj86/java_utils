package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;

import org.junit.jupiter.api.Test;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static javax.ws.rs.core.Response.Status.UNSUPPORTED_MEDIA_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class NotSupportedExceptionMapperSocialTest {

    @Inject
    NotSupportedExceptionMapper notSupportedExceptionMapper;

    @Test
    void shouldMapNotSupportedExceptionToResponse() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16");
        final NotSupportedException exception = new NotSupportedException("message");

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
                        .detail(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
                        .status(UNSUPPORTED_MEDIA_TYPE.getStatusCode())
                        .build())
                .build();

             Response actualResponse = notSupportedExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }
}
