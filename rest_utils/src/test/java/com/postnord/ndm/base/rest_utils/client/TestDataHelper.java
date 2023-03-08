package com.postnord.ndm.base.rest_utils.client;


import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;

import java.util.List;
import java.util.Set;
import java.util.UUID;

final class TestDataHelper {
    static final UUID ACCOUNT_ID = UUID.randomUUID();
    static final String IAM_ROLE = "iam";
    static final String UPN_PERMISSION = "username";
    static final List<AccountRolesMapping> IAM_ACCOUNT_ROLES_MAPPINGS = List.of(new AccountRolesMapping(ACCOUNT_ID.toString(), Set.of(IAM_ROLE)));

    private TestDataHelper() {
    }
}
