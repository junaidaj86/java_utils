package com.postnord.ndm.credit.kafkaManager.handler;

import io.quarkus.jsonb.JsonbConfigCustomizer;

import javax.inject.Singleton;
import javax.json.bind.JsonbConfig;


@Singleton
public class JsonbConfigurationCustomizer implements JsonbConfigCustomizer {

    @Override
    public void customize(JsonbConfig config) {
        config.withNullValues(true);
    }
}
