package com.postnord.ndm.base.jwt_handler.api;

import com.postnord.ndm.base.jwt_handler.adapter.RoleMapper;
import com.postnord.ndm.base.jwt_handler.model.AccountAccessMapping;
import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

import io.smallrye.mutiny.Uni;

import static com.postnord.ndm.base.jwt_handler.api.TestDataHelper.ROLE_MAPPINGS;

@ApplicationScoped
public class TestRoleMapper implements RoleMapper {

    @Override
    public Uni<List<AccountAccessMapping>> resolvePermissions(final List<AccountRolesMapping> accountRolesMappings) {
        return Uni.createFrom().item(accountRolesMappings.stream()
                .map(mapping -> new AccountAccessMapping(
                                mapping.getAccountId(),
                                Collections.unmodifiableSet(mapping.getRoles()),
                                mapping.getRoles().stream().map(ROLE_MAPPINGS::get).flatMap(Collection::stream).collect(Collectors.toUnmodifiableSet())
                        )
                )
                .collect(Collectors.toUnmodifiableList())
        );
    }
}
