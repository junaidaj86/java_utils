package com.postnord.ndm.base.scrolling.util;

import com.postnord.ndm.base.scrolling.model.ScrollableQueryRequest;

import java.util.HashMap;
import java.util.Map;

public final class ScrollerTraceHelper {

    public static Map<String, Object> createExtraFields(final ScrollableQueryRequest request) {
        return createExtraFields(request.getQ(), request.getCursor(), request.getLimit(), request.getSort());
    }

    public static Map<String, Object> createExtraFields(final String q, final String cursor, final Integer limit, final String sort) {
        final Map<String, Object> extraFields = new HashMap<>();
        if (q != null) {
            extraFields.put("q", q);
        }
        if (cursor != null) {
            extraFields.put("cursor", cursor);
        }
        if (limit != null) {
            extraFields.put("limit", limit);
        }
        if (sort != null) {
            extraFields.put("sort", sort);
        }
        return extraFields;
    }

    private ScrollerTraceHelper() {
    }
}
