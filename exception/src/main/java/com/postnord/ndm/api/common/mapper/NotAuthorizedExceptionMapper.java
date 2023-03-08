package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

    @Inject
    MapperConfiguration mapperConfiguration;

    @Override
    public Response toResponse(final NotAuthorizedException exception) {

        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2"))
                        .title(UNAUTHORIZED.getReasonPhrase())
                        .status(UNAUTHORIZED.getStatusCode())
                        .detail(UNAUTHORIZED.getReasonPhrase()).build()).build();
    }
}