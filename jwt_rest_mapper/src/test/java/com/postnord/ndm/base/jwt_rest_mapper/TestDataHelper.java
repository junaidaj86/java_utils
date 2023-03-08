package com.postnord.ndm.base.jwt_rest_mapper;

import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

final class TestDataHelper {
    static final UUID ACCOUNT_ID = UUID.randomUUID();
    static final String IAM_ROLE = "iam";
    static final String UPN_ROLE = "upn";
    static final String IAM_PERMISSION = "role-mappings";
    static final String UPN_PERMISSION = "username";
    static final Set<String> IAM_PERMISSION_SET = Set.of(IAM_PERMISSION);
    static final Set<String> UPN_PERMISSION_SET = Set.of(UPN_PERMISSION);
    static final Map<String, Set<String>> IAM_ROLE_MAPPINGS = Map.of(IAM_ROLE, IAM_PERMISSION_SET);
    static final Map<String, Set<String>> UPN_ROLE_MAPPINGS = Map.of(UPN_ROLE, UPN_PERMISSION_SET);
    static final List<AccountRolesMapping> IAM_ACCOUNT_ROLES_MAPPINGS = List.of(new AccountRolesMapping(ACCOUNT_ID.toString(), Set.of(IAM_ROLE)));
    static final List<AccountRolesMapping> UPN_ACCOUNT_ROLES_MAPPINGS = List.of(new AccountRolesMapping(ACCOUNT_ID.toString(), Set.of(UPN_ROLE)));

    private TestDataHelper() {
    }
}
