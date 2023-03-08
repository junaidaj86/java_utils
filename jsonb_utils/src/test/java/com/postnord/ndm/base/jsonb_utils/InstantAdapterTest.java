package com.postnord.ndm.base.jsonb_utils;


import com.postnord.ndm.api.common.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

class InstantAdapterTest {

    private static final String UTC_DATE_TIME = "2020-01-02T12:34:56.000Z";

    private static final String UTC_DATE_TIME_WITH_NANOS = "2020-01-02T12:34:56.000123Z";

    private static final Instant INSTANT = Instant.parse(UTC_DATE_TIME_WITH_NANOS);

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("-01:00");

    private static final String OFFSET_DATE_TIME = OffsetDateTime.ofInstant(INSTANT, ZONE_OFFSET).toString();

    private static final String ZONED_DATE_TIME = ZonedDateTime.ofInstant(INSTANT, ZONE_OFFSET).toString();

    @Test
    void instantToJsonShouldReturnJson() {
        final var result = new InstantAdapter().adaptToJson(INSTANT);
        Assertions.assertEquals(UTC_DATE_TIME, result);
    }

    @Test
    void instantToJsonShouldReturnNullWhenInstantIsNull() {
        final var result = new InstantAdapter().adaptToJson(null);
        Assertions.assertNull(result);
    }

    @Test
    void utcJsonToInstantShouldReturnInstant() {
        final var result = new InstantAdapter().adaptFromJson(UTC_DATE_TIME_WITH_NANOS);
        Assertions.assertEquals(Instant.parse(UTC_DATE_TIME), result);
    }

    @Test
    void offsetDateTimeJsonToInstantShouldReturnInstant() {
        final var result = new InstantAdapter().adaptFromJson(OFFSET_DATE_TIME);
        Assertions.assertEquals(Instant.parse(UTC_DATE_TIME), result);
    }

    @Test
    void zonedDateTimeJsonToInstantShouldReturnInstant() {
        final var result = new InstantAdapter().adaptFromJson(ZONED_DATE_TIME);
        Assertions.assertEquals(Instant.parse(UTC_DATE_TIME), result);
    }

    @Test
    void jsonToInstantShouldReturnNullWhenJsonIsNull() {
        final var result = new InstantAdapter().adaptFromJson(null);
        Assertions.assertNull(result);
    }

    @Test
    void jsonToInstantShouldThrowAPIExceptionWhenJsonIsInvalidFormat() {
        final InstantAdapter instantAdapter = new InstantAdapter();
        Assertions.assertThrows(APIException.class, () -> instantAdapter.adaptFromJson("2020-10-28"));
    }
}
