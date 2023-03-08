package com.postnord.ndm.base.logger.model;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("PMD.ShortMethodName")
public final class LazyLogRecord extends LogRecord {

    private final Supplier<LogRecord> supplier;
    private LogRecord value;

    public static LazyLogRecord of(final Supplier<LogRecord> supplier) {
        return new LazyLogRecord(supplier);
    }

    private LazyLogRecord(final Supplier<LogRecord> supplier) {
        super(null, null, null);
        this.supplier = supplier;
    }

    private LogRecord getLogRecord() {
        if (value == null) {
            return value = supplier.get();
        } else {
            return value;
        }
    }

    @Override
    public String getMessage() {
        return getLogRecord().getMessage();
    }

    @Override
    public String getCategory() {
        return getLogRecord().getCategory();
    }

    @Override
    public Map<String, Object> getExtraData() {
        return getLogRecord().getExtraData();
    }

    @Override
    public void setMessage(final String message) {
        getLogRecord().setMessage(message);
    }

    @Override
    public void setCategory(final String category) {
        getLogRecord().setCategory(category);
    }

    @Override
    public void setExtraData(final Map<String, Object> extraData) {
        getLogRecord().setExtraData(extraData);
    }
}
