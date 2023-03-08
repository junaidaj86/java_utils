package com.postnord.ndm.base.jpa_utils.rsql;

import com.postnord.ndm.base.rsql_parser.RsqlParserContext;
import com.postnord.ndm.base.rsql_parser.utils.ParserException;
import com.postnord.ndm.base.rsql_parser.utils.RsqlNode;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

public class RsqlParserContextImpl<T> implements RsqlParserContext<Root<T>, Predicate> {
    private final CriteriaBuilder criteriaBuilder;

    public RsqlParserContextImpl(final CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    /**
     * Get entity type.
     *
     * @param root the root type in the from claus
     * @return The entity type.
     */
    @Override
    public final Class<? extends T> getEntityType(final Root<T> root) {
        return root.getModel().getJavaType();
    }

    /**
     * Combine node results using 'and' operator
     *
     * @param root        the root type in the from claus
     * @param nodeResults Results from parsed sub nodes (typically entity fields)
     * @return The combined predicate
     */
    @Override
    public final Predicate combineAnd(final Root<T> root, final List<Predicate> nodeResults) {
        return criteriaBuilder.and(nodeResults.toArray(new Predicate[0]));
    }

    /**
     * Combine node results using 'or' operator
     *
     * @param root        the root type in the from claus
     * @param nodeResults Results from parsed sub nodes (typically entity fields)
     * @return The combined predicate
     */
    @Override
    public final Predicate combineOr(final Root<T> root, final List<Predicate> nodeResults) {
        return criteriaBuilder.or(nodeResults.toArray(new Predicate[0]));
    }

    /**
     * Apply requested operator on specific entity field
     *
     * @param root the root type in the from claus
     * @param node The entity field context to process
     * @return The resulting predicate
     */
    @Override
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public Predicate processNode(final Root<T> root, final RsqlNode node) {
        switch (node.getOperator()) {
            case EQUAL:
                return getEqualPredicate(root, node.getFieldName(), node.getArguments().get(0));
            case NOT_EQUAL:
                return getNotEqualPredicate(root, node.getFieldName(), node.getArguments().get(0));
            case GREATER_THAN:
                return getGreaterThanPredicate(root, node.getFieldName(), castComparable(node.getArguments().get(0)));
            case GREATER_THAN_OR_EQUAL:
                return getGreaterThanOrEqualPredicate(root, node.getFieldName(), castComparable(node.getArguments().get(0)));
            case LESS_THAN:
                return getLessThanPredicate(root, node.getFieldName(), castComparable(node.getArguments().get(0)));
            case LESS_THAN_OR_EQUAL:
                return getLessThanOrEqualPredicate(root, node.getFieldName(), castComparable(node.getArguments().get(0)));
            case IN:
                return getInPredicate(root, node.getFieldName(), node.getArguments());
            case NOT_IN:
                return getNotInPredicate(root, node.getFieldName(), node.getArguments());
            case CONTAINS:
                return getContainsPredicate(root, node.getFieldName(), node.getArguments().get(0));
            case EXCLUDES:
                return getExcludesPredicate(root, node.getFieldName(), node.getArguments().get(0));
            case IS_NULL:
                return getIsNullPredicate(root, node.getFieldName(), node.getArguments().get(0));
            default:
                return null;
        }
    }

    private Predicate getEqualPredicate(final Root<T> root, final String selector, final Object argument) {
        if (argument instanceof String && argument.toString().contains("*")) {
            return criteriaBuilder.like(root.get(selector), argument.toString().replace('*', '%'));
        }
        return criteriaBuilder.equal(root.get(selector), argument);
    }

    private Predicate getNotEqualPredicate(final Root<T> root, final String selector, final Object argument) {
        return getEqualPredicate(root, selector, argument).not();
    }

    private Predicate getLessThanOrEqualPredicate(final Root<T> root, final String selector, final Comparable<Object> argument) {
        return criteriaBuilder.lessThanOrEqualTo(root.get(selector), argument);
    }

    private Predicate getLessThanPredicate(final Root<T> root, final String selector, final Comparable<Object> argument) {
        return criteriaBuilder.lessThan(root.get(selector), argument);
    }

    private Predicate getGreaterThanOrEqualPredicate(final Root<T> root, final String selector, final Comparable<Object> argument) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get(selector), argument);
    }

    private Predicate getGreaterThanPredicate(final Root<T> root, final String selector, final Comparable<Object> argument) {
        return criteriaBuilder.greaterThan(root.get(selector), argument);
    }

    private Predicate getNotInPredicate(final Root<T> root, final String selector, final List<?> arguments) {
        return root.get(selector).in(arguments).not();
    }

    private Predicate getInPredicate(final Root<T> root, final String selector, final List<?> arguments) {
        return root.get(selector).in(arguments);
    }

    private Predicate getContainsPredicate(final Root<T> root, final String selector, final Object argument) {
        if (argument instanceof String && argument.toString().contains("*")) {
            //noinspection unchecked
            return criteriaBuilder.like((Join<T, String>) getJoin(root, selector), argument.toString().replace('*', '%'));
        }
        return criteriaBuilder.equal(getJoin(root, selector), argument);
    }

    private Predicate getExcludesPredicate(final Root<T> root, final String selector, final Object argument) {
        final Subquery<T> subquery = criteriaBuilder.createQuery(root.getModel().getJavaType()).subquery(root.getModel().getJavaType());
        final Root<T> subRoot = subquery.from(root.getModel().getJavaType());
        subquery.select(subRoot).where(getContainsPredicate(subRoot, selector, argument));
        return criteriaBuilder.in(root).value(subquery).not();
    }

    private Predicate getIsNullPredicate(final Root<T> root, final String selector, final Object argument) {
        if ((boolean) argument) {
            return criteriaBuilder.isNull(root.get(selector));
        }
        return criteriaBuilder.isNotNull(root.get(selector));
    }

    private Join<T, ?> getJoin(final Root<T> root, final String selector) {
        final Class<?> type = root.get(selector).getJavaType();
        if (List.class.isAssignableFrom(type)) {
            return root.joinList(selector);
        }
        if (Set.class.isAssignableFrom(type)) {
            return root.joinSet(selector);
        }
        if (Collection.class.isAssignableFrom(type)) {
            return root.joinCollection(selector);
        }
        return root.join(selector);
    }

    private Comparable<Object> castComparable(final Object argument) {
        if (!Comparable.class.isAssignableFrom(argument.getClass())) {
            throw new ParserException("Operator can only be used with arguments of type " + Comparable.class);
        }
        //noinspection unchecked
        return (Comparable<Object>) argument;
    }
}
