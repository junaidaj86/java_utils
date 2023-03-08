package com.postnord.ndm.base.jwt_handler.adapter;

import com.postnord.ndm.base.jwt_handler.model.AccountAccessMapping;
import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;
import io.quarkus.arc.DefaultBean;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;

@DefaultBean
@ApplicationScoped
public class DefaultRoleMapper implements RoleMapper {

    @Override
    public Uni<List<AccountAccessMapping>> resolvePermissions(final List<AccountRolesMapping> accountRolesMappings) {
        return Uni.createFrom().item(
                accountRolesMappings.stream()
                        .map(mapping -> new AccountAccessMapping(
                                        mapping.getAccountId(),
                                        Collections.unmodifiableSet(mapping.getRoles()),
                                        Collections.unmodifiableSet(mapping.getRoles())
                                )
                        )
                        .toList()
        );
    }
}
