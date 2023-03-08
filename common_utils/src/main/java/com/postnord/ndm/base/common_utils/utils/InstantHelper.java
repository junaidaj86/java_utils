package com.postnord.ndm.base.common_utils.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class InstantHelper {

    public static String getStringFromInstant(final Instant instant) {
        if (instant == null) {
            return null;
        }
        return removeNanos(instant)
                .atOffset(DataTimeHelper.DEFAULT_OFFSET)
                .format(DataTimeHelper.DATE_TIME_OUTPUT_FORMAT);
    }

    public static String getStringFromInstant() {
        return getStringFromInstant(Instant.now());
    }

    /**
     * @param instant source instant
     * @return instant
     * @deprecated This method does not make sense since Instant is always UTC. Use {@link #removeNanos(Instant instant)}
     */
    @Deprecated
    public static Instant getInstantOfDefaultOffset(final Instant instant) {
        return removeNanos(instant);
    }

    public static Instant getInstantFromString(final String json) {
        if (json == null) {
            return null;
        }
        return OffsetDateTimeHelper.getOffsetDateTimeFromString(json).toInstant();
    }

    /**
     * This method will trim the nanoseconds from the source instant and return new instant
     *
     * @param instant source instant
     * @return instant
     */
    public static Instant removeNanos(final Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.truncatedTo(ChronoUnit.MILLIS);
    }

    /**
     * This method will return Instant without the nanoseconds
     *
     * @return instant
     */
    public static Instant getInstantAndStripNano() {
        return removeNanos(Instant.now());
    }

    private InstantHelper() {
    }
}
