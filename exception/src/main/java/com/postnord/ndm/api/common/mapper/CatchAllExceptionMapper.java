package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.net.URI;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

/**
 * We want to catch all Exception's even if the developer misses them, this will allow us to avoid leaking internal state (and possible
 * technology choices (security threat)) out via our REST API.
 * <p>
 * We also want to provide errors in a predictable and consumable format for our clients, following the traditional 'belt' and 'braces' of
 * scandic design.
 */
@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class CatchAllExceptionMapper implements ExceptionMapper<Throwable> {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @Override
    public Response toResponse(final Throwable exception) {

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
