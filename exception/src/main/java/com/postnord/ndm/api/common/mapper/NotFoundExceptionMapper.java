package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.net.URI;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @Override
    public Response toResponse(final NotFoundException exception) {

        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.get().logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5"))
                        .title(NOT_FOUND.getReasonPhrase())
                        .status(NOT_FOUND.getStatusCode())
                        .detail(NOT_FOUND.getReasonPhrase()).build()).build();
    }
}
