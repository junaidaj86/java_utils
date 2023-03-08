package com.postnord.ndm.base.health;

import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class LivenessService extends AbstractHealthService {
    @Override
    protected String getCheckName() {
        return "Liveness check";
    }
}
