package com.postnord.ndm.base.common_utils.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class StringHelper {
    private static final String SNAKE_CASE_DELIMETER = "_";
    private static final String CAMEL_TO_SNAKE_REGEX = "([a-z])([A-Z]+)";
    private static final String CAMEL_TO_SNAKE__REPLACEMENT = "$1_$2";

    /**
     * Convert a string from snake_case to CamelCase
     *
     * @param field The snake_case string to convert
     * @return The CamelCase result
     */
    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    public static String snakeToCamelCase(final String field) {
        if (field == null) {
            return null;
        }
        if (!field.contains(SNAKE_CASE_DELIMETER)) {
            return field.toLowerCase();
        }
        final List<String> fragments =
                Arrays.stream(field.split(SNAKE_CASE_DELIMETER)).filter(f -> !f.isBlank()).collect(Collectors.toList());
        if (fragments.isEmpty()) {
            return "";
        }
        final var stringBuilder = new StringBuilder(fragments.get(0).toLowerCase());
        fragments.subList(1, fragments.size()).forEach(fragment ->
                stringBuilder.append(Character.toUpperCase(fragment.charAt(0))).append(fragment.substring(1).toLowerCase()));

        return stringBuilder.toString();
    }

    /**
     * Convert a string from camelCase to snake_case
     *
     * @param field The camelCase string to convert
     * @return The snake_case result
     */
    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    public static String camelToSnakeCase(final String field) {
        if (field == null) {
            return null;
        }
        return field.replaceAll(CAMEL_TO_SNAKE_REGEX, CAMEL_TO_SNAKE__REPLACEMENT).toLowerCase();
    }

    private StringHelper() {
    }
}
