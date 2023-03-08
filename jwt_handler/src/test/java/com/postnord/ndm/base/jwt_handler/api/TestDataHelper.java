package com.postnord.ndm.base.jwt_handler.api;

import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

final class TestDataHelper {
    static final String ACCOUNT_ID_1 = UUID.randomUUID().toString();
    static final String ACCOUNT_ID_2 = UUID.randomUUID().toString();
    static final String ACCOUNT_ID_3 = UUID.randomUUID().toString();
    static final String ROLE_1 = "role1";
    static final String ROLE_2 = "role2";
    static final String ROLE_3 = "role3";
    static final String ROLE_4 = "role4";
    static final String ROLE_5 = "role5";
    static final String PERMISSION_1_1 = "permission1.1";
    static final String PERMISSION_1_2 = "permission1.2";
    static final String PERMISSION_2_1 = "permission2.1";
    static final String PERMISSION_2_2 = "permission2.2";
    static final String PERMISSION_3_1 = "permission3.1";
    static final String PERMISSION_SU = "account-mgr.super-user";
    static final Set<String> PERMISSION_SET_1 = Set.of(PERMISSION_1_1, PERMISSION_1_2);
    static final Set<String> PERMISSION_SET_2 = Set.of(PERMISSION_2_1, PERMISSION_2_2);
    static final Set<String> PERMISSION_SET_3 = Set.of(PERMISSION_3_1);
    static final Set<String> PERMISSION_SET_4 = Set.of();
    static final Set<String> PERMISSION_SET_5 = Set.of(PERMISSION_SU);
    static final Map<String, Set<String>> ROLE_MAPPINGS = Map.of(
            ROLE_1, PERMISSION_SET_1,
            ROLE_2, PERMISSION_SET_2,
            ROLE_3, PERMISSION_SET_3,
            ROLE_4, PERMISSION_SET_4,
            ROLE_5, PERMISSION_SET_5
    );
    static final List<AccountRolesMapping> ACCOUNT_ROLES_MAPPINGS_1 = List.of(
            new AccountRolesMapping(ACCOUNT_ID_1, Set.of(ROLE_1, ROLE_3)),
            new AccountRolesMapping(ACCOUNT_ID_2, Set.of(ROLE_2, ROLE_3)),
            new AccountRolesMapping(ACCOUNT_ID_3, Set.of(ROLE_3, ROLE_4))
    );
    static final List<AccountRolesMapping> ACCOUNT_ROLES_MAPPINGS_2 = List.of(
            new AccountRolesMapping(ACCOUNT_ID_1, Set.of(ROLE_3, ROLE_4))
    );
    static final List<AccountRolesMapping> ACCOUNT_ROLES_MAPPINGS_3 = List.of(
            new AccountRolesMapping(ACCOUNT_ID_1, Set.of(ROLE_5))
    );

    private TestDataHelper() {
    }
}
