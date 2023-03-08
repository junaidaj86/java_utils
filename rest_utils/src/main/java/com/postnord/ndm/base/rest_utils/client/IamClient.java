package com.postnord.ndm.base.rest_utils.client;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;

@ApplicationScoped
public class IamClient {
    private WebClient kcClient;

    @Inject
    TokenCache tokenCache;

    @Inject
    Vertx vertx;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.client-id")
    String clientId;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.client-secret")
    String clientSecret;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.hostname", defaultValue = "localhost")
    String kcHostname;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.port", defaultValue = "8081")
    Integer kcPort;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.use-ssl", defaultValue = "false")
    Boolean useSsl;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.trust-all", defaultValue = "true")
    Boolean trustAll;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.token.endpoint", defaultValue = "/token")
    String tokenEndpoint;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.retry.min-backoff-ms", defaultValue = "5")
    Long retryMinBackoffMs;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.retry.max-backoff-ms", defaultValue = "20")
    Long retryMaxBackoffMs;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.retry.max-retries", defaultValue = "10")
    Integer retryMaxRetries;

    @PostConstruct
    void createClients() {
        kcClient = WebClient.create(
                vertx,
                new WebClientOptions().setDefaultHost(kcHostname).setDefaultPort(kcPort).setSsl(useSsl).setTrustAll(trustAll)
        );
    }

    public Uni<String> getToken() {
        final String cachedToken = tokenCache.getToken();
        if (cachedToken != null) {
            return Uni.createFrom().item(cachedToken);
        }
        return kcClient
                .post(tokenEndpoint)
                .sendJson(new JsonObject().put("clientId", clientId).put("clientSecret", clientSecret))
                .map(response -> response.bodyAsJsonObject().getString("access_token"))
                .onItem().invoke(token -> tokenCache.refreshToken(token))
                .onFailure().retry()
                .withBackOff(Duration.ofMillis(retryMinBackoffMs), Duration.ofMillis(retryMaxBackoffMs)).atMost(retryMaxRetries);
    }
}
