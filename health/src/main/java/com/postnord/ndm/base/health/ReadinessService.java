package com.postnord.ndm.base.health;

import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class ReadinessService extends AbstractHealthService {
    @Override
    protected String getCheckName() {
        return "Readiness check";
    }
}
