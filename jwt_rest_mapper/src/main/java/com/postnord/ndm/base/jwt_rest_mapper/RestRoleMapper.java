package com.postnord.ndm.base.jwt_rest_mapper;

import com.postnord.ndm.base.jwt_handler.adapter.RoleMapper;
import com.postnord.ndm.base.jwt_handler.model.AccountAccessMapping;
import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;
import com.postnord.ndm.base.rest_utils.client.IamClient;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class RestRoleMapper implements RoleMapper {

    @Inject
    AccountManagerClient accountManagerClient;

    @Inject
    IamClient iamClient;

    @Override
    public Uni<List<AccountAccessMapping>> resolvePermissions(final List<AccountRolesMapping> accountRolesMappings) {
        return getToken().chain(token -> accountManagerClient.getPermissions(accountRolesMappings, token));
    }

    Uni<String> getToken() {
        return iamClient.getToken();
    }
}
