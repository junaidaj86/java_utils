package com.postnord.ndm.base.rsql_parser.utils;

import java.lang.reflect.Type;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class RsqlNode {
    String fieldName;
    Type fieldType;
    RsqlOperator operator;
    List<?> arguments;
}
