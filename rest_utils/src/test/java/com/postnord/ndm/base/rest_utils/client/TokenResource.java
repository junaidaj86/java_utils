package com.postnord.ndm.base.rest_utils.client;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
public class TokenResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenResource.class);

    @Inject
    JwtGenerator jwtGenerator;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.client-id")
    String clientId;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.client-secret")
    String clientSecret;

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public JsonObject getToken(final JsonObject jsonObject) {
        LOGGER.debug("Getting token for credentials: {}", jsonObject);
        if (!jsonObject.getString("clientId").equals(clientId) || !jsonObject.getString("clientSecret").equals(clientSecret)) {
            throw new ForbiddenException();
        }
        return Json.createObjectBuilder()
                .add("access_token", jwtGenerator.generateToken(TestDataHelper.IAM_ACCOUNT_ROLES_MAPPINGS))
                .build();
    }
}