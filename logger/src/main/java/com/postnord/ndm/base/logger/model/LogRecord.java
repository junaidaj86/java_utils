package com.postnord.ndm.base.logger.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class LogRecord {
    private static final String EXCEPTION_CLASS = "exception_class";
    private static final String EXCEPTION_MESSAGE = "exception_message";

    private String message;
    private String category;
    private Map<String, Object> extraData;

    public static class LogRecordBuilder {
        public LogRecordBuilder() {
            if (Objects.isNull(this.extraData)) {
                this.extraData = new HashMap<>();
            }
        }

        public LogRecordBuilder exception(final Throwable exception) {
            this.extraData.put(EXCEPTION_CLASS, exception.getClass().getName());
            this.extraData.put(EXCEPTION_MESSAGE, exception.getMessage());
            return this;
        }

        public LogRecordBuilder extraData(final Map<String, Object> extraData) {
            this.extraData.putAll(extraData);
            return this;
        }
    }
}
