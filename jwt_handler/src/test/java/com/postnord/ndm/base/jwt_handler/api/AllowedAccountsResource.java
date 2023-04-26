package com.postnord.ndm.base.jwt_handler.api;

import com.postnord.ndm.base.jwt_handler.model.AllowedAccounts;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import static com.postnord.ndm.base.jwt_handler.api.TestDataHelper.PERMISSION_1_1;
import static com.postnord.ndm.base.jwt_handler.api.TestDataHelper.PERMISSION_2_2;
import static com.postnord.ndm.base.jwt_handler.api.TestDataHelper.PERMISSION_SU;

@Path("/allowed-accounts")
public class AllowedAccountsResource {

    @Inject
    AllowedAccounts allowedAccounts;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({PERMISSION_1_1, PERMISSION_2_2, PERMISSION_SU})
    public AllowedAccounts getAccountIds() {
        return allowedAccounts;
    }

    @GET
    @Path("/permit-all")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public AllowedAccounts getAllAccountIds() {
        return allowedAccounts;
    }

    @GET
    @Path("/deny-all")
    @Produces(MediaType.APPLICATION_JSON)
    @DenyAll
    public AllowedAccounts getNoAccountIds() {
        return allowedAccounts;
    }
}
