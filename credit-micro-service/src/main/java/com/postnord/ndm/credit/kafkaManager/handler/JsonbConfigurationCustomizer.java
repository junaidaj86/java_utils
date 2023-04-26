package com.postnord.ndm.credit.kafkaManager.handler;

import io.quarkus.jsonb.JsonbConfigCustomizer;

import jakarta.inject.Singleton;
import jakarta.json.bind.JsonbConfig;


@Singleton
public class JsonbConfigurationCustomizer implements JsonbConfigCustomizer {

    @Override
    public void customize(JsonbConfig config) {
        config.withNullValues(true);
    }
}
