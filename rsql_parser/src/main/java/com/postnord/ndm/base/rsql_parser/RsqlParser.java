package com.postnord.ndm.base.rsql_parser;

import com.postnord.ndm.api.common.exception.APIException;
import com.postnord.ndm.base.rsql_parser.utils.ParserException;
import com.postnord.ndm.base.rsql_parser.utils.RsqlOperatorHelper;
import com.postnord.ndm.base.rsql_parser.utils.RsqlVisitor;

import java.util.ArrayList;
import java.util.List;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.RSQLParserException;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Parse RSQL strings. See tests for examples.
 *
 * @param <T> The parser type (e.g. javax.persistence.criteria.Root<MyEntity>)
 * @param <R> The response type (e.g. javax.persistence.criteria.Predicate)
 */
public class RsqlParser<T, R> {
    static final String INVALID_FILTER_MESSAGE = "Invalid search filter";
    private final T clientContext;
    private final RsqlParserContext<T, R> parserContext;
    private final RSQLParser parser;
    private final List<String> filters;

    /**
     * Constructor
     *
     * @param clientContext A client context passed to the client in registered methods
     * @param parserContext The parser context
     */
    public RsqlParser(final T clientContext, final RsqlParserContext<T, R> parserContext) {
        this.clientContext = clientContext;
        this.parserContext = parserContext;
        parser = new RSQLParser(RsqlOperatorHelper.getSupportedOperators());
        filters = new ArrayList<>();
    }

    /**
     * Add RSQL string to parser.
     *
     * @param filter RSQL string
     * @return The parser
     */
    public RsqlParser<T, R> addFilter(final String filter) {
        filters.add(filter);
        return this;
    }

    /**
     * Execute the parser. Throws APIException if RSQL string is invalid.
     *
     * @return The parsed response (e.g. javax.persistence.criteria.Predicate)
     */
    @SuppressWarnings("PMD.PreserveStackTrace")
    public R execute() {
        final RsqlVisitor<T, R> visitor = new RsqlVisitor<>(parserContext);
        final List<R> parsedResponses = new ArrayList<>(filters.size());
        for (final String filter : filters) {
            try {
                final var rootNode = parser.parse(filter);
                parsedResponses.add(rootNode.accept(visitor, clientContext));
            } catch (RSQLParserException | IllegalArgumentException | ParserException e) {
                throw new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), INVALID_FILTER_MESSAGE);
            }
        }

        return parserContext.combineAnd(clientContext, parsedResponses);
    }
}
