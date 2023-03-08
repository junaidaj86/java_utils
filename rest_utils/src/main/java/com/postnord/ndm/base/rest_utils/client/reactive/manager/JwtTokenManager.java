package com.postnord.ndm.base.rest_utils.client.reactive.manager;

import com.postnord.ndm.base.rest_utils.client.reactive.util.ConstantsHelper;
import com.postnord.ndm.base.rest_utils.client.reactive.util.KeycloakConfig;

import java.time.Duration;
import java.time.Instant;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.buffer.Buffer;

import static com.postnord.ndm.base.rest_utils.client.reactive.exception.APIExceptionHelper.createRemoteCallException;
import static com.postnord.ndm.base.rest_utils.client.reactive.util.ConstantsHelper.BASE_URI;
import static com.postnord.ndm.base.rest_utils.client.reactive.util.ConstantsHelper.CLIENT_ID;
import static com.postnord.ndm.base.rest_utils.client.reactive.util.ConstantsHelper.CLIENT_SECRET;
import static com.postnord.ndm.base.rest_utils.client.reactive.util.ConstantsHelper.EXPIRES_IN;
import static com.postnord.ndm.base.rest_utils.client.reactive.util.ConstantsHelper.GRANT_TYPE;
import static com.postnord.ndm.base.rest_utils.client.reactive.util.ConstantsHelper.KEYCLOAK_IS_NOT_WORKING_AT_THIS_MOMENT;
import static com.postnord.ndm.base.rest_utils.client.reactive.util.ConstantsHelper.PROTOCOL_OPENID_CONNECT;

@ApplicationScoped
@SuppressWarnings({"PMD.TooManyStaticImports", "PMD.ExcessiveImports", "PMD.AvoidStringBufferField", "PMD.AppendCharacterWithChar"})
public class JwtTokenManager {

    @Inject
    HttpConnection httpConnection;

    @Inject
    KeycloakConfig keycloakConfig;

    String payload = "";
    Instant expiryTime = Instant.now();
    JsonObject token = new JsonObject();

    @PostConstruct
    public void init() {
        payload = new StringBuffer()
                .append(CLIENT_ID)
                .append("=")
                .append(keycloakConfig.clientId())
                .append("&")
                .append(CLIENT_SECRET)
                .append("=")
                .append(keycloakConfig.clientSecret())
                .append("&")
                .append(GRANT_TYPE)
                .append("=")
                .append(keycloakConfig.grantType())
                .toString();
    }

    public Uni<JsonObject> getManagedToken() {
        if (this.token.isEmpty() ||
                Instant.now().getEpochSecond() >= this.expiryTime.getEpochSecond()) {
            return httpConnection.getKeycloakClient()
                    .request(HttpMethod.POST, BASE_URI + keycloakConfig.iotRealm() + PROTOCOL_OPENID_CONNECT + keycloakConfig.endpoints().tokenUri())
                    .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
                    .sendBuffer(Buffer.buffer(payload))
                    .onItem()
                    .transform(response -> {
                        if (response.statusCode() == Response.Status.OK.getStatusCode()) {
                            this.token = response.bodyAsJsonObject();
                            this.expiryTime = Instant.now().plusSeconds(this.token.getLong(EXPIRES_IN));
                        }
                        return this.token;
                    })
                    .onFailure().retry().withBackOff(Duration.ofMillis(ConstantsHelper.REST_RETRY_INTERVAL)).atMost(ConstantsHelper.REST_MAX_RETRIES)
                    .onFailure().transform(throwable -> {
                        return createRemoteCallException(KEYCLOAK_IS_NOT_WORKING_AT_THIS_MOMENT);
                    });
        } else {
            return Uni
                    .createFrom()
                    .item(this.token);
        }
    }
}
