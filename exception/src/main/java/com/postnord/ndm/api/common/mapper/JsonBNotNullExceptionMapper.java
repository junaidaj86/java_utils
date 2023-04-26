package com.postnord.ndm.api.common.mapper;


import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.net.URI;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class JsonBNotNullExceptionMapper implements ExceptionMapper<JsonbException> {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @Override
    public Response toResponse(final JsonbException exception) {
        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.get().logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1"))
                        .title(BAD_REQUEST.getReasonPhrase())
                        .status(BAD_REQUEST.getStatusCode())
                        .detail(BAD_REQUEST.getReasonPhrase()).build()).build();
    }
}
