package com.postnord.ndm.base.scrolling.util;

import com.postnord.ndm.api.common.exception.APIException;
import com.postnord.ndm.base.scrolling.model.Scrollable;
import com.postnord.ndm.base.scrolling.model.ScrollableQuery;
import com.postnord.ndm.base.scrolling.model.ScrollableResponse;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import io.netty.handler.codec.http.HttpResponseStatus;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@SuppressWarnings("PMD.PreserveStackTrace")
public final class ScrollerHelper {

    private ScrollerHelper() {
    }

    public static <T> Response getScrollableResponse(final List<T> items,
                                                     final ScrollableQuery query) {

        return ScrollableResponse.builder()
                .scrollable(Scrollable.builder()
                        .items(getItems(items, query.getLimit()))
                        .page(query.getPageNumber()).limit(query.getLimit())
                        .createdAt(query.getCreatedAt())
                        .rawQueryParams(getBaseQueryParams(query.getQ(), query.getSort()))
                        .hasNextPage(items.size() > query.getLimit())
                        .status(HttpResponseStatus.OK.code())
                        .build())
                .build();
    }

    public static <T> Response getScrollableResponse(final List<T> items,
                                                     final int totalCount,
                                                     final ScrollableQuery query) {

        return ScrollableResponse.builder()
                .scrollable(Scrollable.builder()
                        .items(getItems(items, query.getLimit()))
                        .page(query.getPageNumber()).limit(query.getLimit())
                        .createdAt(query.getCreatedAt())
                        .rawQueryParams(getBaseQueryParams(query.getQ(), query.getSort()))
                        .totalCount(totalCount)
                        .status(HttpResponseStatus.OK.code())
                        .build())
                .build();

    }

    public static int getPageNumber(final String cursor) {
        if (cursor == null) {
            return 0;
        }
        try {
            return Math.max(0, Integer.parseInt(cursor));
        } catch (NumberFormatException e) {
            throw new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), "Invalid cursor");
        }
    }

    public static Map<String, String> getNextParamsMapDecoded(final String base64params) {
        final Map<String, String> queryPairs = new LinkedHashMap<>();
        final String url = getNextLinkDecoded(base64params);
        if (url != null && !url.isEmpty() && url.charAt(0) == '?') {
            final String[] pairs = url.substring(1).split("&");
            for (final String pair : pairs) {
                final int idx = pair.indexOf('=');
                if (idx != -1) {
                    queryPairs.put(pair.substring(0, idx), pair.substring(idx + 1));
                }
            }
        }
        return queryPairs;
    }

    public static String getNextLinkDecoded(final String base64next) {
        if (base64next == null) {
            return null;
        }
        if (base64next.isBlank()) {
            return "";
        }
        try {
            return new String(Base64.getDecoder().decode(base64next.getBytes(StandardCharsets.UTF_8)));
        } catch (IllegalArgumentException e) {
            throw new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), "Invalid cursor");
        }
    }

    public static boolean isEncodedNextLink(final String cursor) {
        if (cursor == null || cursor.length() % 4 != 0) {
            return false;
        }
        boolean isNumeric = true;
        for (int i = 0; i < cursor.length(); i++) {
            if (!Character.isDigit(cursor.charAt(i))) {
                isNumeric = false;
                break;
            }
        }
        return !isNumeric;
    }

    private static <T> List<T> getItems(final List<T> items, final int maxSize) {
        if (maxSize < items.size()) {
            return items.subList(0, maxSize);
        }
        return items;
    }

    private static String getBaseQueryParams(final String q, final String sort) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (q != null && !q.isBlank()) {
            stringBuilder.append("q=").append(q);
        }
        if (sort != null && !sort.isBlank()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append('&');
            }
            stringBuilder.append("sort=").append(sort);
        }
        return stringBuilder.toString();
    }
}
