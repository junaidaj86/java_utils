package com.postnord.ndm.credit.util;

import lombok.experimental.UtilityClass;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.nio.charset.StandardCharsets;

@UtilityClass
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class ConstantsHelper {

    public static final Jsonb JSON_B = JsonbBuilder.create(new JsonbConfig()
            .withNullValues(false)
            .withEncoding(StandardCharsets.UTF_8.name()));
}