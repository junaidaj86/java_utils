package com.postnord.ndm.base.jwt_handler.adapter;

import com.postnord.ndm.base.jwt_handler.model.AccountAccessMapping;
import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;

import java.util.List;

import io.smallrye.mutiny.Uni;

public interface RoleMapper {
    Uni<List<AccountAccessMapping>> resolvePermissions(List<AccountRolesMapping> accountRolesMappings);
}
