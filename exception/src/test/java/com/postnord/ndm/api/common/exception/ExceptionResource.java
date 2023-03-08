package com.postnord.ndm.api.common.exception;


import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.UNSUPPORTED_MEDIA_TYPE;


@RequestScoped
@Path("/integration")
public class ExceptionResource {

    private static final String ERROR_MESSAGE = "Don't panic just testing";

    @GET
    @Path("/exception/unhandled")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateUnhandledException() {
        throw new IllegalArgumentException(ERROR_MESSAGE);
    }

    @GET
    @Path("/exception/api")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateAPIException() {

        throw new APIException(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), UNSUPPORTED_MEDIA_TYPE.getStatusCode(), ERROR_MESSAGE);
    }

    @GET
    @Path("/exception/constraintviolation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateConstraintViolationException() {

        final Set<ConstraintViolation<ConstraintViolationGenerator>> violations =
                Validation.buildDefaultValidatorFactory().getValidator().validate(new ConstraintViolationGenerator());
        throw new ConstraintViolationException(violations);
    }

    @GET
    @Path("/exception/forbidden")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateForbiddenException() {

        throw new ForbiddenException(ERROR_MESSAGE);
    }

    @GET
    @Path("/exception/notacceptable")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateNotAcceptableException() {

        throw new NotAcceptableException(ERROR_MESSAGE);
    }

    @GET
    @Path("/exception/notallowed")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateNotAllowedException() {

        throw new NotAllowedException(ERROR_MESSAGE);

    }

    @GET
    @Path("/exception/notauthorized")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateNotAuthorizedException() {

        throw new NotAuthorizedException(ERROR_MESSAGE);
    }

    @GET
    @Path("/exception/notfound")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateNotFoundException() {

        throw new NotFoundException(ERROR_MESSAGE);
    }

    @GET
    @Path("/exception/notsupported")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateNotSupportedException() {

        throw new NotSupportedException(ERROR_MESSAGE);
    }

    @GET
    @Path("/exception/serviceunavailable")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateServiceUnavailableException() {

        throw new ServiceUnavailableException(ERROR_MESSAGE);
    }

    @GET
    @Path("/exception/webapplication")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateWebApplicationException() {

        throw new WebApplicationException(ERROR_MESSAGE);
    }

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.FinalFieldCouldBeStatic"})
    static class ConstraintViolationGenerator {
        @NotNull(message = ERROR_MESSAGE)
        private final Object field = null;
    }
}
