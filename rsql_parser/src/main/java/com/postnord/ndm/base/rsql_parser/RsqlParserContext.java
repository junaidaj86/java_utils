package com.postnord.ndm.base.rsql_parser;

import com.postnord.ndm.base.rsql_parser.utils.ParsingUtils;
import com.postnord.ndm.base.rsql_parser.utils.RsqlNode;
import com.postnord.ndm.base.rsql_parser.utils.RsqlOperator;

import java.util.List;

public interface RsqlParserContext<T, R> {
    /**
     * Get entity type.
     *
     * @param clientContext The parsed entity (e.g. jakarta.persistence.criteria.Root<MyEntity>)
     * @return The entity type.
     */
    Class<?> getEntityType(T clientContext);

    /**
     * Combine node results using 'and' operator
     *
     * @param clientContext The parsed entity (e.g. jakarta.persistence.criteria.Root<MyEntity>)
     * @param nodeResults   Results from parsed sub nodes (typically entity fields)
     * @return The parsed result (e.g. jakarta.persistence.criteria.Predicate)
     */
    R combineAnd(T clientContext, List<R> nodeResults);

    /**
     * Combine node results using 'or' operator
     *
     * @param clientContext The parsed entity (e.g. jakarta.persistence.criteria.Root<MyEntity>)
     * @param nodeResults   Results from parsed sub nodes (typically entity fields)
     * @return The parsed result (e.g. jakarta.persistence.criteria.Predicate)
     */
    R combineOr(T clientContext, List<R> nodeResults);

    /**
     * Apply requested operator on specific entity field
     *
     * @param clientContext The parsed entity (e.g. jakarta.persistence.criteria.Root<MyEntity>)
     * @param node          The entity field context to process
     * @return The parsed result (e.g. jakarta.persistence.criteria.Predicate)
     */
    R processNode(T clientContext, RsqlNode node);

    /**
     * Convert field names from snake_case to camelCase.
     *
     * @return true, if field names should be converted, false otherwise
     */
    default boolean convertFieldNames() {
        return false;
    }

    /**
     * Create a node from entity field context.
     *
     * @param clientContext The parsed entity (e.g. jakarta.persistence.criteria.Root<MyEntity>)
     * @param fieldName     The name of the field
     * @param operator      The comparison operator to apply
     * @param arguments     The arguments (requires parsing before they can be compared against the actual field value(s))
     * @return The created node
     */
    default RsqlNode createNode(final T clientContext, final String fieldName, final RsqlOperator operator, final List<String> arguments) {
        return ParsingUtils.parseArguments(getEntityType(clientContext), fieldName, operator, arguments);
    }
}
