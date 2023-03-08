package com.postnord.ndm.base.scrolling.model;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;
import com.postnord.ndm.base.scrolling.util.ResultUtils;
import com.postnord.ndm.base.scrolling.util.ScrollerHelper;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RegisterForReflection
public class ScrollableQuery {

    private final boolean encodedNextLink;

    private final String q;

    private final int pageNumber;

    private final int limit;

    private final String sort;

    private final Instant createdAt;

    public ScrollableQuery(final String q, final String cursor, final Integer limit, final String sort) {
        this.encodedNextLink = ScrollerHelper.isEncodedNextLink(cursor);
        if (encodedNextLink) {
            final Map<String, String> params = ScrollerHelper.getNextParamsMapDecoded(cursor);
            this.q = params.getOrDefault(ResultUtils.Q, q);
            this.pageNumber = ScrollerHelper.getPageNumber(params.get(ResultUtils.CURSOR));
            this.limit = Integer.parseInt(params.getOrDefault(ResultUtils.LIMIT, Objects.toString(limit, "100")));
            this.sort = params.getOrDefault(ResultUtils.SORT, sort);
            this.createdAt = InstantHelper.getInstantFromString(params.getOrDefault(ResultUtils.CREATED_AT, InstantHelper.getStringFromInstant()));
        } else {
            this.q = q;
            this.pageNumber = ScrollerHelper.getPageNumber(cursor);
            this.limit = limit;
            this.sort = sort;
            this.createdAt = InstantHelper.getInstantAndStripNano();
        }
    }
}
