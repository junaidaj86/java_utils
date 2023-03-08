package com.postnord.ndm.api.common.validation.validator;


import com.postnord.ndm.api.common.exception.APIException;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class ModelValidatorBoundary<T> extends ModelValidator<T> {

    @Override
    protected void throwError(final String message) {

        throw new APIException("Bad Request", BAD_REQUEST.getStatusCode(), message);
    }
}
