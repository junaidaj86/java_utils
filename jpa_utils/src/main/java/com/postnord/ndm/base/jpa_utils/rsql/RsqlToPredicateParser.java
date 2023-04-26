package com.postnord.ndm.base.jpa_utils.rsql;

import com.postnord.ndm.base.rsql_parser.RsqlParser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Build JPA criteria predicates from RSQL strings. See tests for examples.
 *
 * @param <T> The entity type
 */
public class RsqlToPredicateParser<T> extends RsqlParser<Root<T>, Predicate> {

    /**
     * Constructor
     *
     * @param criteriaBuilder CriteriaBuilder provided by EntityManager
     * @param root            Query root
     */
    public RsqlToPredicateParser(final CriteriaBuilder criteriaBuilder, final Root<T> root) {
        super(root, new RsqlParserContextImpl<>(criteriaBuilder));
    }

    /**
     * Constructor
     *
     * @param parserContext The parser context
     * @param root          Query root
     */
    public RsqlToPredicateParser(final RsqlParserContextImpl<T> parserContext,
                                 final Root<T> root) {
        super(root, parserContext);
    }

    /**
     * Add RSQL string to builder.
     *
     * @param filter RSQL string
     * @return The parser
     */
    @Override
    public RsqlToPredicateParser<T> addFilter(final String filter) {
        return (RsqlToPredicateParser<T>) super.addFilter(filter);
    }
}
