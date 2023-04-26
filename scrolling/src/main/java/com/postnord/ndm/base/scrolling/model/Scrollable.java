package com.postnord.ndm.base.scrolling.model;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTransient;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@JsonbPropertyOrder({
        "items",
        "next"
})
@ToString
@Builder(builderClassName = "ScrollableBuilder", buildMethodName = "buildInternal")
@Getter
public class Scrollable {

    @NonNull
    @JsonbProperty("items")
    private List<?> items;
    @NonNull
    @JsonbTransient
    private Integer limit;
    @NonNull
    @JsonbTransient
    private Integer page;
    @JsonbTransient
    private Boolean hasNextPage;
    @JsonbProperty("next")
    private String next;
    @NonNull
    @JsonbTransient
    private String rawQueryParams;
    @JsonbTransient
    private Integer totalCount;
    @JsonbTransient
    private int status;
    @JsonbTransient
    private Instant createdAt;

    public void buildNextLink() {
        if (Boolean.TRUE.equals(hasNextPage) || pagesLeft()) {
            next = constructNextPageUri();
        }
    }

    private boolean pagesLeft() {
        return totalCount != null && (limit * page) < totalCount;
    }

    private String constructNextPageUri() {
        final StringBuilder builder = new StringBuilder(128);
        builder.append('?').append(rawQueryParams)
                .append("&cursor=").append(getNextPage())
                .append("&limit=").append(limit)
                .append("&created_at=").append(createdAt.toString());
        return Base64.getEncoder().encodeToString(builder.toString().getBytes(StandardCharsets.UTF_8));
    }

    private int getNextPage() {
        return page + 1;
    }

    @JsonbTransient
    public String getNextLinkEncoded() {
        return next;
    }

    @JsonbTransient
    public String getNextLinkDecoded() {
        String decodedNext = null;
        if (next != null) {
            decodedNext = new String(Base64.getDecoder().decode(next.getBytes(StandardCharsets.UTF_8)));
        }
        return decodedNext;
    }

    public Integer getPageSize() {
        return limit;
    }

    public static class ScrollableBuilder {

        private Scrollable obj;

        public Scrollable build() {
            obj = this.buildInternal();
            validate();
            obj.buildNextLink();
            return obj;
        }

        private void validate() {
            Objects.requireNonNull(obj.page, "page cannot be null");
            Objects.requireNonNull(obj.limit, "limit cannot be null");
            Objects.requireNonNull(obj.rawQueryParams, "rawQueryParams cannot be null");

            if (totalCount == null && hasNextPage == null) {
                throw new IllegalArgumentException("totalCount or hasNext has to be set");
            }
        }
    }
}
