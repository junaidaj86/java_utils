package com.postnord.ndm.base.logger;

import com.postnord.ndm.base.logger.model.LogRecord;
import com.postnord.ndm.base.logger.util.LoggerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"PMD.ClassNamingConventions", "CPD-START"})
public final class NdmLogger {
    static final Logger LOGGER = LoggerFactory.getLogger(NdmLogger.class);

    private NdmLogger() {
    }

    public static void error(final LogRecord data) {
        if (LOGGER.isErrorEnabled()) {
            LoggerHelper.log(data, LOGGER::error);
        }
    }

    public static void warn(final LogRecord data) {
        if (LOGGER.isWarnEnabled()) {
            LoggerHelper.log(data, LOGGER::warn);
        }
    }

    public static void info(final LogRecord data) {
        if (LOGGER.isInfoEnabled()) {
            LoggerHelper.log(data, LOGGER::info);
        }
    }

    @SuppressWarnings({"CPD-END"})
    public static void debug(final LogRecord data) {
        if (LOGGER.isDebugEnabled()) {
            LoggerHelper.log(data, LOGGER::debug);
        }
    }
}
