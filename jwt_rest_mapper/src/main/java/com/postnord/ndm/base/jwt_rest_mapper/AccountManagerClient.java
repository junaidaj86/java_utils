package com.postnord.ndm.base.jwt_rest_mapper;

import com.postnord.ndm.base.jwt_handler.model.AccountAccessMapping;
import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;

@ApplicationScoped
public class AccountManagerClient {
    private WebClient amClient;

    @Inject
    Vertx vertx;

    @ConfigProperty(name = "com.postnord.ndm.base.jwt_rest_mapper.am-hostname", defaultValue = "localhost")
    String amHostname;

    @ConfigProperty(name = "com.postnord.ndm.base.jwt_rest_mapper.am-port", defaultValue = "8081")
    Integer amPort;

    @ConfigProperty(name = "com.postnord.ndm.base.jwt_rest_mapper.role-mappings.endpoint", defaultValue = "/role-mappings")
    String roleMappingsEndpoint;

    @ConfigProperty(name = "com.postnord.ndm.base.jwt_rest_mapper.retry.min-backoff-ms", defaultValue = "5")
    Long retryMinBackoffMs;

    @ConfigProperty(name = "com.postnord.ndm.base.jwt_rest_mapper.retry.max-backoff-ms", defaultValue = "20")
    Long retryMaxBackoffMs;

    @ConfigProperty(name = "com.postnord.ndm.base.jwt_rest_mapper.retry.max-retries", defaultValue = "10")
    Integer retryMaxRetries;

    @ConfigProperty(name = "com.postnord.ndm.base.jwt_rest_mapper.use-ssl", defaultValue = "false")
    Boolean useSsl;

    @ConfigProperty(name = "com.postnord.ndm.base.jwt_rest_mapper.trust-all", defaultValue = "true")
    Boolean trustAll;

    @PostConstruct
    void createClients() {
        amClient = WebClient.create(
                vertx,
                new WebClientOptions().setDefaultHost(amHostname).setDefaultPort(amPort).setSsl(useSsl).setTrustAll(trustAll)
        );
    }

    public Uni<List<AccountAccessMapping>> getPermissions(final List<AccountRolesMapping> accountRolesMappings, final String token) {
        return amClient
                .post(roleMappingsEndpoint)
                .bearerTokenAuthentication(token)
                .sendJson(accountRolesMappings)
                .map(response -> Arrays.asList(response.bodyAsJson(AccountAccessMapping[].class)))
                .onFailure().retry()
                .withBackOff(Duration.ofMillis(retryMinBackoffMs), Duration.ofMillis(retryMaxBackoffMs)).atMost(retryMaxRetries);
    }

}
