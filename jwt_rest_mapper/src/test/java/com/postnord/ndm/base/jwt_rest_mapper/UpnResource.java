package com.postnord.ndm.base.jwt_rest_mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.security.identity.SecurityIdentity;

@Path("/upn")
@SuppressWarnings("PMD.GuardLogStatement")
public class UpnResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpnResource.class);

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
