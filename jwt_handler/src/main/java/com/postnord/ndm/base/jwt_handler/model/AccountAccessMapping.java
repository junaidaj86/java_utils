package com.postnord.ndm.base.jwt_handler.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class AccountAccessMapping {
    private String accountId;
    private Set<String> roles;
    private Set<String> permissions;

    public AccountAccessMapping() {
        // For (de-)serialization
    }

    public AccountAccessMapping(final String accountId, final Set<String> roles, final Set<String> permissions) {
        setAccountId(accountId);
        setRoles(roles);
        setPermissions(permissions);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(final String accountId) {
        if (this.accountId != null) {
            throw new IllegalStateException("Not allowed to update account ID");
        }
        this.accountId = accountId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(final Set<String> roles) {
        if (this.roles != null) {
            throw new IllegalStateException("Not allowed to update roles");
        }
        this.roles = Collections.unmodifiableSet(roles);
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(final Set<String> permissions) {
        if (this.permissions != null) {
            throw new IllegalStateException("Not allowed to update permissions");
        }
        this.permissions = Collections.unmodifiableSet(permissions);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountAccessMapping)) {
            return false;
        }
        final AccountAccessMapping that = (AccountAccessMapping) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(roles, that.roles) && Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, roles, permissions);
    }

    @Override
    public String toString() {
        return "AccountAccessMapping{" +
                "accountId=" + accountId +
                ", roles=" + roles +
                ", permissions=" + permissions +
                '}';
    }
}
