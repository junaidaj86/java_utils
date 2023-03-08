package com.postnord.ndm.base.scrolling.model;

import javax.ws.rs.core.Response;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class ScrollableResponse {

    private static final String JSON = "application/json";

    @NonNull
    private Scrollable scrollable;

    public static class ScrollableResponseBuilder {

        public Response build() {
            return Response
                    .status(scrollable.getStatus())
                    .entity(scrollable)
                    .type(JSON)
                    .build();
        }
    }
}
