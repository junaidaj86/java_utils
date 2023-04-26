package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.net.URI;

import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @Override
    public Response toResponse(final WebApplicationException exception) {

        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.get().logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1"))
                        .title(INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .status(INTERNAL_SERVER_ERROR.getStatusCode())
                        .detail(INTERNAL_SERVER_ERROR.getReasonPhrase()).build()).build();
    }
}
