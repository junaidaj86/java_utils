package com.postnord.ndm.base.jwt_handler.identity;

import com.postnord.ndm.base.jwt_handler.model.AccountInfo;

import java.util.Collections;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Priorities;

import io.quarkus.arc.AlternativePriority;
import io.quarkus.arc.Unremovable;
import io.quarkus.security.runtime.SecurityIdentityAssociation;
import io.quarkus.security.runtime.SecurityIdentityProxy;

@Unremovable
@RequestScoped
@AlternativePriority(Priorities.USER + 1)
public class IotaSecurityIdentityProxy extends SecurityIdentityProxy {

    @Inject
    SecurityIdentityAssociation securityIdentityAssociation;

    public Set<AccountInfo> getAuthorizedAccounts(final Set<String> requiredPermissions) {
        if (!(securityIdentityAssociation.getIdentity() instanceof IotaSecurityIdentity)) {
            return Collections.emptySet();
        }
        final IotaSecurityIdentity securityIdentity = (IotaSecurityIdentity) securityIdentityAssociation.getIdentity();
        return securityIdentity.getAuthorizedAccounts(requiredPermissions);
    }
}
