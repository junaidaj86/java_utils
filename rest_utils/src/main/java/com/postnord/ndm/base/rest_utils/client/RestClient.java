package com.postnord.ndm.base.rest_utils.client;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class RestClient {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private Client client;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
    }

    @PreDestroy
    public void dispose() {
        client.close();
    }

    public Response post(final String token,
                         final String data,
                         final String url,
                         final Map<String, String> extraHeaders) {


        final Invocation.Builder request = client
                .target(url)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, token);
        for (final Map.Entry<String, String> entry : extraHeaders.entrySet()) {
            request.header(entry.getKey(), entry.getValue());
        }
        return request.post(Entity.entity(data, MediaType.APPLICATION_JSON));

    }

    public Response get(final String token,
                        final String url,
                        final Map<String, String> queryParams,
                        final Map<String, String> extraHeaders) {

        final WebTarget webTarget = client
                .target(url);
        for (final Map.Entry<String, String> entry : queryParams.entrySet()) {
            webTarget.queryParam(entry.getKey(), entry.getValue());
        }
        final Invocation.Builder request = webTarget
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER, token);
        for (final Map.Entry<String, String> entry : extraHeaders.entrySet()) {
            request.header(entry.getKey(), entry.getValue());
        }
        return request.get();
    }

}
