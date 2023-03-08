package com.postnord.ndm.base.rsql_parser.utils;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public final class ParsingUtils {
    private static final String ONE = "1";
    private static final EnumMap<ArgumentType, BiFunction<String, Type, List<?>>> PARSERS = createParsers();

    public static RsqlNode parseArguments(final Class<?> entityType,
                                          final String fieldName,
                                          final RsqlOperator operator,
                                          final List<String> arguments) {
        final var fieldType = EntityFieldCache.getFieldType(entityType, fieldName);
        final BiFunction<String, Type, List<?>> parser;
        if (operator.equals(RsqlOperator.IS_NULL)) {
            parser = (argument, type) -> Collections.singletonList(Boolean.parseBoolean(argument) || ONE.equals(argument));
        } else {
            parser = getParser(fieldType);
        }
        return RsqlNode.builder()
                .fieldName(fieldName)
                .fieldType(fieldType)
                .operator(operator)
                .arguments(
                        arguments.stream()
                                .map(argument -> parseArgument(fieldType, argument, parser))
                                .flatMap(List::stream)
                                .collect(Collectors.toList())
                ).build();
    }

    private static BiFunction<String, Type, List<?>> getParser(final Type type) {
        final BiFunction<String, Type, List<?>> parser = PARSERS.get(ArgumentType.fromJavaType(type));
        if (parser == null) {
            throw new ParserException("No parser registered for type: " + type);
        }
        return parser;
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    private static List<?> parseArgument(final Type fieldType, final String argument, final BiFunction<String, Type, List<?>> parser) {
        try {
            return parser.apply(argument, fieldType);
        } catch (Exception e) {
            throw new ParserException("Failed to parse argument: " + argument, e);
        }
    }

    private static EnumMap<ArgumentType, BiFunction<String, Type, List<?>>> createParsers() {
        final EnumMap<ArgumentType, BiFunction<String, Type, List<?>>> parsers = new EnumMap<>(ArgumentType.class);
        parsers.put(ArgumentType.STRING, (argument, type) -> Collections.singletonList(argument));
        parsers.put(ArgumentType.BOOLEAN, (argument, type) -> Collections.singletonList(Boolean.parseBoolean(argument)));
        parsers.put(ArgumentType.INTEGER, (argument, type) -> Collections.singletonList(Integer.parseInt(argument)));
        parsers.put(ArgumentType.LONG, (argument, type) -> Collections.singletonList(Long.parseLong(argument)));
        parsers.put(ArgumentType.FLOAT, (argument, type) -> Collections.singletonList(Float.parseFloat(argument)));
        parsers.put(ArgumentType.DOUBLE, (argument, type) -> Collections.singletonList(Double.parseDouble(argument)));
        parsers.put(ArgumentType.BIG_DECIMAL, (argument, type) -> Collections.singletonList(new BigDecimal(argument)));
        parsers.put(ArgumentType.LOCAL_DATE, (argument, type) -> Collections.singletonList(LocalDate.parse(argument, DateTimeFormatter.ISO_DATE)));
        parsers.put(ArgumentType.INSTANT, (argument, type) ->
                Collections.singletonList(InstantHelper.getInstantFromString(argument)));
        parsers.put(ArgumentType.UUID, (argument, type) -> Collections.singletonList(UUID.fromString(argument)));
        //noinspection unchecked
        parsers.put(ArgumentType.ENUM, (argument, type) ->
                Arrays.stream(((Class<Enum<?>>) type).getEnumConstants())
                        .filter(e -> e.name().matches(argument.toUpperCase(Locale.getDefault()).replace("*", ".*")))
                        .collect(Collectors.toList()));

        return parsers;
    }

    private ParsingUtils() {
    }
}
