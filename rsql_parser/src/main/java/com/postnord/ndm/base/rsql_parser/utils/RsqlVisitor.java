package com.postnord.ndm.base.rsql_parser.utils;

import com.postnord.ndm.base.common_utils.utils.StringHelper;
import com.postnord.ndm.base.rsql_parser.RsqlParserContext;

import java.util.List;
import java.util.stream.Collectors;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class RsqlVisitor<T, R> implements RSQLVisitor<R, T> {
    private final RsqlParserContext<T, R> parserContext;

    public RsqlVisitor(final RsqlParserContext<T, R> parserContext) {
        this.parserContext = parserContext;
    }

    @Override
    public R visit(final AndNode node, final T clientContext) {
        return parserContext.combineAnd(clientContext, processNodes(node.getChildren(), clientContext));
    }

    @Override
    public R visit(final OrNode node, final T clientContext) {
        return parserContext.combineOr(clientContext, processNodes(node.getChildren(), clientContext));
    }

    @Override
    public R visit(final ComparisonNode node, final T clientContext) {
        final String fieldName = parseFieldName(node);
        return visit(
                parserContext.createNode(clientContext, fieldName, RsqlOperatorHelper.resolve(node.getOperator()), node.getArguments()),
                clientContext
        );
    }

    private R visit(final RsqlNode node, final T clientContext) {
        if (node.getOperator().equals(RsqlOperator.EQUAL) && node.getArguments().size() > 1) {
            // Needed to cover wildcards in enums
            return parserContext.processNode(clientContext, node.toBuilder().operator(RsqlOperator.IN).build());
        }
        if (node.getOperator().equals(RsqlOperator.NOT_EQUAL) && node.getArguments().size() > 1) {
            // Needed to cover wildcards in enums
            return parserContext.processNode(clientContext, node.toBuilder().operator(RsqlOperator.NOT_IN).build());
        }
        return parserContext.processNode(clientContext, node);
    }

    private List<R> processNodes(final List<Node> nodes, final T clientContext) {
        return nodes.stream().map(node -> node.accept(this, clientContext)).collect(Collectors.toList());
    }

    private String parseFieldName(final ComparisonNode node) {
        final String selector = node.getSelector();
        if (!parserContext.convertFieldNames()) {
            return selector;
        }
        return StringHelper.snakeToCamelCase(selector);
    }
}
