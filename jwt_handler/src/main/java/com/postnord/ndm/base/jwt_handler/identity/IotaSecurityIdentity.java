package com.postnord.ndm.base.jwt_handler.identity;

import com.postnord.ndm.base.jwt_handler.model.AccountAccessMapping;
import com.postnord.ndm.base.jwt_handler.model.AccountInfo;
import io.quarkus.security.credential.Credential;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.config.ConfigProvider;

import java.security.Permission;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

public class IotaSecurityIdentity implements SecurityIdentity {
    private static final Optional<String> SUPER_USER_PERMISSION =
            ConfigProvider.getConfig().getOptionalValue("com.postnord.ndm.account_manager.permission.super-user", String.class);
    private final Principal principal;
    private final Map<String, Object> attributes;
    private final List<AccountAccessMapping> accountAccessMappings;
    private final Set<String> permissions;

    public IotaSecurityIdentity(final Principal principal,
                                final Map<String, Object> attributes,
                                final List<AccountAccessMapping> accountAccessMappings) {
        this.principal = principal;
        this.attributes = Collections.unmodifiableMap(attributes);
        this.accountAccessMappings = Collections.unmodifiableList(accountAccessMappings);
        if (accountAccessMappings.isEmpty()) {
            this.permissions = Collections.emptySet();
        } else {
            this.permissions = accountAccessMappings
                    .stream().map(AccountAccessMapping::getPermissions).flatMap(Collection::stream).collect(Collectors.toUnmodifiableSet());
        }
    }

    public Set<AccountInfo> getAuthorizedAccounts(final Set<String> requiredPermissions) {
        if (accountAccessMappings.isEmpty()) {
            return Collections.emptySet();
        }
        if (requiredPermissions == null) {
            return accountAccessMappings.stream().map(IotaSecurityIdentity::toAccountInfo).collect(Collectors.toUnmodifiableSet());
        }
        return accountAccessMappings.stream()
                .filter(mapping -> requiredPermissions.stream().anyMatch(mapping.getPermissions()::contains))
                .map(IotaSecurityIdentity::toAccountInfo)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    @Override
    public Set<String> getRoles() {
        return permissions;
    }

    @Override
    public boolean hasRole(final String role) {
        return permissions.contains(role);
    }

    @Override
    public <T extends Credential> T getCredential(final Class<T> credentialType) {
        return null;
    }

    @Override
    public Set<Credential> getCredentials() {
        return Collections.emptySet();
    }

    @Override
    public <T> T getAttribute(final String name) {
        //noinspection unchecked
        return (T) attributes.get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Uni<Boolean> checkPermission(final Permission permission) {
        return Uni.createFrom().item(true);
    }

    private static AccountInfo toAccountInfo(final AccountAccessMapping accountAccessMapping) {
        final boolean superUser =
                SUPER_USER_PERMISSION.isPresent() && accountAccessMapping.getPermissions().contains(SUPER_USER_PERMISSION.get());
        return new AccountInfo(accountAccessMapping.getAccountId(), superUser);
    }
}
