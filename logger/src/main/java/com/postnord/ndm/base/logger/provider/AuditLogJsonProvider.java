package com.postnord.ndm.base.logger.provider;

import com.postnord.ndm.base.logger.NdmAuditor;
import com.postnord.ndm.base.logger.util.LoggerContext;
import com.postnord.ndm.base.logger.util.LoggerHelper;
import io.quarkiverse.loggingjson.JsonGenerator;
import io.quarkiverse.loggingjson.JsonProvider;
import io.quarkiverse.loggingjson.JsonWritingUtils;
import org.jboss.logmanager.ExtLogRecord;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Objects;

@Singleton
public class AuditLogJsonProvider implements JsonProvider {
    private static final String FACILITY_FIELD = "facility";
    private static final String FACILITY_VALUE = "log audit";
    private static final String SUBJECT_FIELD = "subject";

    @Inject
    LoggerContext loggerContext;

    @Override
    public void writeTo(final JsonGenerator generator, final ExtLogRecord event) throws IOException {
        if (event.getLoggerName().equals(NdmAuditor.class.getName())) {
            JsonWritingUtils.writeStringField(generator, FACILITY_FIELD, FACILITY_VALUE);
            JsonWritingUtils.writeStringField(generator, SUBJECT_FIELD,
                    Objects.requireNonNullElse(loggerContext.getUpn(), LoggerHelper.UNSET));
        }
    }
}
