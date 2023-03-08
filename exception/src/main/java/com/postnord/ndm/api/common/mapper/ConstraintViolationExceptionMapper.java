package com.postnord.ndm.api.common.mapper;


import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    public static final String VIOLATION_UNKNOWN = "Violation Unknown";
    private static final String MESSAGE = "message";
    private static final String VIOLATION_OCCURRED = "Violation occurred";

    @Inject
    MapperConfiguration mapperConfiguration;

    @Override
    public Response toResponse(final ConstraintViolationException exception) {

        final Iterator<ConstraintViolation<?>> violations = exception.getConstraintViolations().iterator();

        if (violations.hasNext()) {

            final ConstraintViolation<?> violation = exception.getConstraintViolations().iterator().next();

            NdmLogger.warn(LogRecord
                    .builder()
                    .message(VIOLATION_OCCURRED)
                    .category(mapperConfiguration.logCategory())
                    .extraData(Map.of("Violation", parseMessageFromViolation(violation)))
                    .build());

            return buildProblemResponse(parseMessageFromViolation(violation), violation.getPropertyPath());

        } else {

            NdmLogger.warn(LogRecord
                    .builder()
                    .category(mapperConfiguration.logCategory())
                    .exception(exception)
                    .build());

            return buildProblemResponse(VIOLATION_UNKNOWN, null);
        }
    }

    private Response buildProblemResponse(final String s, final Path invalidParam) {
        final var problemBuilder = Problem.builder();
        problemBuilder.type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1"))
                .title(BAD_REQUEST.getReasonPhrase())
                .status(BAD_REQUEST.getStatusCode());
        final List<Map<String, String>> violationResponse = Collections.synchronizedList(getViolationObject(invalidParam, s));
        if (invalidParam == null || violationResponse.isEmpty()) {
            problemBuilder.detail(s);
        } else {
            problemBuilder.detail("Invalid request");
            problemBuilder.parameter("invalid-params", violationResponse);
        }
        return ProblemResponse.builder()
                .problem(problemBuilder.build()).build();
    }

    private String parseMessageFromViolation(final ConstraintViolation<?> violation) {

        if (violation.getMessage() == null) {
            return String.valueOf(violation.getConstraintDescriptor().getAttributes().get(MESSAGE));
        } else {
            return violation.getMessage();
        }
    }

    private List<Map<String, String>> getViolationObject(final Path path, final String violationMessage) {
        if (path != null) {
            final AtomicReference<Path.Node> lastElement = new AtomicReference<>();
            path.iterator().forEachRemaining(lastElement::set);
            if (lastElement.get().getKind().equals(ElementKind.PROPERTY) || lastElement.get().getKind().equals(ElementKind.PARAMETER)) {
                final Map<String, String> violationObject = new TreeMap<>();
                violationObject.put("name", lastElement.get().getName());
                violationObject.put("violation", violationMessage);
                return List.of(violationObject);
            }
        }
        return List.of();
    }
}