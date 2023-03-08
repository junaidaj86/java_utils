package com.postnord.ndm.base.jwt_handler.api;

import com.postnord.ndm.base.jwt_handler.model.AccountInfo;
import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;
import com.postnord.ndm.base.jwt_handler.model.AllowedAccounts;
import com.postnord.ndm.base.jwt_handler.util.ResourceAccessParser;
import com.postnord.ndm.base.jwt_helper.JwtUtils;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.List;

import static com.postnord.ndm.base.jwt_handler.api.TestDataHelper.ACCOUNT_ID_1;
import static com.postnord.ndm.base.jwt_handler.api.TestDataHelper.ACCOUNT_ID_2;
import static com.postnord.ndm.base.jwt_handler.api.TestDataHelper.ACCOUNT_ID_3;
import static com.postnord.ndm.base.jwt_handler.api.TestDataHelper.ACCOUNT_ROLES_MAPPINGS_1;
import static com.postnord.ndm.base.jwt_handler.api.TestDataHelper.ACCOUNT_ROLES_MAPPINGS_2;
import static com.postnord.ndm.base.jwt_handler.api.TestDataHelper.ACCOUNT_ROLES_MAPPINGS_3;
import static io.restassured.RestAssured.given;

@QuarkusTest
@SuppressWarnings({"PMD.LinguisticNaming", "PMD.TooManyStaticImports"})
class AllowedAccountsResourceTest {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String ALLOWED_ACCOUNTS_PATH = "/allowed-accounts";

    @BeforeAll
    static void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void getAllowedAccountsWithRequiredPermissionsShouldSucceed() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final AllowedAccounts allowedAccounts = given()
                .when()
                .header(AUTHORIZATION_HEADER, generateTokenHeader(ACCOUNT_ROLES_MAPPINGS_1))
                .accept(ContentType.JSON)
                .get(ALLOWED_ACCOUNTS_PATH)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(AllowedAccounts.class);

        Assertions.assertNotNull(allowedAccounts);
        Assertions.assertNotNull(allowedAccounts.getAccounts());
        Assertions.assertEquals(2, allowedAccounts.getAccounts().size());
        Assertions.assertTrue(allowedAccounts.getAccounts().stream().map(AccountInfo::id).anyMatch(ACCOUNT_ID_1::equals));
        Assertions.assertTrue(allowedAccounts.getAccounts().stream().map(AccountInfo::id).anyMatch(ACCOUNT_ID_2::equals));
        Assertions.assertTrue(allowedAccounts.getAccounts().stream().noneMatch(AccountInfo::superUser));
        Assertions.assertFalse(allowedAccounts.containsSuperUser());
    }

    @Test
    void getAllowedAccountsWithMissingPermissionsShouldBeForbidden() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        given()
                .when()
                .header(AUTHORIZATION_HEADER, generateTokenHeader(ACCOUNT_ROLES_MAPPINGS_2))
                .accept(ContentType.JSON)
                .get(ALLOWED_ACCOUNTS_PATH)
                .then()
                .statusCode(403);
    }

    @Test
    void getAllowedAccountsAsSuperUserShouldSucceed() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final AllowedAccounts allowedAccounts = given()
                .when()
                .header(AUTHORIZATION_HEADER, generateTokenHeader(ACCOUNT_ROLES_MAPPINGS_3))
                .accept(ContentType.JSON)
                .get(ALLOWED_ACCOUNTS_PATH)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(AllowedAccounts.class);

        Assertions.assertNotNull(allowedAccounts);
        Assertions.assertNotNull(allowedAccounts.getAccounts());
        Assertions.assertEquals(1, allowedAccounts.getAccounts().size());
        Assertions.assertTrue(allowedAccounts.getAccounts().stream().map(AccountInfo::id).anyMatch(ACCOUNT_ID_1::equals));
        Assertions.assertTrue(allowedAccounts.getAccounts().stream().allMatch(AccountInfo::superUser));
        Assertions.assertTrue(allowedAccounts.containsSuperUser());
    }

    @Test
    void getAllowedAccountsFromPermitAllEndpointShouldSucceed() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final AllowedAccounts allowedAccounts = given()
                .when()
                .header(AUTHORIZATION_HEADER, generateTokenHeader(ACCOUNT_ROLES_MAPPINGS_1))
                .accept(ContentType.JSON)
                .get("/allowed-accounts/permit-all")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(AllowedAccounts.class);

        Assertions.assertNotNull(allowedAccounts);
        Assertions.assertNotNull(allowedAccounts.getAccounts());
        Assertions.assertEquals(3, allowedAccounts.getAccounts().size());
        Assertions.assertTrue(allowedAccounts.getAccounts().stream().map(AccountInfo::id).anyMatch(ACCOUNT_ID_1::equals));
        Assertions.assertTrue(allowedAccounts.getAccounts().stream().map(AccountInfo::id).anyMatch(ACCOUNT_ID_2::equals));
        Assertions.assertTrue(allowedAccounts.getAccounts().stream().map(AccountInfo::id).anyMatch(ACCOUNT_ID_3::equals));
        Assertions.assertTrue(allowedAccounts.getAccounts().stream().noneMatch(AccountInfo::superUser));
        Assertions.assertFalse(allowedAccounts.containsSuperUser());
    }

    @Test
    void getAllowedAccountsFromDenyAllEndpointShouldBeForbidden() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        given()
                .when()
                .header(AUTHORIZATION_HEADER, generateTokenHeader(ACCOUNT_ROLES_MAPPINGS_1))
                .accept(ContentType.JSON)
                .get("/allowed-accounts/deny-all")
                .then()
                .statusCode(403);
    }

    @Test
    void getAllowedAccountsWithNoResourceAccessClaimShouldBeForbidden() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        given()
                .when()
                .header(AUTHORIZATION_HEADER, generateTokenHeader(Collections.emptyList()))
                .accept(ContentType.JSON)
                .get(ALLOWED_ACCOUNTS_PATH)
                .then()
                .statusCode(403);
    }

    @Test
    void getAllowedAccountsWithNoResourceAccessClaimFromPermitAllEndpointShouldSucceed()
            throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final AllowedAccounts allowedAccounts = given()
                .when()
                .header(AUTHORIZATION_HEADER, generateTokenHeader(Collections.emptyList()))
                .accept(ContentType.JSON)
                .get("/allowed-accounts/permit-all")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(AllowedAccounts.class);

        Assertions.assertNotNull(allowedAccounts);
        Assertions.assertTrue(allowedAccounts.getAccounts().isEmpty());
        Assertions.assertFalse(allowedAccounts.containsSuperUser());
    }

    private static String generateTokenHeader(final List<AccountRolesMapping> accountRolesMappings) throws InvalidKeySpecException,
            NoSuchAlgorithmException, IOException {
        return "Bearer " + JwtUtils.generateTokenString(60, ResourceAccessParser.toClaim(accountRolesMappings));
    }
}
