package com.postnord.ndm.api.common.problem;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;



@RequestScoped
@Path("/integration")
public class ProblemResource {

    public static final URI PROBLEM_TYPE = URI.create("http://ndm.postnord.com/problems/404");
    public static final URI PROBLEM_INSTANCE = URI.create("http://ndm.postnord.com/errors/404");
    public static final String PROBLEM_TITLE = "NOT_FOUND";
    public static final String PROBLEM_DETAIL = "ASSET NOT FOUND";
    public static final int PROBLEM_STATUS = 404;

    @GET
    @Path("/problem")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateProblem() {
        return ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(PROBLEM_TYPE)
                        .instance(PROBLEM_INSTANCE)
                        .title(PROBLEM_TITLE)
                        .status(PROBLEM_STATUS)
                        .detail(PROBLEM_DETAIL).build())
                .build();
    }
}
