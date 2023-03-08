package com.postnord.ndm.base.rsql_parser.utils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;

@Builder
public class TestEntity {
    private final String testString1;
    private final String testString2;
    private final Boolean testBoolean;
    private final Integer testInteger1;
    private final Integer testInteger2;
    private final Long testLong;
    private final Float testFloat;
    private final Double testDouble;
    private final BigDecimal testBigDecimal;
    private final LocalDate testLocalDate;
    private final Instant testInstant;
    private final UUID testUUID;
    private final TestEnum testEnum1;
    private final TestEnum testEnum2;
}
