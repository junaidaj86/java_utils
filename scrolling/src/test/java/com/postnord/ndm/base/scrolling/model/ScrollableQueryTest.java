package com.postnord.ndm.base.scrolling.model;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.Period;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScrollableQueryTest {

    private static final String STATUS_INACTIVE = "status==INACTIVE";
    private static final String STATUS_ACTIVE = "status==ACTIVE";
    private static final String SORT_ID_DESC = "-id";
    private static final String SORT_ID_ASC = "+id";
    private static final String QUERY_PARAMS = "?sort=" + SORT_ID_ASC + "&cursor=1&limit=40&q=" + STATUS_ACTIVE;
    private static final String QUERY_PARAMS_BASE64 = Base64.getEncoder().encodeToString(QUERY_PARAMS.getBytes(StandardCharsets.UTF_8));
    private static final Instant DATE = InstantHelper.getInstantAndStripNano().minus(Period.ofDays(1));
    private static final String QUERY_PARAMS_CREATED_AT = QUERY_PARAMS + "&created_at=" + DATE;
    private static final String QUERY_PARAMS_CREATED_AT_BASE64 = Base64.getEncoder()
            .encodeToString(QUERY_PARAMS_CREATED_AT.getBytes(StandardCharsets.UTF_8));


    @Test
    void testWhenCursorIsNumericThenParametersShouldBeUsed() {
        final ScrollableQuery query = new ScrollableQuery(STATUS_INACTIVE, "0", 20, SORT_ID_DESC);
        assertFalse(query.isEncodedNextLink());
        assertEquals(STATUS_INACTIVE, query.getQ());
        assertEquals(0, query.getPageNumber());
        assertEquals(20, query.getLimit());
        assertEquals(SORT_ID_DESC, query.getSort());
    }

    @Test
    void testWhenCursorIsNextListThenDecodedParametersShouldBeUsed() {
        final ScrollableQuery query = new ScrollableQuery(STATUS_INACTIVE, QUERY_PARAMS_BASE64, 20, SORT_ID_DESC);
        assertTrue(query.isEncodedNextLink());
        assertEquals(STATUS_ACTIVE, query.getQ());
        assertEquals(1, query.getPageNumber());
        assertEquals(40, query.getLimit());
        assertEquals(SORT_ID_ASC, query.getSort());
    }

    @Test
    void testWhenCursorIsNextListThenCursorTimeStampShouldBeUsed() {
        final ScrollableQuery query = new ScrollableQuery(STATUS_INACTIVE, QUERY_PARAMS_CREATED_AT_BASE64, 20, SORT_ID_DESC);
        assertTrue(query.isEncodedNextLink());
        assertEquals(STATUS_ACTIVE, query.getQ());
        assertEquals(1, query.getPageNumber());
        assertEquals(40, query.getLimit());
        assertEquals(SORT_ID_ASC, query.getSort());
        assertEquals(DATE, query.getCreatedAt());
    }

    @Test
    void testWhenCursorIsNumberThenCurrentTimeTimeStampShouldBeUsed() {
        final ScrollableQuery query = new ScrollableQuery(STATUS_INACTIVE, "0", 20, SORT_ID_DESC);
        assertFalse(query.isEncodedNextLink());
        assertEquals(STATUS_INACTIVE, query.getQ());
        assertEquals(0, query.getPageNumber());
        assertEquals(20, query.getLimit());
        assertEquals(SORT_ID_DESC, query.getSort());
        assertTrue(query.getCreatedAt().isAfter(DATE));
    }
}
