package com.postnord.ndm.base.rest_utils.client.reactive.exception;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public final class APIExceptionHelper {
    private static final String KEY_WITH_ID = "Key with ID ";

    public static void throwNotFoundException(final String id) {
        throw createNotFoundException(id);
    }

    public static APIException createNotFoundException(final String detail) {
        return new APIException(NOT_FOUND.getReasonPhrase(), NOT_FOUND.getStatusCode(), detail);
    }

    public static APIException createRemoteCallException(final String detail) {
        return new APIException(INTERNAL_SERVER_ERROR.getReasonPhrase(), INTERNAL_SERVER_ERROR.getStatusCode(), detail);
    }

    public static void throwForbiddenException(final String id) {
        throw createForbiddenException(id);
    }

    public static APIException createForbiddenException(final String id) {
        return new APIException(FORBIDDEN.getReasonPhrase(), FORBIDDEN.getStatusCode(), KEY_WITH_ID + id + " not authorized");
    }

    private APIExceptionHelper() {
    }
}
