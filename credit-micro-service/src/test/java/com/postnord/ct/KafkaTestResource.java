package com.postnord.ct;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class KafkaTestResource implements QuarkusTestResourceLifecycleManager {

    private static final DockerComposeContainer<?> KAFKA =
            new DockerComposeContainer<>(new File("src/test/resources/local-kafka.yaml"))
                    .withExposedService("zookeeper", 1, 2181, Wait.forListeningPort())
                    .withExposedService("kafka", 1, 9092, Wait.forListeningPort());

    @Override
    public Map<String, String> start() {
        KAFKA.start();
        return Collections.emptyMap();
    }

    @Override
    public void stop() {
        KAFKA.stop();
    }
}
