package com.postnord.ndm.base.common_utils.utils;

import com.postnord.ndm.api.common.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

class InstantHelperTest {

    private static final String UTC_DATE_TIME = "2020-01-02T12:34:56.000Z";

    private static final String UTC_DATE_TIME_WITH_NANOS = "2020-01-02T12:34:56.00099Z";

    private static final Instant INSTANT = Instant.parse(UTC_DATE_TIME_WITH_NANOS);

    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("+01:00");

    private static final String OFFSET_DATE = INSTANT.atOffset(ZONE_OFFSET).toString();

    @Test
    void testGetStringFromInstantShouldReturnString() {
        final var result = InstantHelper.getStringFromInstant(INSTANT);
        Assertions.assertEquals(UTC_DATE_TIME, result);
    }

    @Test
    void testGetStringFromInstantShouldReturnNullWhenInstantIsNull() {
        Assertions.assertNull(InstantHelper.getStringFromInstant(null));
    }

    @Test
    void testGetStingFromInstantShouldReturnInstantWithoutNanoSeconds() {
        assertNanos(Instant.parse(InstantHelper.getStringFromInstant()));
    }

    @Test
    void testGetInstantOfDefaultOffsetShouldReturnInstant() {
        Assertions.assertEquals(Instant.parse(UTC_DATE_TIME), InstantHelper.getInstantOfDefaultOffset(INSTANT.atOffset(ZONE_OFFSET).toInstant()));
    }

    @Test
    void testGetInstantOfDefaultOffsetShouldReturnNullWhenInstantIsNull() {
        Assertions.assertNull(InstantHelper.getInstantOfDefaultOffset(null));
    }

    @Test
    void testGetInstantFromStringShouldReturnInstant() {
        final var result = InstantHelper.getInstantFromString(UTC_DATE_TIME_WITH_NANOS);
        Assertions.assertEquals(Instant.parse(UTC_DATE_TIME), result);
    }

    @Test
    void testGetOffsetDateTimeFromStringShouldReturnInstant() {
        final var result = InstantHelper.getInstantFromString(OFFSET_DATE);
        Assertions.assertEquals(Instant.parse(UTC_DATE_TIME), result);
    }

    @Test
    void testGetInstantFromStringShouldReturnNullWhenStringIsNull() {
        Assertions.assertNull(InstantHelper.getInstantFromString(null));
    }

    @Test
    void testGetInstantFromInvalidStringShouldThrowException() {
        Assertions.assertThrows(APIException.class, () -> InstantHelper.getInstantFromString("2021-11-11"));
    }

    @Test
    void testRemoveNanosShouldReturnInstant() {
        Assertions.assertEquals(Instant.parse(UTC_DATE_TIME), InstantHelper.removeNanos(INSTANT));
    }

    @Test
    void testRemoveNanosShouldReturnNullWhenInstantIsNull() {
        Assertions.assertNull(InstantHelper.removeNanos(null));
    }

    @Test
    void testGetInstantAndStripNanoShouldReturnInstantWithoutNanoSeconds() {
        assertNanos(InstantHelper.getInstantAndStripNano());
    }

    private void assertNanos(final Instant instant) {
        final var millis = instant.get(ChronoField.MILLI_OF_SECOND);
        final var nanos = instant.getNano();
        Assertions.assertEquals(0, nanos - millis * 1_000_000);
    }
}
