package com.postnord.ndm.base.jwt_rest_mapper;

import com.postnord.ndm.base.jwt_handler.model.AccountAccessMapping;
import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;
import com.postnord.ndm.base.jwt_handler.util.ResourceAccessParser;
import com.postnord.ndm.base.jwt_helper.JwtUtils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.smallrye.mutiny.Uni;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@SuppressWarnings({"PMD.LinguisticNaming", "PMD.TooManyStaticImports", "PMD.ExcessiveImports"})
class UpnResourceTest {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @InjectSpy
    RestRoleMapper roleMapperSpy;

    @BeforeAll
    static void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    void setUp() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        Mockito.when(roleMapperSpy.getToken()).thenReturn(Uni.createFrom().item(generateTokenString(TestDataHelper.IAM_ACCOUNT_ROLES_MAPPINGS)));
        Mockito.when(roleMapperSpy.resolvePermissions(TestDataHelper.UPN_ACCOUNT_ROLES_MAPPINGS)).thenCallRealMethod();
        // Bypass RestRoleMapper for IAM endpoints to avoid cyclic loop
        Mockito.when(roleMapperSpy.resolvePermissions(TestDataHelper.IAM_ACCOUNT_ROLES_MAPPINGS))
                .thenReturn(Uni.createFrom().item(
                        TestDataHelper.IAM_ACCOUNT_ROLES_MAPPINGS.stream()
                                .map(mapping -> new AccountAccessMapping(
                                                mapping.getAccountId(),
                                                Collections.unmodifiableSet(mapping.getRoles()),
                                                mapping.getRoles().stream()
                                                        .map(TestDataHelper.IAM_ROLE_MAPPINGS::get).flatMap(Collection::stream)
                                                        .collect(Collectors.toUnmodifiableSet())
                                        )
                                )
                                .collect(Collectors.toUnmodifiableList())
                        )
                );
    }

    @Test
    void getUsernameWithRequiredPermissionShouldSucceed() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        given()
                .when()
                .header(AUTHORIZATION_HEADER, generateTokenHeader(TestDataHelper.UPN_ACCOUNT_ROLES_MAPPINGS))
                .accept(ContentType.TEXT)
                .get("/upn")
                .then()
                .statusCode(200)
                .body(is("jesse.james"));
    }

    @Test
    void getUsernameWithMissingPermissionShouldBeForbidden() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        given()
                .when()
                .header(AUTHORIZATION_HEADER, generateTokenHeader(List.of(new AccountRolesMapping(TestDataHelper.ACCOUNT_ID.toString(), Set.of()))))
                .accept(ContentType.TEXT)
                .get("/upn")
                .then()
                .statusCode(403);
    }

    private static String generateTokenHeader(final List<AccountRolesMapping> accountRolesMappings) throws InvalidKeySpecException,
            NoSuchAlgorithmException, IOException {
        return "Bearer " + generateTokenString(accountRolesMappings);
    }

    private static String generateTokenString(final List<AccountRolesMapping> accountRolesMappings) throws InvalidKeySpecException,
            NoSuchAlgorithmException, IOException {
        return JwtUtils.generateTokenString(3600, ResourceAccessParser.toClaim(accountRolesMappings));
    }
}
