package com.postnord.ndm.base.jsonb_utils;


import com.postnord.ndm.api.common.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class LocalDateAdapterTest {

    @Test
    void localDateToJsonShouldReturnJson() {
        final LocalDate now = LocalDate.now();
        final String result = new LocalDateAdapter().adaptToJson(now);

        Assertions.assertEquals(now.format(DateTimeFormatter.ISO_DATE), result);
    }

    @Test
    void localDateToJsonShouldReturnNullWhenLocalDateIsNull() {
        final String result = new LocalDateAdapter().adaptToJson(null);

        Assertions.assertNull(result);
    }

    @Test
    void jsonToLocalDateShouldReturnLocalDate() {
        final LocalDate now = LocalDate.now();
        final LocalDate result = new LocalDateAdapter().adaptFromJson(now.format(DateTimeFormatter.ISO_DATE));

        Assertions.assertEquals(now, result);
    }

    @Test
    void jsonToLocalDateShouldReturnNullWhenJsonIsNull() {
        final LocalDate result = new LocalDateAdapter().adaptFromJson(null);

        Assertions.assertNull(result);
    }

    @Test
    void jsonToLocalDateShouldThrowAPIExceptionWhenJsonIsInvalidFormat() {
        final LocalDateAdapter localDateAdapter = new LocalDateAdapter();
        Assertions.assertThrows(APIException.class, () -> localDateAdapter.adaptFromJson("2020-10-32"));
    }
}
