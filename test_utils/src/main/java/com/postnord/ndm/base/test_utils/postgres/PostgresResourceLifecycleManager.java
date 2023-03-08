package com.postnord.ndm.base.test_utils.postgres;

import org.eclipse.microprofile.config.ConfigProvider;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class PostgresResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {

    private static final boolean ENV_HAS_DOCKER =
            ConfigProvider.getConfig().getOptionalValue("env.has.docker", Boolean.class).orElse(false);
    private static final String POSTGRES_IMAGE =
            ConfigProvider.getConfig().getOptionalValue("com.postnord.ndm.base.test_utils.postgres.image", String.class)
                    .orElse("serodocker.sero.gic.postnord.com/proj-iota/3pp/postgres:12.4-alpine");

    private static PostgreSQLContainer<?> db;

    @Override
    public Map<String, String> start() {
        if (!ENV_HAS_DOCKER) {
            return Map.of();
        }
        getDb().start();
        return Map.of("quarkus.datasource.reactive.url", "postgresql://localhost:" + getDb().getMappedPort(5432) + "/test");
    }

    @Override
    public void stop() {
        if (ENV_HAS_DOCKER) {
            getDb().stop();
        }
    }

    @SuppressWarnings({"java:S2095", "java:S1452", "PMD.AvoidSynchronizedAtMethodLevel"})
    protected static synchronized PostgreSQLContainer<?> getDb() {
        if (db == null) {
            db = new PostgreSQLContainer<>(
                    DockerImageName.parse(POSTGRES_IMAGE).asCompatibleSubstituteFor("postgres"))
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test")
                    .waitingFor(
                            Wait.forLogMessage(".*database system is ready to accept connections.*", 1)
                    );
        }
        return db;
    }
}
