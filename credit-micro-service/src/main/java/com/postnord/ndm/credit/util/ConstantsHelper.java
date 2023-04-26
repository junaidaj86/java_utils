package com.postnord.ndm.credit.util;

import lombok.experimental.UtilityClass;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import java.nio.charset.StandardCharsets;

@UtilityClass
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class ConstantsHelper {

    public static final Jsonb JSON_B = JsonbBuilder.create(new JsonbConfig()
            .withNullValues(false)
            .withEncoding(StandardCharsets.UTF_8.name()));
}
