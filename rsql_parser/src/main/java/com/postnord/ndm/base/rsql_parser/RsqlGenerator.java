package com.postnord.ndm.base.rsql_parser;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Build RSQL strings. See tests for examples.
 */
@SuppressWarnings({"PMD.ShortMethodName", "PMD.AvoidStringBufferField", "PMD.LinguisticNaming"})
public class RsqlGenerator {
    private final StringBuilder filterBuilder = new StringBuilder();

    /**
     * Add 'equal' condition to filter. Value can contain wildcards (*).
     *
     * @param field The field in the target model
     * @param value The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner eq(final String field, final Object value) {
        return append(field, valueToString(value), "==");
    }

    /**
     * Add 'equal' conditions to filter. Value can contain wildcards (*).
     *
     * @param field            The field in the target model
     * @param values           The target values
     * @param combineOperation The combine operation to apply (and/or)
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner eq(final String field, final Collection<?> values, final CombineOperation combineOperation) {
        return append(field, valuesToString(values), "==", Objects.requireNonNull(combineOperation.getDelimiter()));
    }

    /**
     * Add 'not equal' condition to filter. Value can contain wildcards (*).
     *
     * @param field The field in the target model
     * @param value The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner ne(final String field, final Object value) {
        return append(field, valueToString(value), "!=");
    }

    /**
     * Add 'not equal' conditions to filter. Value can contain wildcards (*).
     *
     * @param field            The field in the target model
     * @param values           The target values
     * @param combineOperation The combine operation to apply (and/or)
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner ne(final String field, final Collection<?> values, final CombineOperation combineOperation) {
        return append(field, valuesToString(values), "!=", Objects.requireNonNull(combineOperation).getDelimiter());
    }

    /**
     * Add 'greater than' condition to filter.
     *
     * @param field The field in the target model
     * @param value The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner gt(final String field, final Comparable<?> value) {
        return append(field, valueToString(value), ">");
    }

    /**
     * Add 'greater than' conditions to filter.
     *
     * @param field            The field in the target model
     * @param values           The target values
     * @param combineOperation The combine operation to apply (and/or)
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner gt(final String field, final Collection<Comparable<?>> values, final CombineOperation combineOperation) {
        return append(field, valuesToString(values), ">", Objects.requireNonNull(combineOperation).getDelimiter());
    }

    /**
     * Add 'greater than or equal' condition to filter.
     *
     * @param field The field in the target model
     * @param value The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner gte(final String field, final Comparable<?> value) {
        return append(field, valueToString(value), ">=");
    }

    /**
     * Add 'greater than or equal' conditions to filter.
     *
     * @param field            The field in the target model
     * @param values           The target values
     * @param combineOperation The combine operation to apply (and/or)
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner gte(final String field, final Collection<Comparable<?>> values, final CombineOperation combineOperation) {
        return append(field, valuesToString(values), ">=", Objects.requireNonNull(combineOperation).getDelimiter());
    }

    /**
     * Add 'less than' condition to filter.
     *
     * @param field The field in the target model
     * @param value The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner lt(final String field, final Comparable<?> value) {
        return append(field, valueToString(value), "<");
    }

    /**
     * Add 'less than' conditions to filter.
     *
     * @param field            The field in the target model
     * @param values           The target values
     * @param combineOperation The combine operation to apply (and/or)
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner lt(final String field, final Collection<Comparable<?>> values, final CombineOperation combineOperation) {
        return append(field, valuesToString(values), "<", Objects.requireNonNull(combineOperation).getDelimiter());
    }

    /**
     * Add 'less than or equal' condition to filter.
     *
     * @param field The field in the target model
     * @param value The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner lte(final String field, final Comparable<?> value) {
        return append(field, valueToString(value), "<=");
    }

    /**
     * Add 'less than or equal' conditions to filter.
     *
     * @param field            The field in the target model
     * @param values           The target values
     * @param combineOperation The combine operation to apply (and/or)
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner lte(final String field, final Collection<Comparable<?>> values, final CombineOperation combineOperation) {
        return append(field, valuesToString(values), "<=", Objects.requireNonNull(combineOperation).getDelimiter());
    }

    /**
     * Add 'contains' condition to filter. Value can contain wildcards (*).
     *
     * @param field The field in the target model
     * @param value The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner contains(final String field, final Object value) {
        return append(field, valueToString(value), "=contains=");
    }

    /**
     * Add 'contains' conditions to filter. Value can contain wildcards (*).
     *
     * @param field            The field in the target model
     * @param values           The target values
     * @param combineOperation The combine operation to apply (and/or)
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner contains(final String field, final Collection<?> values, final CombineOperation combineOperation) {
        return append(field, valuesToString(values), "=contains=", Objects.requireNonNull(combineOperation.getDelimiter()));
    }

    /**
     * Add 'excludes' condition to filter. Value can contain wildcards (*).
     *
     * @param field The field in the target model
     * @param value The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner excludes(final String field, final Object value) {
        return append(field, valueToString(value), "=excludes=");
    }

    /**
     * Add 'excludes' conditions to filter. Value can contain wildcards (*).
     *
     * @param field            The field in the target model
     * @param values           The target values
     * @param combineOperation The combine operation to apply (and/or)
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner excludes(final String field, final Collection<?> values, final CombineOperation combineOperation) {
        return append(field, valuesToString(values), "=excludes=", Objects.requireNonNull(combineOperation.getDelimiter()));
    }

    /**
     * Add 'isnull' condition to filter.
     *
     * @param field The field in the target model
     * @param value The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner isNull(final String field, final boolean value) {
        return append(field, valueToString(value), "=isnull=");
    }

    /**
     * Add 'isnull' conditions to filter.
     *
     * @param field            The field in the target model
     * @param values           The target values
     * @param combineOperation The combine operation to apply (and/or)
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner isNull(final String field, final Collection<Boolean> values, final CombineOperation combineOperation) {
        return append(field, valuesToString(values), "=isnull=", Objects.requireNonNull(combineOperation.getDelimiter()));
    }

    /**
     * Add 'in' list condition to filter.
     *
     * @param field  The field in the target model
     * @param values The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner in(final String field, final Collection<?> values) {
        return append(field, valuesToString(values), "in");
    }

    /**
     * Add 'not in' condition to filter.
     *
     * @param field  The field in the target model
     * @param values The target value
     * @return A combiner that can be used to concatenate conditions
     */
    public Combiner nin(final String field, final Collection<?> values) {
        return append(field, valuesToString(values), "out");
    }

    /**
     * Build the resulting RSQL string.
     *
     * @return The resulting RSQL string
     */
    public String execute() {
        return filterBuilder.toString();
    }

    private Combiner append(final String field, final String value, final String operator) {
        filterBuilder.append(Objects.requireNonNull(field)).append(operator).append(Objects.requireNonNull(value));
        return new Combiner(this);
    }

    private Combiner append(final String field, final Collection<String> values, final String operator) {
        filterBuilder
                .append(Objects.requireNonNull(field))
                .append('=')
                .append(operator)
                .append("=(")
                .append(String.join(",", Objects.requireNonNull(values)))
                .append(')');
        return new Combiner(this);
    }

    private Combiner append(final String field, final Collection<String> values, final String operator, final String delimiter) {
        final List<String> conditions = Objects.requireNonNull(values).stream()
                .map(value -> Objects.requireNonNull(field) + operator + value)
                .collect(Collectors.toList());
        filterBuilder.append(String.join(delimiter, conditions));
        return new Combiner(this);
    }

    private static String valueToString(final Object value) {
        if (value instanceof LocalDate) {
            return ((LocalDate) value).format(DateTimeFormatter.ISO_DATE);
        }
        if (value instanceof Instant) {
            return InstantHelper.getStringFromInstant((Instant) value);
        }
        return value.toString();
    }

    private static <T> List<String> valuesToString(final Collection<T> values) {
        return values.stream().map(RsqlGenerator::valueToString).collect(Collectors.toList());
    }

    /**
     * Builder helper that can be used to concatenate conditions.
     */
    public static class Combiner {
        private final RsqlGenerator rsqlBuilder;

        public Combiner(final RsqlGenerator rsqlBuilder) {
            this.rsqlBuilder = rsqlBuilder;
        }

        /**
         * Add 'and' condition.
         *
         * @return The RSQL builder
         */
        public RsqlGenerator and() {
            rsqlBuilder.filterBuilder.append(';');
            return rsqlBuilder;
        }

        /**
         * Add 'or' condition.
         *
         * @return The RSQL builder
         */
        public RsqlGenerator or() {
            rsqlBuilder.filterBuilder.append(',');
            return rsqlBuilder;
        }

        /**
         * NOP - return RSQL builder for completion.
         *
         * @return The RSQL builder
         */
        public RsqlGenerator end() {
            return rsqlBuilder;
        }
    }

    /**
     * Combine operations.
     */
    public enum CombineOperation {
        AND(";"),
        OR(",");

        private final String delimiter;

        CombineOperation(final String delimiter) {
            this.delimiter = delimiter;
        }

        public String getDelimiter() {
            return delimiter;
        }
    }
}
