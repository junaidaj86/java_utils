package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Inject
    MapperConfiguration mapperConfiguration;

    @Override
    public Response toResponse(final WebApplicationException exception) {

        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1"))
                        .title(INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .status(INTERNAL_SERVER_ERROR.getStatusCode())
                        .detail(INTERNAL_SERVER_ERROR.getReasonPhrase()).build()).build();
    }
}
