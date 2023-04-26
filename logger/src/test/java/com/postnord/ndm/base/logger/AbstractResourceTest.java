package com.postnord.ndm.base.logger;

import com.postnord.ndm.base.jwt_helper.JwtUtils;
import lombok.SneakyThrows;

import jakarta.json.Json;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
abstract class AbstractResourceTest {
    static final String X_FLOW_ID = "X-Flow-ID";

    @SneakyThrows
    static String generateTokenHeader() {
        return "Bearer " + JwtUtils.generateTokenString(
                1800,
                Map.of(UUID.randomUUID().toString(), Json.createObjectBuilder().add("roles", Json.createArrayBuilder().add("test").build()).build())
        );
    }
}
