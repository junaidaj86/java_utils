package com.postnord.ndm.base.logger.util;


import com.postnord.ndm.base.logger.NdmPrivacyTag;
import com.postnord.ndm.base.logger.NdmPrivacyType;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.*;

@UtilityClass
public class PrivacyTagHelper {

    @SneakyThrows
    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    public Map<String, Object> toExtraData(final Object o, final NdmPrivacyType defaultType) {
        final var map = new HashMap<String, Object>();
        for (final Field f : getAllFields(o.getClass())) {
            f.setAccessible(true);
            final var fieldObject = f.get(o);
            f.setAccessible(false);
            final var privacyTag = Arrays.stream(f.getAnnotationsByType(NdmPrivacyTag.class)).findAny();
            map.put(f.getName(), privacyTag.map(tag -> {
                if (defaultType != null && NdmPrivacyType.NOT_SET.equals(tag.value())) {
                    return defaultType.wrap(fieldObject);
                }
                return (Object) tag.value().wrap(fieldObject);
            }).orElse(fieldObject));
        }
        return Map.of(o.getClass().getSimpleName(), map);
    }

    private List<Field> getAllFields(final Class<?> aClass) {
        final var fields = new ArrayList<>(Arrays.asList(aClass.getDeclaredFields()));
        final Class<?> superClass = aClass.getSuperclass();
        if (superClass != null) {
            fields.addAll(getAllFields(superClass));
        }
        return fields;
    }

    public Map<String, Object> toExtraData(final Object o) {
        return toExtraData(o, null);
    }
}