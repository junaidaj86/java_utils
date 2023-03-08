package com.postnord.ndm.base.rsql_parser.utils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public enum ArgumentType {
    STRING(String.class),
    BOOLEAN(Boolean.class, boolean.class),
    INTEGER(Integer.class, int.class),
    LONG(Long.class, long.class),
    FLOAT(Float.class, float.class),
    DOUBLE(Double.class, double.class),
    BIG_DECIMAL(BigDecimal.class),
    ENUM(Enum.class),
    LOCAL_DATE(LocalDate.class),
    INSTANT(Instant.class),
    UUID(java.util.UUID.class);

    private final Set<Type> types;

    ArgumentType(final Type... types) {
        this.types = Set.of(types);
    }

    static ArgumentType fromJavaType(final Type type) {
        for (final ArgumentType argumentType : ArgumentType.values()) {
            if (argumentType.types.stream().anyMatch(c -> c.equals(type) || ((Class<?>) c).isAssignableFrom((Class<?>) type))) {
                return argumentType;
            }
        }
        throw new ParserException("No argument type registered for: " + type);
    }
}
