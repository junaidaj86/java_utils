package com.postnord.ndm.base.rsql_parser.utils;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

class ParsingUtilsTest {
    private static final String INVALID = "invalid";

    @Test
    void parseValidStringShouldSucceed() {
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testString1", RsqlOperator.EQUAL, Collections.singletonList("test"));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals("test", arguments.get(0));
    }

    @Test
    void parseValidBooleanShouldSucceed() {
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testBoolean", RsqlOperator.EQUAL, Collections.singletonList("true"));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals(true, arguments.get(0));
    }

    @Test
    void parseValidIntegerShouldSucceed() {
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testInteger1", RsqlOperator.EQUAL, Collections.singletonList("77"));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals(77, arguments.get(0));
    }

    @Test
    void parseInvalidIntegerShouldThrowParsingException() {
        final List<String> arguments = Collections.singletonList(INVALID);
        Assertions.assertThrows(ParserException.class,
                () -> ParsingUtils.parseArguments(TestEntity.class, "testInteger1", RsqlOperator.EQUAL, arguments));
    }

    @Test
    void parseValidLongShouldSucceed() {
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testLong", RsqlOperator.EQUAL, Collections.singletonList(String.valueOf(Long.MAX_VALUE)));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals(Long.MAX_VALUE, arguments.get(0));
    }

    @Test
    void parseInvalidLongShouldThrowParsingException() {
        final List<String> arguments = Collections.singletonList(INVALID);
        Assertions.assertThrows(ParserException.class,
                () -> ParsingUtils.parseArguments(TestEntity.class, "testLong", RsqlOperator.EQUAL, arguments));
    }

    @Test
    void parseValidFloatShouldSucceed() {
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testFloat", RsqlOperator.EQUAL, Collections.singletonList("7.7"));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals(7.7F, arguments.get(0));
    }

    @Test
    void parseInvalidFloatShouldThrowParsingException() {
        final List<String> arguments = Collections.singletonList(INVALID);
        Assertions.assertThrows(ParserException.class,
                () -> ParsingUtils.parseArguments(TestEntity.class, "testFloat", RsqlOperator.EQUAL, arguments));
    }

    @Test
    void parseValidDoubleShouldSucceed() {
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testDouble", RsqlOperator.EQUAL, Collections.singletonList(String.valueOf(Double.MAX_VALUE)));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals(Double.MAX_VALUE, arguments.get(0));
    }

    @Test
    void parseInvalidDoubleShouldThrowParsingException() {
        final List<String> arguments = Collections.singletonList(INVALID);
        Assertions.assertThrows(ParserException.class,
                () -> ParsingUtils.parseArguments(TestEntity.class, "testDouble", RsqlOperator.EQUAL, arguments));
    }

    @Test
    void parseValidBigDecimalShouldSucceed() {
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testBigDecimal", RsqlOperator.EQUAL,
                        Collections.singletonList(BigDecimal.valueOf(7).toString()));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals(BigDecimal.valueOf(7), arguments.get(0));
    }

    @Test
    void parseInvalidBigDecimalShouldThrowParsingException() {
        final List<String> arguments = Collections.singletonList(INVALID);
        Assertions.assertThrows(ParserException.class,
                () -> ParsingUtils.parseArguments(TestEntity.class, "testBigDecimal", RsqlOperator.EQUAL, arguments));
    }

    @Test
    void parseValidLocalDateShouldSucceed() {
        final LocalDate now = LocalDate.now();
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testLocalDate", RsqlOperator.EQUAL,
                        Collections.singletonList(now.format(DateTimeFormatter.ISO_DATE)));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals(now, arguments.get(0));
    }

    @Test
    void parseInvalidLocalDateShouldThrowParsingException() {
        final List<String> arguments = Collections.singletonList(INVALID);
        Assertions.assertThrows(ParserException.class,
                () -> ParsingUtils.parseArguments(TestEntity.class, "testLocalDate", RsqlOperator.EQUAL, arguments));
    }

    @Test
    void parseValidInstantShouldSucceed() {
        final Instant now = InstantHelper.getInstantAndStripNano();
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testInstant", RsqlOperator.EQUAL,
                        Collections.singletonList(InstantHelper.getStringFromInstant(now)));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals(now, arguments.get(0));
    }

    @Test
    void parseInvalidInstantShouldThrowParsingException() {
        final List<String> arguments = Collections.singletonList(INVALID);
        Assertions.assertThrows(ParserException.class,
                () -> ParsingUtils.parseArguments(TestEntity.class, "testInstant", RsqlOperator.EQUAL, arguments));
    }

    @Test
    void parseValidUUIDShouldSucceed() {
        final UUID uuid = UUID.randomUUID();
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testUUID", RsqlOperator.EQUAL, Collections.singletonList(uuid.toString()));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals(uuid, arguments.get(0));
    }

    @Test
    void parseInvalidUUIDShouldThrowParsingException() {
        final List<String> arguments = Collections.singletonList(INVALID);
        Assertions.assertThrows(ParserException.class,
                () -> ParsingUtils.parseArguments(TestEntity.class, "testUUID", RsqlOperator.EQUAL, arguments));
    }

    @Test
    void parseValidEnumShouldSucceed() {
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testEnum1", RsqlOperator.EQUAL, Collections.singletonList(TestEnum.ONE.toString()));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(1, arguments.size());
        Assertions.assertEquals(TestEnum.ONE, arguments.get(0));
    }

    @Test
    void parseWildcardEnumShouldSucceed() {
        final RsqlNode node =
                ParsingUtils.parseArguments(TestEntity.class, "testEnum1", RsqlOperator.EQUAL, Collections.singletonList("*"));
        final List<?> arguments = node.getArguments();

        Assertions.assertNotNull(arguments);
        Assertions.assertEquals(TestEnum.values().length, arguments.size());
        Assertions.assertTrue(arguments.contains(TestEnum.ONE));
        Assertions.assertTrue(arguments.contains(TestEnum.TWO));
        Assertions.assertTrue(arguments.contains(TestEnum.THREE));
    }

    @Test
    void parseInvalidFieldShouldThrowParsingException() {
        final List<String> arguments = Collections.singletonList("valid");
        Assertions.assertThrows(ParserException.class, () -> ParsingUtils.parseArguments(TestEntity.class, INVALID, RsqlOperator.EQUAL, arguments));
    }
}
