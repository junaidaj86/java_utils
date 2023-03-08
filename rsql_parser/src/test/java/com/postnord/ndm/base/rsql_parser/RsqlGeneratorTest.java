package com.postnord.ndm.base.rsql_parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.CONTAINS_OPERATOR;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.ENUM_FIELD_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.ENUM_FIELD_2;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.ENUM_VALUE_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.ENUM_VALUE_2;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.EXCLUDES_OPERATOR;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INSTANT_FIELD;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INSTANT_VALUE;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INT_FIELD_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INT_FIELD_2;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INT_VALUE_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INT_VALUE_2;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.ISNULL_OPERATOR;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.STRING_FIELD_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.STRING_FIELD_2;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.STRING_VALUE_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.STRING_VALUE_2;


@SuppressWarnings("PMD.TooManyStaticImports")
class RsqlGeneratorTest {

    @Test
    void generateEqualConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().eq(STRING_FIELD_1, STRING_VALUE_1).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + "==" + STRING_VALUE_1, rsql);
    }

    @Test
    void generateEqualAndConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().eq(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + "==" + STRING_VALUE_1 + ';' + STRING_FIELD_1 + "==" + STRING_VALUE_2, rsql);
    }

    @Test
    void generateEqualOrConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().eq(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        Assertions.assertEquals(INT_FIELD_1 + "==" + INT_VALUE_1 + ',' + INT_FIELD_1 + "==" + INT_VALUE_2, rsql);
    }

    @Test
    void generateNotEqualConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().ne(ENUM_FIELD_1, ENUM_VALUE_1).end().execute();
        Assertions.assertEquals(ENUM_FIELD_1 + "!=" + ENUM_VALUE_1, rsql);
    }

    @Test
    void generateNotEqualAndConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().ne(ENUM_FIELD_1, Arrays.asList(ENUM_VALUE_1, ENUM_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        Assertions.assertEquals(ENUM_FIELD_1 + "!=" + ENUM_VALUE_1 + ';' + ENUM_FIELD_1 + "!=" + ENUM_VALUE_2, rsql);
    }

    @Test
    void generateNotEqualOrConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().ne(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + "!=" + STRING_VALUE_1 + ',' + STRING_FIELD_1 + "!=" + STRING_VALUE_2, rsql);
    }

    @Test
    void generateGreaterThanConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().gt(INSTANT_FIELD, INSTANT_VALUE).end().execute();
        Assertions.assertEquals(INSTANT_FIELD + '>' + INSTANT_VALUE, rsql);
    }

    @Test
    void generateGreaterThanAndConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().gt(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        Assertions.assertEquals(INT_FIELD_1 + '>' + INT_VALUE_1 + ';' + INT_FIELD_1 + '>' + INT_VALUE_2, rsql);
    }

    @Test
    void generateGreaterThanOrConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().gt(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + '>' + STRING_VALUE_1 + ',' + STRING_FIELD_1 + '>' + STRING_VALUE_2, rsql);
    }

    @Test
    void generateGreaterThanOrEqualConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().gte(INSTANT_FIELD, INSTANT_VALUE).end().execute();
        Assertions.assertEquals(INSTANT_FIELD + ">=" + INSTANT_VALUE, rsql);
    }

    @Test
    void generateGreaterThanOrEqualAndConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().gte(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        Assertions.assertEquals(INT_FIELD_1 + ">=" + INT_VALUE_1 + ';' + INT_FIELD_1 + ">=" + INT_VALUE_2, rsql);
    }

    @Test
    void generateGreaterThanOrEqualOrConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().gte(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        Assertions.assertEquals(INT_FIELD_1 + ">=" + INT_VALUE_1 + ',' + INT_FIELD_1 + ">=" + INT_VALUE_2, rsql);
    }

    @Test
    void generateLessThanConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().lt(INSTANT_FIELD, INSTANT_VALUE).end().execute();
        Assertions.assertEquals(INSTANT_FIELD + '<' + INSTANT_VALUE, rsql);
    }

    @Test
    void generateLessThanAndConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().lt(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        Assertions.assertEquals(INT_FIELD_1 + '<' + INT_VALUE_1 + ';' + INT_FIELD_1 + '<' + INT_VALUE_2, rsql);
    }

    @Test
    void generateLessThanOrConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().lt(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + '<' + STRING_VALUE_1 + ',' + STRING_FIELD_1 + '<' + STRING_VALUE_2, rsql);
    }

    @Test
    void generateLessThanOrEqualConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().lte(INSTANT_FIELD, INSTANT_VALUE).end().execute();
        Assertions.assertEquals(INSTANT_FIELD + "<=" + INSTANT_VALUE, rsql);
    }

    @Test
    void generateLessThanOrEqualAndConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().lte(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        Assertions.assertEquals(INT_FIELD_1 + "<=" + INT_VALUE_1 + ';' + INT_FIELD_1 + "<=" + INT_VALUE_2, rsql);
    }

    @Test
    void generateLessThanOrEqualOrConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().lte(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + "<=" + STRING_VALUE_1 + ',' + STRING_FIELD_1 + "<=" + STRING_VALUE_2, rsql);
    }

    @Test
    void generateContainsConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().contains(STRING_FIELD_1, STRING_VALUE_1).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + CONTAINS_OPERATOR + STRING_VALUE_1, rsql);
    }

    @Test
    void generateContainsAndConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().contains(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + CONTAINS_OPERATOR + STRING_VALUE_1 + ';' + STRING_FIELD_1 + CONTAINS_OPERATOR + STRING_VALUE_2, rsql);
    }

    @Test
    void generateContainsOrConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().contains(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        Assertions.assertEquals(INT_FIELD_1 + CONTAINS_OPERATOR + INT_VALUE_1 + ',' + INT_FIELD_1 + CONTAINS_OPERATOR + INT_VALUE_2, rsql);
    }

    @Test
    void generateExcludesConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().excludes(STRING_FIELD_1, STRING_VALUE_1).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + EXCLUDES_OPERATOR + STRING_VALUE_1, rsql);
    }

    @Test
    void generateExcludesAndConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().excludes(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + EXCLUDES_OPERATOR + STRING_VALUE_1 + ';' + STRING_FIELD_1 + EXCLUDES_OPERATOR + STRING_VALUE_2, rsql);
    }

    @Test
    void generateExcludesOrConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().excludes(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        Assertions.assertEquals(INT_FIELD_1 + EXCLUDES_OPERATOR + INT_VALUE_1 + ',' + INT_FIELD_1 + EXCLUDES_OPERATOR + INT_VALUE_2, rsql);
    }

    @Test
    void generateIsNullConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().isNull(STRING_FIELD_1, true).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + ISNULL_OPERATOR + true, rsql);
    }

    @Test
    void generateIsNullAndConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().isNull(STRING_FIELD_1, Arrays.asList(true, false), RsqlGenerator.CombineOperation.AND).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + ISNULL_OPERATOR + true + ';' + STRING_FIELD_1 + ISNULL_OPERATOR + false, rsql);
    }

    @Test
    void generateIsNullOrConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().isNull(INT_FIELD_1, Arrays.asList(false, true), RsqlGenerator.CombineOperation.OR).end().execute();
        Assertions.assertEquals(INT_FIELD_1 + ISNULL_OPERATOR + false + ',' + INT_FIELD_1 + ISNULL_OPERATOR + true, rsql);
    }

    @Test
    void generateInConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().in(ENUM_FIELD_1, Arrays.asList(ENUM_VALUE_1, ENUM_VALUE_2)).end().execute();
        Assertions.assertEquals(ENUM_FIELD_1 + "=in=(" + ENUM_VALUE_1 + ',' + ENUM_VALUE_2 + ')', rsql);
    }

    @Test
    void generateNotInConditionShouldSucceed() {
        final String rsql = new RsqlGenerator().nin(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2)).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + "=out=(" + STRING_VALUE_1 + ',' + STRING_VALUE_2 + ')', rsql);
    }

    @Test
    void generateAndConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().eq(STRING_FIELD_1, STRING_VALUE_1).and().ne(STRING_FIELD_2, STRING_VALUE_2).end().execute();
        Assertions.assertEquals(STRING_FIELD_1 + "==" + STRING_VALUE_1 + ';' + STRING_FIELD_2 + "!=" + STRING_VALUE_2, rsql);
    }

    @Test
    void generateOrConditionsShouldSucceed() {
        final String rsql = new RsqlGenerator().eq(INT_FIELD_2, INT_VALUE_2).or().ne(ENUM_FIELD_2, ENUM_VALUE_2).end().execute();
        Assertions.assertEquals(INT_FIELD_2 + "==" + INT_VALUE_2 + ',' + ENUM_FIELD_2 + "!=" + ENUM_VALUE_2, rsql);
    }
}
