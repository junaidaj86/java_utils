package com.postnord.ndm.base.rest_utils.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.security.identity.SecurityIdentity;

@Path("/tokentest")
@SuppressWarnings("PMD.GuardLogStatement")
public class TokenTestResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenTestResource.class);

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed(TestDataHelper.UPN_PERMISSION)
    public String getUsername() {
        LOGGER.debug("Getting username for: {}", securityIdentity.getPrincipal().getName());
        return securityIdentity.getPrincipal().getName();
    }
}
