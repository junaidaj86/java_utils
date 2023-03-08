package com.postnord.ndm.base.rest_utils.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public class MockService implements QuarkusTestResourceLifecycleManager {

    private static final int SERVICE_PORT = 8082;
    private static final String SERVICE_URL_PREFIX = "/service/batch/v1/sim";
    private static final String BASIC_AUTHENTICATION = "Basic " + Base64
            .getEncoder()
            .encodeToString("iotauserapi:iotauserapi"
                    .getBytes(StandardCharsets.UTF_8));
    private static final String APPLICATION_JSON = "application/json";

    private final WireMockServer mockedService = new WireMockServer(WireMockConfiguration
            .options()
            .port(SERVICE_PORT)
            .notifier(new ConsoleNotifier(true)));


    void simulate200SuccessForService(final String requestBodyContent) {
        mockedService.stubFor(post(urlPathEqualTo(SERVICE_URL_PREFIX))
                .withHeader(HttpHeaders.AUTHORIZATION, containing(BASIC_AUTHENTICATION))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(APPLICATION_JSON))
                .withRequestBody(containing(requestBodyContent))
                .willReturn(aResponse().withStatus(200)));
    }

    private String dataFeed() {
        return "[{\"id\":\"12345678\"}]";
    }

    @Override
    public Map<String, String> start() {
        mockedService.start();
        simulate200SuccessForService(dataFeed());
        return Collections.EMPTY_MAP;
    }

    @Override
    public void stop() {
        mockedService.stop();
    }

}
