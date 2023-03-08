package com.postnord.ndm.base.common_utils.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.postnord.ndm.base.common_utils.utils.StringHelper.camelToSnakeCase;
import static com.postnord.ndm.base.common_utils.utils.StringHelper.snakeToCamelCase;

class StringHelperTest {

    @Test
    void testSnakeToCamelCase() {
        Assertions.assertEquals("testField", snakeToCamelCase("test_field"));
    }

    @Test
    void testNullToCamelCase() {
        Assertions.assertNull(snakeToCamelCase(null));
    }

    @Test
    void testBlankStringToCamelCase() {
        Assertions.assertEquals("", snakeToCamelCase(""));
    }

    @Test
    void testUnderscoreToCamelCase() {
        Assertions.assertEquals("", snakeToCamelCase("_"));
    }

    @Test
    void testUnderscoreLetterToCamelCase() {
        Assertions.assertEquals("f", snakeToCamelCase("_f"));
    }

    @Test
    void testLetterUnderscoreToCamelCase() {
        Assertions.assertEquals("f", snakeToCamelCase("f_"));
    }

    @Test
    void testCamelToSnakeCase() {
        Assertions.assertEquals("test_field", camelToSnakeCase("testField"));
    }

    @Test
    void testNullToSnakeCase() {
        Assertions.assertNull(camelToSnakeCase(null));
    }

    @Test
    void testBlankStringToSnakeCase() {
        Assertions.assertEquals("", camelToSnakeCase(""));
    }

    @Test
    void testUnderscoreToSnakeCase() {
        Assertions.assertEquals("_", camelToSnakeCase("_"));
    }

    @Test
    void testUnderscoreLetterToSnakeCase() {
        Assertions.assertEquals("_f", camelToSnakeCase("_f"));
    }

    @Test
    void testLetterUnderscoreToSnakeCase() {
        Assertions.assertEquals("f_", camelToSnakeCase("f_"));
    }
}
