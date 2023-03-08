package com.postnord.ndm.base.jwt_rest_mapper;

import com.postnord.ndm.base.jwt_handler.model.AccountAccessMapping;
import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
public class AccountManagerResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagerResource.class);

    @POST
    @Path("/role-mappings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(TestDataHelper.IAM_PERMISSION)
    public List<AccountAccessMapping> getRoleMappings(final List<AccountRolesMapping> accountRolesMappings) {
        LOGGER.debug("Getting role mappings for: {}", accountRolesMappings);
        final List<AccountAccessMapping> accountAccessMappings = accountRolesMappings.stream()
                .map(mapping -> new AccountAccessMapping(
                                mapping.getAccountId(),
                                mapping.getRoles(),
                                mapping.getRoles().stream().map(TestDataHelper.UPN_ROLE_MAPPINGS::get).flatMap(Collection::stream).collect(Collectors.toSet())
                        )
                )
                .collect(Collectors.toList());
        LOGGER.debug("Resolved role mappings: {}", accountAccessMappings);

        return accountAccessMappings;
    }
}