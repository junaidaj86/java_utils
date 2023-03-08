package com.postnord.ndm.api.common.exception;

import com.postnord.ndm.api.common.mapper.MapperConfiguration;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;
import io.quarkus.security.AuthenticationFailedException;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipalFactory;
import io.smallrye.jwt.auth.principal.JWTAuthContextInfo;
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal;
import io.smallrye.jwt.auth.principal.ParseException;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

@ApplicationScoped
@Alternative
@Priority(1)
@SuppressWarnings("PMD.GuardLogStatement")
public class JWTParseExceptionLogger extends DefaultJWTCallerPrincipalFactory {

    @Inject
    MapperConfiguration mapperConfiguration;

    @Override
    @SuppressWarnings("PMD.PreserveStackTrace")
    public JWTCallerPrincipal parse(final String token, final JWTAuthContextInfo authContextInfo) throws ParseException {
        try {
            return super.parse(token, authContextInfo);
        } catch (ParseException parseException) {
            NdmLogger.warn(LogRecord
                    .builder()
                    .exception(new AuthenticationFailedException(parseException))
                    .category(mapperConfiguration.logCategory())
                    .build());
            throw parseException;
        }
    }
}