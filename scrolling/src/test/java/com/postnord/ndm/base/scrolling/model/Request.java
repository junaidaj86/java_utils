package com.postnord.ndm.base.scrolling.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Request implements ScrollableQueryRequest {

    private final String q;

    private final String cursor;

    private final Integer limit;

    private final String sort;
}
