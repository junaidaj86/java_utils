package com.postnord.ndm.base.test_utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.Format;

import java.util.UUID;

import jakarta.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8081})
@SuppressWarnings("PMD.TooManyStaticImports")
class ApiRequestTest {
    private static final String RESPONSE_BODY = "dummy response body";
    private static final String BASE_URL = "http://localhost:8081";
    private static final String BASE_PATH = "/test";
    private static final UUID ACCOUNT_ID = UUID.randomUUID();
    private static final String ROLE = "test";

    @Test
    void testGenerateTokenHeaderSucceed() {
        final String header = ApiUtils.generateTokenHeader(ACCOUNT_ID, ROLE);

        assertNotNull(header);
        assertTrue(header.startsWith("Bearer "));
    }

    @Test
    @SuppressWarnings({"PMD.AvoidCatchingGenericException", "PMD.AvoidPrintStackTrace"})
    void testGet(final MockServerClient client) {
        client
                .when(
                        request()
                                .withHeader(ApiUtils.AUTHORIZATION_HEADER)
                                .withMethod("GET")
                                .withPath(BASE_PATH))
                .respond(
                        response()
                                .withBody(RESPONSE_BODY)
                                .withStatusCode(200));

        try (Response response = ApiUtils.get(BASE_URL + BASE_PATH, ACCOUNT_ID, ROLE, Response.class)) {
            assertNotNull(response);
            assertEquals(200, response.getStatus());
            assertEquals(RESPONSE_BODY, response.readEntity(String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testPost(final MockServerClient client) {
        client
                .when(
                        request()
                                .withHeader(ApiUtils.AUTHORIZATION_HEADER)
                                .withMethod("POST")
                                .withPath(BASE_PATH))
                .respond(
                        response()
                                .withBody(RESPONSE_BODY)
                                .withStatusCode(200));

        try (Response response = ApiUtils.post(BASE_URL + BASE_PATH, ACCOUNT_ID, ROLE, "dummy body", Response.class)) {
            assertEquals(200, response.getStatus());
            assertEquals(RESPONSE_BODY, response.readEntity(String.class));
        }
    }

    @Test
    void testDelete(final MockServerClient client) {
        client
                .when(
                        request()
                                .withHeader(ApiUtils.AUTHORIZATION_HEADER)
                                .withMethod("DELETE")
                                .withPath(BASE_PATH))
                .respond(
                        response()
                                .withStatusCode(204));

        ApiUtils.delete(BASE_URL + BASE_PATH, ACCOUNT_ID, ROLE);

        final String recordedRequests = client
                .retrieveRecordedRequests(
                        request()
                                .withPath(BASE_PATH)
                                .withMethod("DELETE"),
                        Format.JAVA
                );
        assertTrue(recordedRequests.contains("DELETE"));
    }

    @Test
    void testGetNotFound(final MockServerClient client) {
        client
                .when(
                        request()
                                .withHeader(ApiUtils.AUTHORIZATION_HEADER)
                                .withMethod("GET")
                                .withPath(BASE_PATH + "/id"))
                .respond(
                        response()
                                .withStatusCode(404));

        try (Response response = ApiUtils.get(BASE_URL + BASE_PATH + "/id", ACCOUNT_ID, ROLE, Response.class)) {
            assertNotNull(response);
            assertEquals(404, response.getStatus());
        }
    }
}
