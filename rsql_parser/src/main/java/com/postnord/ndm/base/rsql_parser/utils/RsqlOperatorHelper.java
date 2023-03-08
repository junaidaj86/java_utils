package com.postnord.ndm.base.rsql_parser.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

public final class RsqlOperatorHelper {
    private static final Map<ComparisonOperator, RsqlOperator> CONVERSION_MAP = createConversionMap();

    public static Set<ComparisonOperator> getSupportedOperators() {
        return Collections.unmodifiableSet(CONVERSION_MAP.keySet());
    }

    static RsqlOperator resolve(final ComparisonOperator comparisonOperator) {
        return CONVERSION_MAP.get(comparisonOperator);
    }

    private static Map<ComparisonOperator, RsqlOperator> createConversionMap() {
        final Map<ComparisonOperator, RsqlOperator> conversionMap = new HashMap<>();
        conversionMap.put(RSQLOperators.EQUAL, RsqlOperator.EQUAL);
        conversionMap.put(RSQLOperators.NOT_EQUAL, RsqlOperator.NOT_EQUAL);
        conversionMap.put(RSQLOperators.GREATER_THAN, RsqlOperator.GREATER_THAN);
        conversionMap.put(RSQLOperators.GREATER_THAN_OR_EQUAL, RsqlOperator.GREATER_THAN_OR_EQUAL);
        conversionMap.put(RSQLOperators.LESS_THAN, RsqlOperator.LESS_THAN);
        conversionMap.put(RSQLOperators.LESS_THAN_OR_EQUAL, RsqlOperator.LESS_THAN_OR_EQUAL);
        conversionMap.put(RSQLOperators.IN, RsqlOperator.IN);
        conversionMap.put(RSQLOperators.NOT_IN, RsqlOperator.NOT_IN);
        conversionMap.put(new ComparisonOperator("=contains="), RsqlOperator.CONTAINS);
        conversionMap.put(new ComparisonOperator("=excludes="), RsqlOperator.EXCLUDES);
        conversionMap.put(new ComparisonOperator("=isnull="), RsqlOperator.IS_NULL);

        return conversionMap;
    }

    private RsqlOperatorHelper() {
    }
}
