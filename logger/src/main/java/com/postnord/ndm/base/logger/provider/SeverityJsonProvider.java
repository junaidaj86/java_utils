package com.postnord.ndm.base.logger.provider;

import org.jboss.logmanager.ExtLogRecord;

import java.io.IOException;
import java.util.logging.Level;

import javax.inject.Singleton;

import io.quarkiverse.loggingjson.JsonGenerator;
import io.quarkiverse.loggingjson.JsonProvider;
import io.quarkiverse.loggingjson.JsonWritingUtils;

@Singleton
public class SeverityJsonProvider implements JsonProvider {
    private static final String SEVERITY_FIELD = "severity";

    @Override
    public void writeTo(final JsonGenerator generator, final ExtLogRecord event) throws IOException {
        JsonWritingUtils.writeStringField(generator, SEVERITY_FIELD, toSeverity(event.getLevel()));
    }

    private static String toSeverity(final Level level) {
        if (Level.SEVERE.equals(level)) {
            return "error";
        }
        if (Level.WARNING.equals(level)) {
            return "warning";
        }
        if (Level.INFO.equals(level)) {
            return "info";
        }
        return "debug";
    }
}
