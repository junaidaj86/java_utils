package com.postnord.ndm.credit.api;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

public class PostgreSqlTestResource implements QuarkusTestResourceLifecycleManager {

    private static final String POSTGRES_IMAGE = "postgres:13.4-alpine";
    private static PostgreSQLContainer<?> DB;

    @Override
    public Map<String, String> start() {
        getDb().start();
        return Map.of("quarkus.datasource.reactive.url", "postgresql://localhost:" + getDb().getMappedPort(5432) + "/my_db");
    }

    @Override
    public void stop() {
        getDb().stop();
    }

    @SuppressWarnings({"java:S2095", "java:S1452", "PMD.AvoidSynchronizedAtMethodLevel"})
    protected static synchronized PostgreSQLContainer<?> getDb() {
        if (DB == null) {
            DB = new PostgreSQLContainer<>(
                    DockerImageName.parse(POSTGRES_IMAGE)
                            .asCompatibleSubstituteFor("postgres"))
                    .withDatabaseName("my_db")
                    .withUsername("postgres")
                    .withPassword("password")
                    .waitingFor(Wait.forListeningPort());
        }
        return DB;
    }
}
