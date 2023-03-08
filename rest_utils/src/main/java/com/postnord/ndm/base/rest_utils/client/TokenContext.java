package com.postnord.ndm.base.rest_utils.client;

import java.time.Instant;

public final class TokenContext {
    private final String token;
    private final Instant instant;

    public TokenContext(final String token, final Instant instant) {
        this.token = token;
        this.instant = instant;
    }

    public String getToken() {
        return token;
    }

    public Instant getInstant() {
        return instant;
    }
}
