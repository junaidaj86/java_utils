package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import java.net.URI;
import java.util.Optional;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.json.stream.JsonParsingException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @Override
    public Response toResponse(final ProcessingException exception) {
        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.get().logCategory())
                .build());

        final Optional<JsonParsingException> jsonParsingException = extractJsonParsingException(exception);
        if (jsonParsingException.isPresent()) {
            return ProblemResponse.builder()
                    .problem(Problem.builder()
                            .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1"))
                            .title(BAD_REQUEST.getReasonPhrase())
                            .status(BAD_REQUEST.getStatusCode())
                            .detail(jsonParsingException.get().getMessage()).build()).build();
        }

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1"))
                        .title(INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .status(INTERNAL_SERVER_ERROR.getStatusCode())
                        .detail(INTERNAL_SERVER_ERROR.getReasonPhrase()).build()).build();
    }

    private static Optional<JsonParsingException> extractJsonParsingException(final Throwable throwable) {
        if (throwable == null) {
            return Optional.empty();
        }
        if (throwable instanceof JsonParsingException) {
            return Optional.of((JsonParsingException) throwable);
        }
        return extractJsonParsingException(throwable.getCause());
    }
}
