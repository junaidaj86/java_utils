package com.postnord.ndm.base.health;


import com.postnord.ndm.base.logger.NdmLogger;
import com.postnord.ndm.base.logger.model.LogRecord;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import jakarta.enterprise.event.Observes;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("PMD.GuardLogStatement")
abstract class AbstractHealthService implements HealthCheck {
    private final AtomicBoolean applicationUp = new AtomicBoolean(false);

    @ConfigProperty(name = "com.postnord.ndm.base.health.log-category", defaultValue = "UNSET")
    String logCategory;

    @Override
    public HealthCheckResponse call() {
        if (applicationUp.get()) {
            return HealthCheckResponse.up(getCheckName());
        }
        return HealthCheckResponse.down(getCheckName());
    }

    void onStart(@Observes final StartupEvent ev) {
        NdmLogger.info(LogRecord.builder().message("Application is up").category(logCategory).build());
        applicationUp.set(true);
    }

    void onStop(@Observes final ShutdownEvent ev) {
        applicationUp.set(false);
        NdmLogger.info(LogRecord.builder().message("Application is down").category(logCategory).build());
    }

    protected abstract String getCheckName();
}
