package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import java.net.URI;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @Override
    public Response toResponse(final NotAllowedException exception) {

        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.get().logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6"))
                        .title(METHOD_NOT_ALLOWED.getReasonPhrase())
                        .status(METHOD_NOT_ALLOWED.getStatusCode())
                        .detail(METHOD_NOT_ALLOWED.getReasonPhrase()).build()).build();
    }
}
