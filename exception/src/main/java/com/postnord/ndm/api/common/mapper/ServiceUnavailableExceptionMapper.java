package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import java.net.URI;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static jakarta.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class ServiceUnavailableExceptionMapper implements ExceptionMapper<ServiceUnavailableException> {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @Override
    public Response toResponse(final ServiceUnavailableException exception) {

        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.get().logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.4"))
                        .title(SERVICE_UNAVAILABLE.getReasonPhrase())
                        .status(SERVICE_UNAVAILABLE.getStatusCode())
                        .detail(SERVICE_UNAVAILABLE.getReasonPhrase()).build()).build();
    }
}
