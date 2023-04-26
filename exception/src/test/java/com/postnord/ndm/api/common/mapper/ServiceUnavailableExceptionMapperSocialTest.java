package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;

import org.junit.jupiter.api.Test;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ServiceUnavailableExceptionMapperSocialTest {

    @Inject
    ServiceUnavailableExceptionMapper serviceUnavailableExceptionMapper;

    @Test
    void shouldMapServiceUnavailableExceptionToResponse() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.4");
        final ServiceUnavailableException exception = new ServiceUnavailableException("message");

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(SERVICE_UNAVAILABLE.getReasonPhrase())
                        .detail(SERVICE_UNAVAILABLE.getReasonPhrase())
                        .status(SERVICE_UNAVAILABLE.getStatusCode())
                        .build())
                .build();

             Response actualResponse = serviceUnavailableExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }
}
