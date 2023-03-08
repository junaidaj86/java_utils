package com.postnord.ndm.base.scrolling.model;

public interface ScrollableQueryRequest {

    String getCursor();

    String getQ();

    Integer getLimit();

    String getSort();

    default ScrollableQuery toScrollableQuery() {
        return new ScrollableQuery(getQ(), getCursor(), getLimit(), getSort());
    }
}
