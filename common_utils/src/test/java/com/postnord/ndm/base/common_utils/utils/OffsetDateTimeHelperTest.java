package com.postnord.ndm.base.common_utils.utils;


import com.postnord.ndm.api.common.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

class OffsetDateTimeHelperTest {

    private static final String UTC_DATE_TIME = "2021-01-02T12:34:56.000Z";

    private static final String UTC_DATE_TIME_WITH_NANOS = "2021-01-02T12:34:56.00099Z";

    private static final OffsetDateTime OFFSET_DATE_TIME = OffsetDateTime.parse(UTC_DATE_TIME_WITH_NANOS);

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("+02:00");

    @Test
    void testGetStringFromOffsetDateTimeShouldReturnString() {
        final var result = OffsetDateTimeHelper.getStringFromOffsetDateTime(OFFSET_DATE_TIME);
        Assertions.assertEquals(UTC_DATE_TIME, result);
    }

    @Test
    void testGetStringFromOffsetDateTimeWithOffsetShouldReturnString() {
        final var result = OffsetDateTimeHelper.getStringFromOffsetDateTime(OFFSET_DATE_TIME.withOffsetSameInstant(ZONE_OFFSET));
        Assertions.assertEquals(UTC_DATE_TIME, result);
    }

    @Test
    void testGetStringFromOffsetDateTimeShouldReturnNullWhenOffsetDateTimeIsNull() {
        Assertions.assertNull(OffsetDateTimeHelper.getStringFromOffsetDateTime(null));
    }

    @Test
    void testGetStringFromOffsetDateTimeShouldReturnInstantWithoutNanoSeconds() {
        assertNanos(OffsetDateTime.parse(OffsetDateTimeHelper.getStringFromOffsetDateTime()));
    }

    @Test
    void testGetOffsetDateTimeOfDefaultOffsetShouldReturnOffsetDateTime() {
        Assertions.assertEquals(OffsetDateTime.parse(UTC_DATE_TIME), OffsetDateTimeHelper.getOffsetDateTimeOfDefaultOffset(OFFSET_DATE_TIME.withOffsetSameInstant(ZONE_OFFSET)));
    }

    @Test
    void testGetOffsetDateTimeOfDefaultOffsetShouldReturnNullWhenOffsetDateTimeNull() {
        Assertions.assertNull(OffsetDateTimeHelper.getOffsetDateTimeOfDefaultOffset(null));
    }

    @Test
    void testGetOffsetDateTimeFromStringShouldReturnOffsetDateTime() {
        final var result = OffsetDateTimeHelper.getOffsetDateTimeFromString(UTC_DATE_TIME_WITH_NANOS);
        Assertions.assertEquals(OffsetDateTime.parse(UTC_DATE_TIME), result);
    }

    @Test
    void testGetOffsetDateTimeWithOffsetFromStringShouldReturnOffsetDateTime() {
        final var result = OffsetDateTimeHelper.getOffsetDateTimeFromString(OFFSET_DATE_TIME.withOffsetSameInstant(ZONE_OFFSET).toString());
        Assertions.assertEquals(OffsetDateTime.parse(UTC_DATE_TIME), result);
    }

    @Test
    void testGetOffsetDateTimeFromStringShouldReturnNullWhenStringIsNull() {
        Assertions.assertNull(OffsetDateTimeHelper.getOffsetDateTimeFromString(null));
    }

    @Test
    void testGetOffsetDateTimeFromInvalidStringShouldThrowException() {
        Assertions.assertThrows(APIException.class, () -> OffsetDateTimeHelper.getOffsetDateTimeFromString("2021-11-11"));
    }

    @Test
    void testRemoveNanosShouldReturnOffsetDateTime() {
        Assertions.assertEquals(OffsetDateTime.parse(UTC_DATE_TIME), OffsetDateTimeHelper.removeNanos(OFFSET_DATE_TIME));
    }

    @Test
    void testRemoveNanosShouldReturnNullWhenOffsetDateTimeIsNull() {
        Assertions.assertNull(OffsetDateTimeHelper.removeNanos(null));
    }

    @Test
    void testGetOffsetDateTimeAndStripNanoShouldReturnOffsetDateTimeWithoutNanoSeconds() {
        assertNanos(OffsetDateTimeHelper.getOffsetDateTimeAndStripNano());
    }

    private void assertNanos(final OffsetDateTime offsetDateTime) {
        final var millis = offsetDateTime.get(ChronoField.MILLI_OF_SECOND);
        final var nanos = offsetDateTime.getNano();
        Assertions.assertEquals(0, nanos - millis * 1_000_000);
    }
}
