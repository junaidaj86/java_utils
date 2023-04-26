package com.postnord.ndm.base.jwt_handler.util;

import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import static com.postnord.ndm.base.jwt_handler.util.ResourceAccessParser.RESOURCE_ACCESS_CLAIM;
import static com.postnord.ndm.base.jwt_handler.util.ResourceAccessParser.ROLES_TAG;

class ResourceAccessParserTest {

    @Test
    void parseValidResourceAccessClaimWithoutRolesShouldSucceed() {
        final JsonWebToken jsonWebToken = Mockito.mock(JsonWebToken.class);
        Mockito
                .when(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM))
                .thenReturn(createResourceAccessClaim(UUID.randomUUID().toString(), ROLES_TAG, Json.createArrayBuilder().build()));

        final List<AccountRolesMapping> accountRolesMappings = ResourceAccessParser.parse(jsonWebToken);
        Assertions.assertNotNull(accountRolesMappings);
        Assertions.assertEquals(1, accountRolesMappings.size());
        Assertions.assertNotNull(accountRolesMappings.get(0));
        Assertions.assertNotNull(accountRolesMappings.get(0).getAccountId());
        Assertions.assertNotNull(accountRolesMappings.get(0).getRoles());
        Assertions.assertTrue(accountRolesMappings.get(0).getRoles().isEmpty());
    }

    @Test
    void parseValidResourceAccessClaimWithRolesShouldSucceed() {
        final JsonWebToken jsonWebToken = Mockito.mock(JsonWebToken.class);
        Mockito
                .when(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM))
                .thenReturn(createResourceAccessClaim(UUID.randomUUID().toString(), ROLES_TAG, Json.createArrayBuilder().add("test").build()));

        final List<AccountRolesMapping> accountRolesMappings = ResourceAccessParser.parse(jsonWebToken);
        Assertions.assertNotNull(accountRolesMappings);
        Assertions.assertEquals(1, accountRolesMappings.size());
        Assertions.assertNotNull(accountRolesMappings.get(0));
        Assertions.assertNotNull(accountRolesMappings.get(0).getAccountId());
        Assertions.assertNotNull(accountRolesMappings.get(0).getRoles());
        Assertions.assertEquals(1, accountRolesMappings.get(0).getRoles().size());
        Assertions.assertEquals("test", accountRolesMappings.get(0).getRoles().iterator().next());
    }

    @Test
    void parseResourceAccessClaimWithNullRolesShouldThrowIllegalArgumentException() {
        final JsonWebToken jsonWebToken = Mockito.mock(JsonWebToken.class);
        Mockito
                .when(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM))
                .thenReturn(createResourceAccessClaim(UUID.randomUUID().toString(), ROLES_TAG, Json.createArrayBuilder().addNull().build()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> ResourceAccessParser.parse(jsonWebToken));
    }

    @Test
    void parseResourceAccessClaimWithBlankRolesShouldThrowIllegalArgumentException() {
        final JsonWebToken jsonWebToken = Mockito.mock(JsonWebToken.class);
        Mockito
                .when(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM))
                .thenReturn(createResourceAccessClaim(UUID.randomUUID().toString(), ROLES_TAG, Json.createArrayBuilder().add(" ").build()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> ResourceAccessParser.parse(jsonWebToken));
    }

    @Test
    void parseResourceAccessClaimWithNonStringRolesShouldThrowIllegalArgumentException() {
        final JsonWebToken jsonWebToken = Mockito.mock(JsonWebToken.class);
        Mockito
                .when(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM))
                .thenReturn(createResourceAccessClaim(UUID.randomUUID().toString(), ROLES_TAG, Json.createArrayBuilder().add(15).build()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> ResourceAccessParser.parse(jsonWebToken));
    }

    @Test
    void parseResourceAccessClaimWithNonArrayRolesShouldThrowIllegalArgumentException() {
        final JsonWebToken jsonWebToken = Mockito.mock(JsonWebToken.class);
        Mockito
                .when(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM))
                .thenReturn(createResourceAccessClaim(UUID.randomUUID().toString(), ROLES_TAG, Json.createValue("test")));

        Assertions.assertThrows(IllegalArgumentException.class, () -> ResourceAccessParser.parse(jsonWebToken));
    }

    @Test
    void parseResourceAccessClaimWithNoRolesTagShouldThrowIllegalArgumentException() {
        final JsonWebToken jsonWebToken = Mockito.mock(JsonWebToken.class);
        Mockito
                .when(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM))
                .thenReturn(createResourceAccessClaim(UUID.randomUUID().toString(), "invalid", Json.createArrayBuilder().build()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> ResourceAccessParser.parse(jsonWebToken));
    }

    @Test
    void parseResourceAccessClaimWithInvalidAccountIdShouldThrowIllegalArgumentException() {
        final JsonWebToken jsonWebToken = Mockito.mock(JsonWebToken.class);
        Mockito
                .when(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM))
                .thenReturn(createResourceAccessClaim("invalid", ROLES_TAG, Json.createArrayBuilder().build()));

        Assertions.assertThrows(IllegalArgumentException.class, () -> ResourceAccessParser.parse(jsonWebToken));
    }

    @Test
    void parseResourceAccessClaimOfInvalidTypeShouldThrowIllegalArgumentException() {
        final JsonWebToken jsonWebToken = Mockito.mock(JsonWebToken.class);
        Mockito
                .when(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM))
                .thenReturn("invalid");

        Assertions.assertThrows(IllegalArgumentException.class, () -> ResourceAccessParser.parse(jsonWebToken));
    }

    @Test
    void parseNoResourceAccessClaimShouldSucceed() {
        final JsonWebToken jsonWebToken = Mockito.mock(JsonWebToken.class);
        Mockito
                .when(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM))
                .thenReturn(null);

        final List<AccountRolesMapping> accountRolesMappings = ResourceAccessParser.parse(jsonWebToken);
        Assertions.assertTrue(accountRolesMappings.isEmpty());
    }

    private static Map<Object, Object> createResourceAccessClaim(final Object key, final String fieldName, final JsonValue fieldValue) {
        return createResourceAccessClaim(key, Json.createObjectBuilder().add(fieldName, fieldValue).build());
    }

    private static Map<Object, Object> createResourceAccessClaim(final Object key, final JsonObject value) {
        return createResourceAccessClaim(key, (Object) value);
    }

    private static Map<Object, Object> createResourceAccessClaim(final Object key, final Object value) {
        return Map.of(key, value);
    }
}
