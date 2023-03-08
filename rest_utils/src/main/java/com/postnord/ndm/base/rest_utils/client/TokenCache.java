package com.postnord.ndm.base.rest_utils.client;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenCache {
    private AtomicReference<TokenContext> atomicReference;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.token.refresh-seconds", defaultValue = "1500")
    Integer iamTokenRefreshSeconds;

    @PostConstruct
    void init() {
        atomicReference = new AtomicReference<>();
    }

    public String getToken() {
        final TokenContext tokenContext = atomicReference.get();
        if (tokenContext == null || Duration.between(tokenContext.getInstant(), Instant.now()).getSeconds() > iamTokenRefreshSeconds) {
            return null;
        }
        return tokenContext.getToken();
    }

    public void refreshToken(final String token) {
        atomicReference.set(new TokenContext(token, Instant.now()));
    }
}
