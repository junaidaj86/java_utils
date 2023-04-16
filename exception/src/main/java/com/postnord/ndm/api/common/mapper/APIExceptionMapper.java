package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.exception.APIException;
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

@Provider
@SuppressWarnings("PMD.GuardLogStatement")
public class APIExceptionMapper implements ExceptionMapper<APIException> {

    @Inject
    Instance<MapperConfiguration> mapperConfiguration;

    @Override
    public Response toResponse(final APIException exception) {

        NdmLogger.warn(LogRecord
                .builder()
                .exception(exception)
                .category(mapperConfiguration.get().logCategory())
                .build());

        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(URI.create("http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1"))
                        .title(exception.getTitle())
                        .status(exception.getStatus())
                        .detail(exception.getDetail())
                        .parameters(exception.getParameters())
                        .build()).build();
    }
}
