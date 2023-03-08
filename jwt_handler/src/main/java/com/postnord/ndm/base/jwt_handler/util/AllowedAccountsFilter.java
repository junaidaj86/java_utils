package com.postnord.ndm.base.jwt_handler.util;

import com.postnord.ndm.base.jwt_handler.identity.IotaSecurityIdentityProxy;
import com.postnord.ndm.base.jwt_handler.model.AllowedAccounts;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AllowedAccountsFilter implements ContainerRequestFilter {

    @Inject
    ResourceInfo resourceInfo;

    @Inject
    IotaSecurityIdentityProxy iotaSecurityIdentityProxy;

    @Inject
    Instance<AllowedAccounts> allowedAccountsInstance;

    @Inject
    JWTAnnotationCacheService jwtAnnotationCacheService;

    @Override
    public void filter(final ContainerRequestContext requestContext) {
        final var annotation = jwtAnnotationCacheService.getAnnotation(resourceInfo.getResourceMethod());
        if (annotation == null || annotation instanceof PermitAll) {
            initAllowedAccounts(null);
        } else if (annotation instanceof DenyAll) {
            initAllowedAccounts(Collections.emptySet());
        } else {
            final var rolesAllowedAnnotation = (RolesAllowed) annotation;
            initAllowedAccounts(Stream.of(rolesAllowedAnnotation.value()).collect(Collectors.toSet()));
        }
    }

    private void initAllowedAccounts(final Set<String> requiredPermissions) {
        final var allowedAccounts = allowedAccountsInstance.get();
        allowedAccounts.setAccounts(iotaSecurityIdentityProxy.getAuthorizedAccounts(requiredPermissions));
    }
}
