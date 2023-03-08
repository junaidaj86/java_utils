package com.postnord.ndm.base.test_utils;

import com.postnord.ndm.base.jsonb_utils.JsonbHelper;
import com.postnord.ndm.base.jwt_helper.JwtUtils;
import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;
import lombok.SneakyThrows;

import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Collection of common utilities for API unit testing.
 */
@SuppressWarnings("PMD.GuardLogStatement")
public final class ApiUtils {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    static final Jsonb JSONB = JsonbHelper.createJsonb();
    static final String TARGET_URI = "Target URI";
    static final String APPLICATION_PROBLEM_JSON = "application/problem+json";

    private ApiUtils() {
    }

    /**
     * Returns a String containing the Bearer token.
     *
     * @param accountId the account ID to include in the 'resource_access' claim
     * @param role      the role to include in the 'resource_access' claim
     * @return the token string
     */
    @SneakyThrows
    public static String generateTokenHeader(final UUID accountId, final String role) {
        return generateTokenHeader(UUID.randomUUID(), accountId, role);
    }

    /**
     * Returns a String containing the Bearer token.
     *
     * @param userId    the user ID to populate in the subject claim
     * @param accountId the account ID to include in the 'resource_access' claim
     * @param role      the role to include in the 'resource_access' claim
     * @return the token string
     */
    @SneakyThrows
    public static String generateTokenHeader(final UUID userId, final UUID accountId, final String role) {
        return "Bearer " + JwtUtils.generateTokenString(
                1800,
                userId,
                Map.of(accountId.toString(), Json.createObjectBuilder().add("roles", Json.createArrayBuilder().add(role).build()).build())
        );
    }

    @SuppressWarnings("java:S1604") // Registering a context resolver doesn't support lambdas
    private static Client createJsonbClient() {
        return ClientBuilder.newClient().register(new ContextResolver<Jsonb>() {
            @Override
            public Jsonb getContext(final Class<?> type) {
                return JSONB;
            }
        });
    }

    /**
     * Generates Bearer token and sends GET request to the specified path
     *
     * @param path          an absolute URL for the API request
     * @param accountId     the account ID to include in the 'resource_access' claim
     * @param role          the role to include in the 'resource_access' claim
     * @param responseClass specifies the expected return type
     * @param <R>           type of the response
     * @return The API response as object of type responseClass
     */
    public static <R> R get(final String path, final UUID accountId, final String role, final Class<R> responseClass) {
        final Client client = createJsonbClient();
        final WebTarget target = client.target(path);
        final Invocation.Builder builder = target
                .request(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, generateTokenHeader(accountId, role));
        NdmLogger.debug(LogRecord
                .builder()
                .message("Sending GET request to")
                .extraData(Map.of(TARGET_URI, target.getUri()))
                .build());
        if (responseClass == null) {
            try (Response response = builder.get()) {
                assertEquals(404, response.getStatusInfo().getStatusCode());
            }
            client.close();
            return null;
        }
        final R response = target
                .request(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, generateTokenHeader(accountId, role))
                .get(responseClass);

        NdmLogger.debug(LogRecord
                .builder()
                .message("Got GET response with")
                .extraData(Map.of("Response", response.toString()))
                .build());
        client.close();

        return response;
    }

    /**
     * Generates Bearer token and sends POST request to the specified path
     *
     * @param path          an absolute URL for the API request
     * @param accountId     the account ID to include in the 'resource_access' claim
     * @param role          the role to include in the 'resource_access' claim
     * @param body          the API request body
     * @param responseClass the expected return type
     * @param <R>           type of the response
     * @return The API response as object of type responseClass
     */
    public static <R> R post(final String path, final UUID accountId, final String role, final Object body, final Class<R> responseClass) {
        final Client client = createJsonbClient();
        final WebTarget target = client.target(path);
        NdmLogger.debug(LogRecord
                .builder()
                .message("Sending POST request to")
                .extraData(Map.of(TARGET_URI, target.getUri()))
                .build());
        final R response = target
                .request(MediaType.APPLICATION_JSON, APPLICATION_PROBLEM_JSON)
                .header(AUTHORIZATION_HEADER, generateTokenHeader(accountId, role))
                .post(Entity.entity(body, MediaType.APPLICATION_JSON), responseClass);

        NdmLogger.debug(LogRecord
                .builder()
                .message("Got POST response with")
                .extraData(Map.of("Response", response.toString()))
                .build());
        client.close();

        return response;
    }

    /**
     * Generates Bearer token and sends DELETE request to the specified path
     *
     * @param path      an absolute URL for the API request
     * @param accountId the account ID to include in the 'resource_access' claim
     * @param role      the role to include in the 'resource_access' claim
     */
    public static void delete(final String path, final UUID accountId, final String role) {
        final Client client = createJsonbClient();
        final WebTarget target = client.target(path);
        NdmLogger.debug(LogRecord
                .builder()
                .message("Sending DELETE request to")
                .extraData(Map.of(TARGET_URI, target.getUri()))
                .build());
        try (Response response = target.request()
                .accept(APPLICATION_PROBLEM_JSON)
                .header(AUTHORIZATION_HEADER, generateTokenHeader(accountId, role)).delete()) {
            assertEquals(204, response.getStatusInfo().getStatusCode());
        } finally {
            client.close();
        }
    }
}
