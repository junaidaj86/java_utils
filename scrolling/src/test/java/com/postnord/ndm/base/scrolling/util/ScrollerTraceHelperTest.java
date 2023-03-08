package com.postnord.ndm.base.scrolling.util;

import com.postnord.ndm.base.scrolling.model.Request;
import com.postnord.ndm.base.scrolling.model.ScrollableQueryRequest;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScrollerTraceHelperTest {

    private static final String STATUS_ACTIVE = "status==ACTIVE";
    private static final String CURSOR = "0";
    private static final String SORT_ID_DESC = "-id";
    private static final int LIMIT = 20;

    @Test
    void testWhenExtraFieldsAreCreatedFromScrollableQueryRequest() {
        final ScrollableQueryRequest request = Request.builder()
                .q(STATUS_ACTIVE)
                .cursor(CURSOR)
                .limit(LIMIT)
                .sort(SORT_ID_DESC)
                .build();
        assertExtraFields(ScrollerTraceHelper.createExtraFields(request));
    }

    @Test
    void testWhenExtraFieldsAreCreatedUsingArguments() {
        assertExtraFields(ScrollerTraceHelper.createExtraFields(STATUS_ACTIVE, CURSOR, LIMIT, SORT_ID_DESC));
    }

    private void assertExtraFields(final Map<String, Object> extraFields) {
        assertEquals(STATUS_ACTIVE, extraFields.get("q"));
        assertEquals(CURSOR, extraFields.get("cursor"));
        assertEquals(LIMIT, extraFields.get("limit"));
        assertEquals(SORT_ID_DESC, extraFields.get("sort"));
    }
}