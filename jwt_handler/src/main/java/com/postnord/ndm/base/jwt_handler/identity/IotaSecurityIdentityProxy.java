package com.postnord.ndm.base.jwt_handler.identity;

import com.postnord.ndm.base.jwt_handler.model.AccountInfo;
import io.quarkus.arc.Unremovable;
import io.quarkus.security.runtime.SecurityIdentityAssociation;
import io.quarkus.security.runtime.SecurityIdentityProxy;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;

import java.util.Collections;
import java.util.Set;

@Unremovable
@RequestScoped
@Alternative()
@Priority(Priorities.USER + 1)
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
