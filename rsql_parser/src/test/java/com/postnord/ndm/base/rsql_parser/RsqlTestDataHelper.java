package com.postnord.ndm.base.rsql_parser;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;
import com.postnord.ndm.base.rsql_parser.utils.TestEnum;

final class RsqlTestDataHelper {
    static final String STRING_FIELD_1 = "testString1";
    static final String STRING_FIELD_2 = "testString2";
    static final String STRING_VALUE_1 = "test_1";
    static final String STRING_VALUE_2 = "test_2";
    static final String INT_FIELD_1 = "testInteger1";
    static final String INT_FIELD_2 = "testInteger2";
    static final Integer INT_VALUE_1 = 1;
    static final Integer INT_VALUE_2 = 2;
    static final String ENUM_FIELD_1 = "testEnum1";
    static final String ENUM_FIELD_2 = "testEnum2";
    static final TestEnum ENUM_VALUE_1 = TestEnum.ONE;
    static final TestEnum ENUM_VALUE_2 = TestEnum.TWO;
    static final String INSTANT_FIELD = "testInstant";
    static final String INSTANT_VALUE = InstantHelper.getStringFromInstant();
    static final String CONTAINS_OPERATOR = "=contains=";
    static final String EXCLUDES_OPERATOR = "=excludes=";
    static final String ISNULL_OPERATOR = "=isnull=";

    private RsqlTestDataHelper() {
    }
}
