package com.postnord.ndm.base.rest_utils.client;


import com.postnord.ndm.base.test_utils.ApiUtils;

import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/restClientTest")
public class RestClientResource {

    private static final String SERVICE_BASIC_AUTH = "SERVICE_BASIC_AUTH";
    private static final String BASIC = "Basic ";

    @Inject
    RestClient restClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response restClientTest() {
        return restClient
                .post(ApiUtils.generateTokenHeader(UUID.randomUUID(), "test"),
                        dataFeed(),
                        "http://localhost:8082/service/batch/v1/sim",
                        Map.of(HttpHeaders.AUTHORIZATION, BASIC + System.getenv(SERVICE_BASIC_AUTH)));
    }

    private String dataFeed() {
        return "[{\"id\":\"12345678\"}]";
    }
}
