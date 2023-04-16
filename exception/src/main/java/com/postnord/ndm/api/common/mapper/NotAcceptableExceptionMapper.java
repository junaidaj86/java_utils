package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import java.net.URI;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class NotAcceptableExceptionMapper implements ExceptionMapper<NotAcceptableException> {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @Override
    public Response toResponse(final NotAcceptableException exception) {

        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.get().logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7"))
                        .title(NOT_ACCEPTABLE.getReasonPhrase())
                        .status(NOT_ACCEPTABLE.getStatusCode())
                        .detail(NOT_ACCEPTABLE.getReasonPhrase()).build()).build();
    }
}
