package com.postnord.ndm.base.rest_utils.client.reactive.manager;

import com.postnord.ndm.base.rest_utils.client.reactive.util.KeycloakConfig;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import lombok.Getter;

@ApplicationScoped
@SuppressWarnings({"PMD.SingletonClassReturningNewInstance"})
public class HttpConnection {

    @Inject
    Vertx vertx;

    @Inject
    KeycloakConfig keycloakConfig;

    @Getter
    WebClient keycloakClient;

    @PostConstruct
    public void init() {
        this.keycloakClient = getInstance(keycloakConfig.defaultHost(),
                keycloakConfig.defaultPort(),
                keycloakConfig.poolSize(),
                keycloakConfig.connectionTimeout(),
                keycloakConfig.keepAliveTimeout());
    }

    public WebClient getInstance(final String defaultHost,
                                 final int defaultPort,
                                 final int poolSize,
                                 final int connectionTimeout,
                                 final int keepAliveTimeout) {
        return WebClient.create(vertx,
                new WebClientOptions()
                        .setDefaultHost(defaultHost)
                        .setDefaultPort(defaultPort)
                        .setPipelining(true)
                        .setMaxPoolSize(poolSize)
                        .setConnectTimeout(connectionTimeout)
                        .setKeepAliveTimeout(keepAliveTimeout)
                        .setKeepAlive(true));
    }
}
