package com.postnord.ndm.base.jwt_handler.util;

import com.postnord.ndm.base.jwt_handler.model.AccountRolesMapping;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import java.util.*;

@SuppressWarnings({"PMD.AvoidCatchingGenericException", "PMD.PreserveStackTrace"})
public final class ResourceAccessParser {
    static final String RESOURCE_ACCESS_CLAIM = "resource_access";
    static final String ROLES_TAG = "roles";
    static final String INVALID_CLAIM_MESSAGE = "Invalid '" + RESOURCE_ACCESS_CLAIM + "' claim";

    private ResourceAccessParser() {
    }

    public static List<AccountRolesMapping> parse(final JsonWebToken jsonWebToken) {
        if (jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM) == null) {
            return Collections.emptyList();
        }
        final Map<?, ?> rawClaim = parseClaim(jsonWebToken.getClaim(RESOURCE_ACCESS_CLAIM));
        final List<AccountRolesMapping> accountRolesMappings = new ArrayList<>(rawClaim.size());
        rawClaim.forEach((s, o) -> accountRolesMappings.add(new AccountRolesMapping(parseAccountId(s), parseAllowedRoles(o))));

        return Collections.unmodifiableList(accountRolesMappings);
    }

    public static Map<String, JsonObject> toClaim(final List<AccountRolesMapping> accountRolesMappings) {
        if (accountRolesMappings.isEmpty()) {
            return Collections.emptyMap();
        }
        final Map<String, JsonObject> claim = new HashMap<>(accountRolesMappings.size());
        accountRolesMappings.forEach(mapping -> {
            final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            mapping.getRoles().forEach(jsonArrayBuilder::add);
            claim.put(mapping.getAccountId(), Json.createObjectBuilder().add(ROLES_TAG, jsonArrayBuilder.build()).build());
        });

        return claim;
    }

    private static Map<?, ?> parseClaim(final Object claimObject) {
        if (!(claimObject instanceof Map)) {
            throw new IllegalArgumentException(INVALID_CLAIM_MESSAGE);
        }
        return (Map<?, ?>) claimObject;
    }

    private static String parseAccountId(final Object accountIdObject) {
        try {
            return UUID.fromString(accountIdObject.toString()).toString();
        } catch (Exception e) {
            throw new IllegalArgumentException(INVALID_CLAIM_MESSAGE);
        }
    }

    private static Set<String> parseAllowedRoles(final Object rolesObject) {
        if (!(rolesObject instanceof JsonObject)) {
            throw new IllegalArgumentException(INVALID_CLAIM_MESSAGE);
        }
        return parseAllowedRoles((JsonObject) rolesObject);
    }

    @SuppressWarnings("PMD.AvoidRethrowingException")
    private static Set<String> parseAllowedRoles(final JsonObject rolesObject) {
        try {
            final JsonArray jsonArray = rolesObject.getJsonArray(ROLES_TAG);
            final Set<String> roles = new HashSet<>(jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                final String role = jsonArray.getString(i).trim();
                if (role.isEmpty()) {
                    throw new IllegalArgumentException(INVALID_CLAIM_MESSAGE);
                }
                roles.add(role);
            }
            return Collections.unmodifiableSet(roles);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(INVALID_CLAIM_MESSAGE);
        }
    }
}
