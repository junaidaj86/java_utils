package com.postnord.ndm.base.rest_utils.client.reactive.util;

public final class ConstantsHelper {

    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String GRANT_TYPE = "grant_type";
    public static final String EXPIRES_IN = "expires_in";

    public static final String KEYCLOAK_IS_NOT_WORKING_AT_THIS_MOMENT = "Keycloak is not working at this moment";

    public static final String BASE_URI = "/auth/realms/";
    public static final String PROTOCOL_OPENID_CONNECT = "/protocol/openid-connect";

    public static final int REST_MAX_RETRIES = 3;
    public static final long REST_RETRY_INTERVAL = 50L;

    private ConstantsHelper() {
    }
}
