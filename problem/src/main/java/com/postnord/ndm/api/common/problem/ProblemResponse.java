package com.postnord.ndm.api.common.problem;


import jakarta.ws.rs.core.Response;
import lombok.Builder;
import lombok.NonNull;

@Builder(buildMethodName = "buildInternal")
public class ProblemResponse {

    private static final String JSON_PROBLEM = "application/problem+json";

    @NonNull
    private Problem problem;

    public static class ProblemResponseBuilder {

        public Response build() {
            return Response
                    .status(problem.getStatus())
                    .entity(problem)
                    .type(JSON_PROBLEM)
                    .build();
        }
    }
}
