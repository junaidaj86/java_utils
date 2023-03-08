package com.postnord.ndm.base.rsql_parser;

import com.postnord.ndm.api.common.exception.APIException;
import com.postnord.ndm.base.rsql_parser.utils.RsqlNode;
import com.postnord.ndm.base.rsql_parser.utils.TestEntity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.ENUM_FIELD_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.ENUM_FIELD_2;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.ENUM_VALUE_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.ENUM_VALUE_2;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INSTANT_FIELD;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INSTANT_VALUE;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INT_FIELD_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INT_FIELD_2;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INT_VALUE_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.INT_VALUE_2;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.STRING_FIELD_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.STRING_FIELD_2;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.STRING_VALUE_1;
import static com.postnord.ndm.base.rsql_parser.RsqlTestDataHelper.STRING_VALUE_2;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@SuppressWarnings("PMD.TooManyStaticImports")
class RsqlParserTest {
    private static final RsqlParserContext<Class<TestEntity>, String> PARSER_CONTEXT = createRsqlParserContext();

    @Test
    void parseEqualConditionShouldSucceed() {
        final String filter = new RsqlGenerator().eq(STRING_FIELD_1, STRING_VALUE_1).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseEqualAndConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().eq(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseEqualOrConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().eq(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseNotEqualConditionShouldSucceed() {
        final String filter = new RsqlGenerator().ne(ENUM_FIELD_1, ENUM_VALUE_1).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseNotEqualAndConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().ne(ENUM_FIELD_1, Arrays.asList(ENUM_VALUE_1, ENUM_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseNotEqualOrConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().ne(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseGreaterThanConditionShouldSucceed() {
        final String filter = new RsqlGenerator().gt(INSTANT_FIELD, INSTANT_VALUE).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseGreaterThanAndConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().gt(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseGreaterThanOrConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().gt(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseGreaterThanOrEqualConditionShouldSucceed() {
        final String filter = new RsqlGenerator().gte(INSTANT_FIELD, INSTANT_VALUE).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseGreaterThanOrEqualAndConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().gte(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseGreaterThanOrEqualOrConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().gte(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseLessThanConditionShouldSucceed() {
        final String filter = new RsqlGenerator().lt(INSTANT_FIELD, INSTANT_VALUE).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseLessThanAndConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().lt(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseLessThanOrConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().lt(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseLessThanOrEqualConditionShouldSucceed() {
        final String filter = new RsqlGenerator().lte(INSTANT_FIELD, INSTANT_VALUE).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseLessThanOrEqualAndConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().lte(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseLessThanOrEqualOrConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().lte(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseContainsConditionShouldSucceed() {
        final String filter = new RsqlGenerator().contains(STRING_FIELD_1, STRING_VALUE_1).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseContainsAndConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().contains(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseContainsOrConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().contains(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseExcludesConditionShouldSucceed() {
        final String filter = new RsqlGenerator().excludes(STRING_FIELD_1, STRING_VALUE_1).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseExcludesAndConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().excludes(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2), RsqlGenerator.CombineOperation.AND).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseExcludesOrConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().excludes(INT_FIELD_1, Arrays.asList(INT_VALUE_1, INT_VALUE_2), RsqlGenerator.CombineOperation.OR).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseIsNullConditionShouldSucceed() {
        final String filter = new RsqlGenerator().isNull(STRING_FIELD_1, true).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseIsNullAndConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().isNull(STRING_FIELD_1, Arrays.asList(true, false), RsqlGenerator.CombineOperation.AND).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseIsNullOrConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().isNull(INT_FIELD_1, Arrays.asList(false, true), RsqlGenerator.CombineOperation.OR).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseInConditionShouldSucceed() {
        final String filter = new RsqlGenerator().in(ENUM_FIELD_1, Arrays.asList(ENUM_VALUE_1, ENUM_VALUE_2)).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseNotInConditionShouldSucceed() {
        final String filter = new RsqlGenerator().nin(STRING_FIELD_1, Arrays.asList(STRING_VALUE_1, STRING_VALUE_2)).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseAndConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().eq(STRING_FIELD_1, STRING_VALUE_1).and().ne(STRING_FIELD_2, STRING_VALUE_2).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseOrConditionsShouldSucceed() {
        final String filter = new RsqlGenerator().eq(INT_FIELD_2, INT_VALUE_2).or().ne(ENUM_FIELD_2, ENUM_VALUE_2).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter).execute();

        Assertions.assertEquals(filter, result);
    }

    @Test
    void parseTwoFiltersShouldSucceed() {
        final String filter1 = new RsqlGenerator().eq(STRING_FIELD_1, STRING_VALUE_1).end().execute();
        final String filter2 = new RsqlGenerator().ne(STRING_FIELD_2, STRING_VALUE_2).end().execute();
        final String result = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter1).addFilter(filter2).execute();

        Assertions.assertEquals(filter1 + ';' + filter2, result);
    }

    @Test
    void parseInvalidFieldNameShouldThrowAPIException() {
        final String filter = "dummy==*";
        final RsqlParser<Class<TestEntity>, String> rsqlParser = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter);
        final APIException apiException = Assertions.assertThrows(APIException.class, rsqlParser::execute);

        Assertions.assertEquals(BAD_REQUEST.getReasonPhrase(), apiException.getTitle());
        Assertions.assertEquals(BAD_REQUEST.getStatusCode(), apiException.getStatus());
        Assertions.assertEquals(RsqlParser.INVALID_FILTER_MESSAGE, apiException.getDetail());
    }

    @Test
    void parseInvalidFieldValueShouldThrowAPIException() {
        final String filter = "testInteger1==*";
        final RsqlParser<Class<TestEntity>, String> rsqlParser = new RsqlParser<>(TestEntity.class, PARSER_CONTEXT).addFilter(filter);
        final APIException apiException = Assertions.assertThrows(APIException.class, rsqlParser::execute);

        Assertions.assertEquals(BAD_REQUEST.getReasonPhrase(), apiException.getTitle());
        Assertions.assertEquals(BAD_REQUEST.getStatusCode(), apiException.getStatus());
        Assertions.assertEquals(RsqlParser.INVALID_FILTER_MESSAGE, apiException.getDetail());
    }

    private static RsqlParserContext<Class<TestEntity>, String> createRsqlParserContext() {
        return new RsqlParserContext<>() {
            @Override
            public Class<?> getEntityType(final Class<TestEntity> clientContext) {
                return clientContext;
            }

            @Override
            public String combineAnd(final Class<TestEntity> clientContext, final List<String> nodeResults) {
                return String.join(";", nodeResults);
            }

            @Override
            public String combineOr(final Class<TestEntity> clientContext, final List<String> nodeResults) {
                return String.join(",", nodeResults);
            }

            @Override
            public String processNode(final Class<TestEntity> clientContext, final RsqlNode node) {
                final RsqlGenerator rsqlGenerator = new RsqlGenerator();
                switch (node.getOperator()) {
                    case EQUAL:
                        return rsqlGenerator.eq(node.getFieldName(), node.getArguments().get(0)).end().execute();
                    case NOT_EQUAL:
                        return rsqlGenerator.ne(node.getFieldName(), node.getArguments().get(0)).end().execute();
                    case GREATER_THAN:
                        return rsqlGenerator.gt(node.getFieldName(), (Comparable<?>) node.getArguments().get(0)).end().execute();
                    case GREATER_THAN_OR_EQUAL:
                        return rsqlGenerator.gte(node.getFieldName(), (Comparable<?>) node.getArguments().get(0)).end().execute();
                    case LESS_THAN:
                        return rsqlGenerator.lt(node.getFieldName(), (Comparable<?>) node.getArguments().get(0)).end().execute();
                    case LESS_THAN_OR_EQUAL:
                        return rsqlGenerator.lte(node.getFieldName(), (Comparable<?>) node.getArguments().get(0)).end().execute();
                    case IN:
                        return rsqlGenerator.in(node.getFieldName(), node.getArguments()).end().execute();
                    case NOT_IN:
                        return rsqlGenerator.nin(node.getFieldName(), node.getArguments()).end().execute();
                    case CONTAINS:
                        return rsqlGenerator.contains(node.getFieldName(), node.getArguments().get(0)).end().execute();
                    case EXCLUDES:
                        return rsqlGenerator.excludes(node.getFieldName(), node.getArguments().get(0)).end().execute();
                    case IS_NULL:
                        return rsqlGenerator.isNull(node.getFieldName(), Boolean.parseBoolean(node.getArguments().get(0).toString())).end().execute();
                    default:
                        return rsqlGenerator.execute();
                }
            }
        };
    }
}
