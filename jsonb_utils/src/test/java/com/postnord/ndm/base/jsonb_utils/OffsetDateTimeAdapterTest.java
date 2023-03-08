package com.postnord.ndm.base.jsonb_utils;


import com.postnord.ndm.api.common.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class OffsetDateTimeAdapterTest {

    private static final String UTC_DATE_TIME = "2021-01-02T12:34:56.000Z";

    private static final String UTC_DATE_TIME_WITH_NANOS = "2021-01-02T12:34:56.000123Z";

    private static final OffsetDateTime OFFSET_DATE_TIME = OffsetDateTime.parse(UTC_DATE_TIME_WITH_NANOS);

    private static final String INSTANT = OFFSET_DATE_TIME.toInstant().toString();

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("-02:00");

    private static final String ZONED_DATE_TIME = OFFSET_DATE_TIME.atZoneSameInstant(ZONE_OFFSET).toString();

    @Test
    void instantToJsonShouldReturnJson() {
        final var result = new OffsetDateTimeAdapter().adaptToJson(OFFSET_DATE_TIME);
        Assertions.assertEquals(UTC_DATE_TIME, result);
    }

    @Test
    void instantToJsonShouldReturnNullWhenOffsetDateTimeIsNull() {
        final var result = new OffsetDateTimeAdapter().adaptToJson(null);
        Assertions.assertNull(result);
    }

    @Test
    void utcJsonToOffsetDateTimeShouldReturnOffsetDateTime() {
        final var result = new OffsetDateTimeAdapter().adaptFromJson(UTC_DATE_TIME_WITH_NANOS);
        Assertions.assertEquals(OffsetDateTime.parse(UTC_DATE_TIME), result);
    }

    @Test
    void instantJsonToOffsetDateTimeShouldReturnOffsetDateTime() {
        final var result = new OffsetDateTimeAdapter().adaptFromJson(INSTANT);
        Assertions.assertEquals(OffsetDateTime.parse(UTC_DATE_TIME), result);
    }

    @Test
    void zonedDateTimeJsonToOffsetDateTimeShouldReturnOffsetDateTime() {
        final var result = new OffsetDateTimeAdapter().adaptFromJson(ZONED_DATE_TIME);
        Assertions.assertEquals(OffsetDateTime.parse(UTC_DATE_TIME), result);
    }

    @Test
    void jsonToOffsetDateTimeShouldReturnNullWhenJsonIsNull() {
        final var result = new OffsetDateTimeAdapter().adaptFromJson(null);
        Assertions.assertNull(result);
    }

    @Test
    void jsonToOffsetDateTimeShouldThrowAPIExceptionWhenJsonIsInvalidFormat() {
        final OffsetDateTimeAdapter instantAdapter = new OffsetDateTimeAdapter();
        Assertions.assertThrows(APIException.class, () -> instantAdapter.adaptFromJson("2020-10-28"));
    }
}
