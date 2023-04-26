package com.postnord.ndm.base.jwt_handler.model;

import io.quarkus.arc.Unremovable;

import jakarta.enterprise.context.RequestScoped;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Unremovable
@RequestScoped
public final class AllowedAccounts {
    private Set<AccountInfo> accounts;

    public Set<AccountInfo> getAccounts() {
        return accounts;
    }

    public void setAccounts(final Set<AccountInfo> accounts) {
        if (this.accounts != null) {
            throw new IllegalStateException("Not allowed to update accounts");
        }
        this.accounts = Collections.unmodifiableSet(accounts);
    }

    public boolean containsSuperUser() {
        return accounts != null && accounts.stream().anyMatch(AccountInfo::superUser);
    }

    public Set<String> toIds() {
        if (accounts == null) {
            return Collections.emptySet();
        }
        return accounts.stream().map(AccountInfo::id).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AllowedAccounts)) {
            return false;
        }
        final AllowedAccounts that = (AllowedAccounts) o;
        return Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounts);
    }

    @Override
    public String toString() {
        return "AllowedAccounts{" +
                "accounts=" + accounts +
                '}';
    }
}
