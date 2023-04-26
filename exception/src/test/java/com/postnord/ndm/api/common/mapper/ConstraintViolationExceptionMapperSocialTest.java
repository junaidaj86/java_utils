package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.metadata.ConstraintDescriptor;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class ConstraintViolationExceptionMapperSocialTest {

    public static final String VIOLATION_UNKNOWN = "Violation Unknown";
    public static final String MESSAGE = "message";
    public static final String UNSET = "UNSET";

    @Inject
    ConstraintViolationExceptionMapper constraintViolationExceptionMapper;

    @Test
    void shouldMapConstraintViolationExceptionToResponse() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1");
        final ConstraintViolationException exception = new ConstraintViolationException(MESSAGE, Collections.emptySet());

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(BAD_REQUEST.getReasonPhrase())
                        .detail(VIOLATION_UNKNOWN)
                        .status(BAD_REQUEST.getStatusCode())
                        .build()).build();

             Response actualResponse = constraintViolationExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }

    @Test
    void shouldMapConstraintViolationExceptionToResponseWithViolation() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1");
        final Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();
        constraintViolations.add(createMockedConstraintViolationWithMessage());

        final ConstraintViolationException exception = new ConstraintViolationException(MESSAGE, constraintViolations);

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(BAD_REQUEST.getReasonPhrase())
                        .detail(MESSAGE)
                        .status(BAD_REQUEST.getStatusCode())
                        .build()).build();

             Response actualResponse = constraintViolationExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }

    @Test
    void shouldMapConstraintViolationExceptionToResponseWithViolationHavingAttributesMessage() {
        final URI type = URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1");
        final Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();
        constraintViolations.add(createMockedConstraintViolationWithAttributes());

        final ConstraintViolationException exception = new ConstraintViolationException(MESSAGE, constraintViolations);

        try (Response expectedResponse = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(type)
                        .title(BAD_REQUEST.getReasonPhrase())
                        .detail(UNSET)
                        .status(BAD_REQUEST.getStatusCode())
                        .build())
                .build();

             Response actualResponse = constraintViolationExceptionMapper.toResponse(exception)) {

            assertEquals(expectedResponse.getEntity().toString(), actualResponse.getEntity().toString(),
                    "entity not as expected");
        }
    }

    private ConstraintViolation<?> createMockedConstraintViolationWithMessage() {
        final ConstraintViolation cv = mock(ConstraintViolation.class);
        when(cv.getMessage()).thenReturn(MESSAGE);
        return cv;
    }

    private ConstraintViolation<?> createMockedConstraintViolationWithAttributes() {
        final ConstraintViolation cv = mock(ConstraintViolation.class);
        final ConstraintDescriptor constraintDescriptor = mock(ConstraintDescriptor.class);
        when(constraintDescriptor.getAttributes()).thenReturn(Collections.singletonMap(MESSAGE, UNSET));
        when(cv.getConstraintDescriptor()).thenReturn(constraintDescriptor);
        return cv;
    }
}
