package com.postnord.ndm.api.common.validation.validator;


import com.postnord.ndm.api.common.exception.APIException;

import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

public class ModelValidatorInternal<T> extends ModelValidator<T> {

    @Override
    protected void throwError(final String message) {

        throw new APIException("Internal Server Error", INTERNAL_SERVER_ERROR.getStatusCode(), message);
    }
}
