package com.postnord.ndm.base.rsql_parser.utils;

import lombok.Value;

@Value
class EntityField {
    Class<?> entityType;
    String fieldName;
}
