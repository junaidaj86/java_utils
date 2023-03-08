package com.postnord.ndm.base.jwt_handler.identity;

import com.postnord.ndm.base.jwt_handler.adapter.RoleMapper;
import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;
import com.postnord.ndm.base.jwt_handler.util.ResourceAccessParser;

import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.quarkus.arc.Unremovable;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.smallrye.mutiny.Uni;

@Unremovable
@ApplicationScoped
public class ResourceAccessAugmentor implements SecurityIdentityAugmentor {

    @Inject
    RoleMapper rolePermissionMapper;

    @Override
    public Uni<SecurityIdentity> augment(final SecurityIdentity securityIdentity, final AuthenticationRequestContext requestContext) {
        if (!(securityIdentity.getPrincipal() instanceof JsonWebToken)) {
            return Uni.createFrom().item(securityIdentity);
        }
        final List<AccountRolesMapping> accountRolesMappings = ResourceAccessParser.parse((JsonWebToken) securityIdentity.getPrincipal());
        if (accountRolesMappings.isEmpty()) {
            return Uni.createFrom().item(new IotaSecurityIdentity(
                    securityIdentity.getPrincipal(),
                    securityIdentity.getAttributes(),
                    Collections.emptyList()
            ));
        }
        return rolePermissionMapper.resolvePermissions(accountRolesMappings)
                .map(accountAccessMappings ->
                        new IotaSecurityIdentity(
                                securityIdentity.getPrincipal(),
                                securityIdentity.getAttributes(),
                                accountAccessMappings
                        )
                );
    }
}
