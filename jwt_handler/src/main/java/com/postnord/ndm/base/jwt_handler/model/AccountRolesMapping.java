package com.postnord.ndm.base.jwt_handler.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class AccountRolesMapping {
    private String accountId;
    private Set<String> roles;

    public AccountRolesMapping() {
        // For (de-)serialization
    }

    public AccountRolesMapping(final String accountId, final Set<String> roles) {
        setAccountId(accountId);
        setRoles(roles);
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountRolesMapping)) {
            return false;
        }
        final AccountRolesMapping that = (AccountRolesMapping) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, roles);
    }

    @Override
    public String toString() {
        return "AccountRolesMapping{" +
                "accountId=" + accountId +
                ", roles=" + roles +
                '}';
    }
}
