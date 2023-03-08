package com.postnord.ndm.base.scrolling.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ScrollableQueryRequestTest {

    private static final String STATUS_ACTIVE = "status==ACTIVE";
    private static final String SORT_ID_DESC = "-id";
    private static final int LIMIT = 20;

    @Test
    void testWhenScrollableQueryRequestIsConvertedToScrollableQuery() {
        final ScrollableQuery query = Request.builder()
                .q(STATUS_ACTIVE)
                .cursor("0")
                .limit(LIMIT)
                .sort(SORT_ID_DESC)
                .build()
                .toScrollableQuery();
        assertFalse(query.isEncodedNextLink());
        assertEquals(STATUS_ACTIVE, query.getQ());
        assertEquals(0, query.getPageNumber());
        assertEquals(LIMIT, query.getLimit());
        assertEquals(SORT_ID_DESC, query.getSort());
    }
}
