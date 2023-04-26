package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmEuiccIdString;
import com.postnord.ndm.api.common.validation.constraints.NdmIccIdString;
import com.postnord.ndm.api.common.validation.constraints.NdmStringRegex;
import com.postnord.ndm.api.common.validation.constraints.NdmUuId4String;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/integration")
@SuppressWarnings({"PMD.AvoidDuplicateLiterals"})
public class ValidationResource {


    @GET
    @Path("/validation/euicc/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkEuicc(@PathParam("value")
                               @NdmEuiccIdString final String mustBeValidEuicc) {
        return Response.ok("Got EUICC of : " + mustBeValidEuicc).build();
    }


    @GET
    @Path("/validation/iccid/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkIccId(@PathParam("value")
                               @NdmIccIdString(nullAllowed = true) final String mustBeValidIccid) {
        return Response.ok("Got ICCID of : " + mustBeValidIccid).build();
    }


    @GET
    @Path("/validation/uuid/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkUuid(@PathParam("value") @NdmUuId4String final String mustBeUuid) {
        return Response.ok("Got valid UUID of : " + mustBeUuid).build();
    }

    @GET
    @Path("/validation/regex/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkRegex(@PathParam("value")
                               @NdmStringRegex(regex = "\\d[hm]", message = "must be 4m or 4h") final String mustMatchRegex) {
        return Response.ok("Got valid REGEX of : " + mustMatchRegex).build();
    }
}
