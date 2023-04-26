package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.net.URI;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import io.quarkus.security.ForbiddenException;

import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class QuarkusSecurityExceptionMappers {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @ServerExceptionMapper
    public Response mapException(final ForbiddenException exception) {
        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.get().logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4"))
                        .title(FORBIDDEN.getReasonPhrase())
                        .status(FORBIDDEN.getStatusCode())
                        .detail(FORBIDDEN.getReasonPhrase()).build()).build();
    }

}
