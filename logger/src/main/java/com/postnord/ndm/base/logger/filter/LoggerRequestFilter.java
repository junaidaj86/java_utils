package com.postnord.ndm.base.logger.filter;

import com.postnord.ndm.base.logger.util.LoggerContext;

import java.security.Principal;

import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.USER)
public class LoggerRequestFilter implements ContainerRequestFilter {

    @Inject
    ResourceInfo resourceInfo;

    @Inject
    Instance<LoggerContext> tracerContextInstance;

    private static final String X_FLOW_ID = "X-Flow-ID";

    @Override
    public void filter(final ContainerRequestContext requestContext) {
        final var tracerContext = tracerContextInstance.get();
        final Principal principal = requestContext.getSecurityContext().getUserPrincipal();
        if (principal != null) {
            tracerContext.setUpn(principal.getName());
        }
        tracerContext.setFlowId(extractFlowId(requestContext));
        tracerContext.setClassId(extractClassId());
        tracerContext.setMethodId(extractMethodId());
    }

    private String extractMethodId() {
        return resourceInfo.getResourceMethod().getName();
    }

    private String extractClassId() {
        return resourceInfo.getResourceClass().getName();
    }

    private String extractFlowId(final ContainerRequestContext requestContext) {
        return requestContext.getHeaderString(X_FLOW_ID);
    }
}
