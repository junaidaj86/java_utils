package com.postnord.ndm.base.common_utils.utils;


import com.postnord.ndm.api.common.exception.APIException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public final class OffsetDateTimeHelper {

    public static String getStringFromOffsetDateTime(final OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return removeNanos(offsetDateTime).format(DataTimeHelper.DATE_TIME_OUTPUT_FORMAT);
    }

    public static String getStringFromOffsetDateTime() {
        return getStringFromOffsetDateTime(OffsetDateTime.now());
    }

    public static OffsetDateTime getOffsetDateTimeOfDefaultOffset(final OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return removeNanos(offsetDateTime).withOffsetSameInstant(DataTimeHelper.DEFAULT_OFFSET);
    }

    public static OffsetDateTime getOffsetDateTimeFromString(final String json) {
        if (json == null) {
            return null;
        }
        return parseOffsetDateTime(json)
                .orElseThrow(() -> new APIException(
                        BAD_REQUEST.getReasonPhrase(),
                        BAD_REQUEST.getStatusCode(),
                        "Invalid timestamp format. " + DataTimeHelper.SUPPORTED_TIMESTAMP_FORMATS
                ));
    }

    private static Optional<OffsetDateTime> parseOffsetDateTime(final String json) {
        try {
            return Optional.of(removeNanos(OffsetDateTime.parse(json, DateTimeFormatter.ISO_DATE_TIME)));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * This method will trim the nanoseconds from the source offset date time and return new offset data time
     *
     * @param offsetDateTime source offset date time
     * @return offset date time
     */
    public static OffsetDateTime removeNanos(final OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return offsetDateTime
                .truncatedTo(ChronoUnit.MILLIS)
                .withOffsetSameInstant(DataTimeHelper.DEFAULT_OFFSET);
    }

    /**
     * This method will return OffsetDateTime without the nanoseconds
     *
     * @return offset date time
     */
    public static OffsetDateTime getOffsetDateTimeAndStripNano() {
        return removeNanos(OffsetDateTime.now());
    }

    private OffsetDateTimeHelper() {
    }
}
