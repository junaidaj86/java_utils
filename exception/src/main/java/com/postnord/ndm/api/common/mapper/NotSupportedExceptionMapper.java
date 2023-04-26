package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import java.net.URI;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static jakarta.ws.rs.core.Response.Status.UNSUPPORTED_MEDIA_TYPE;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class NotSupportedExceptionMapper implements ExceptionMapper<NotSupportedException> {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @Override
    public Response toResponse(final NotSupportedException exception) {

        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.get().logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16"))
                        .title(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
                        .status(UNSUPPORTED_MEDIA_TYPE.getStatusCode())
                        .detail(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()).build()).build();
    }
}
