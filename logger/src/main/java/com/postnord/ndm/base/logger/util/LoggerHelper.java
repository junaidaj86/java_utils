package com.postnord.ndm.base.logger.util;

import com.postnord.ndm.base.logger.model.LogRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import jakarta.enterprise.context.ContextNotActiveException;
import jakarta.enterprise.inject.spi.CDI;

import static io.quarkiverse.loggingjson.providers.KeyValueStructuredArgument.kv;

public final class LoggerHelper {
    private static final String EXTRA_DATA = "extra_data";
    private static final String METADATA = "metadata";
    private static final String CATEGORY = "category";
    private static final String FLOW_ID = "flow_id";
    private static final String CLASS_ID = "resource_class_id";
    private static final String METHOD_ID = "resource_method_id";
    private static final String THREAD_ID = "thread_id";
    private static final String THREAD_NAME = "thread_name";
    public static final String UNSET = "UNSET";

    private LoggerHelper() {
    }

    public static void log(final LogRecord data, final BiConsumer<String, Object[]> logger) {
        logger.accept(
                data.getMessage(),
                Stream.of(kv(METADATA, prepareMetadata(data)), kv(EXTRA_DATA, prepareExtraData(data))).toArray()
        );
    }

    private static Map<String, Object> prepareMetadata(final LogRecord data) {
        return Map.of(CATEGORY, Objects.requireNonNullElse(data.getCategory(), UNSET));
    }

    private static Map<String, Object> prepareExtraData(final LogRecord data) {
        final Map<String, Object> extraData = new HashMap<>(data.getExtraData());
        extraData.putAll(createDefaultExtraData());

        return extraData;
    }

    private static Map<String, Object> createDefaultExtraData() {
        try {
            final LoggerContext loggerContext = CDI.current().select(LoggerContext.class).get();
            return Map.of(
                    FLOW_ID, Objects.requireNonNullElse(loggerContext.getFlowId(), UNSET),
                    CLASS_ID, Objects.requireNonNullElse(loggerContext.getClassId(), UNSET),
                    METHOD_ID, Objects.requireNonNullElse(loggerContext.getMethodId(), UNSET),
                    THREAD_ID, Thread.currentThread().getId(),
                    THREAD_NAME, Thread.currentThread().getName()
            );
        } catch (IllegalStateException | ContextNotActiveException e) {
            return Map.of(
                    FLOW_ID, UNSET,
                    CLASS_ID, UNSET,
                    METHOD_ID, UNSET,
                    THREAD_ID, Thread.currentThread().getId(),
                    THREAD_NAME, Thread.currentThread().getName()
            );
        }
    }
}
