package com.postnord.ndm.base.rest_utils.client;

import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;
import com.postnord.ndm.base.jwt_handler.util.ResourceAccessParser;
import com.postnord.ndm.base.jwt_helper.JwtUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.InternalServerErrorException;

@ApplicationScoped
public class JwtGenerator {

    @SuppressWarnings("PMD.PreserveStackTrace")
    public String generateToken(final List<AccountRolesMapping> accountRolesMappings) {
        try {
            return JwtUtils.generateTokenString(3600, ResourceAccessParser.toClaim(TestDataHelper.IAM_ACCOUNT_ROLES_MAPPINGS));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
