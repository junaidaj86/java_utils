package com.postnord.ndm.base.rsql_parser.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

final class EntityFieldCache {
    private static final Map<EntityField, Type> ENTITY_FIELD_TO_TYPE_MAP = new ConcurrentHashMap<>();

    static Type getFieldType(final Class<?> entityType, final String fieldName) {
        final EntityField entityField = new EntityField(entityType, fieldName);
        return ENTITY_FIELD_TO_TYPE_MAP.computeIfAbsent(entityField, EntityFieldCache::getFieldType);
    }

    private static Type getFieldType(final EntityField entityField) {
        final Field field = getField(entityField.getEntityType(), entityField.getFieldName());
        if (Collection.class.isAssignableFrom(field.getType())) {
            final ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
            return stringListType.getActualTypeArguments()[0];
        }
        return field.getType();
    }

    private static Field getField(final Class<?> entityType, final String fieldName) {
        final Optional<Field> fieldOptional = Stream.of(entityType.getDeclaredFields()).filter(f -> f.getName().equals(fieldName)).findAny();
        if (fieldOptional.isPresent()) {
            return fieldOptional.get();
        }
        if (entityType.getSuperclass().equals(Object.class)) {
            throw new ParserException("Entity " + entityType.getSimpleName() + " does not have field '" + fieldName + "'");
        }
        return getField(entityType.getSuperclass(), fieldName);
    }

    private EntityFieldCache() {
    }
}
