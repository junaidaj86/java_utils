package com.postnord.ndm.base.jpa_utils.utils;

import com.postnord.ndm.base.test_utils.postgres.PostgresResourceLifecycleManager;

import java.util.Map;

public class PostgresTestResource extends PostgresResourceLifecycleManager {
    @Override
    public Map<String, String> start() {
        final Map<String, String> overrides = super.start();
        if (overrides.isEmpty()) {
            return overrides;
        }
        return Map.of("quarkus.datasource.jdbc.url", "jdbc:postgresql://localhost:" +
                PostgresResourceLifecycleManager.getDb().getMappedPort(5432) + "/test");
    }
}
