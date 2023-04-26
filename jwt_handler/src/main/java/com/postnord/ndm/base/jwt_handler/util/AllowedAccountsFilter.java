package com.postnord.ndm.base.jwt_handler.util;

import com.postnord.ndm.base.jwt_handler.identity.IotaSecurityIdentityProxy;
import com.postnord.ndm.base.jwt_handler.model.AllowedAccounts;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.annotation.Priority;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.ext.Provider;

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
