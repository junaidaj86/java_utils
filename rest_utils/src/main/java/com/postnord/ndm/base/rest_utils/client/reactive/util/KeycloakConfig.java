package com.postnord.ndm.base.rest_utils.client.reactive.util;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "com.postnord.ndm.base.rest_utils.client.reactive.keycloak")
public interface KeycloakConfig {

    String defaultHost();

    Integer defaultPort();

    Integer keepAliveTimeout();

    Integer connectionTimeout();

    Integer responseTimeout();

    Integer poolSize();

    String iotRealm();

    String clientId();

    String clientSecret();

    String grantType();

    Endpoints endpoints();

    interface Endpoints {
        String tokenUri();
    }

}
