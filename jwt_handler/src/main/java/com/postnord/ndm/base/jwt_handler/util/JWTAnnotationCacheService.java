package com.postnord.ndm.base.jwt_handler.util;

import io.quarkus.cache.CacheResult;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@ApplicationScoped
public class JWTAnnotationCacheService {

    private final Set<Class<? extends Annotation>> jwtAnnotations = Set.of(DenyAll.class, PermitAll.class, RolesAllowed.class);

    @CacheResult(cacheName = "jwt-annotation-cache")
    Annotation getAnnotation(final Method resourceMethod) {
        final Annotation annotation = getAnnotation(resourceMethod.getDeclaredAnnotations());
        if (annotation != null) {
            return annotation;
        }

        return getAnnotation(resourceMethod.getDeclaringClass().getDeclaredAnnotations());
    }

    Annotation getAnnotation(final Annotation... declaredAnnotations) {
        final List<Annotation> annotations = Stream.of(declaredAnnotations)
                .filter(annotation -> jwtAnnotations.contains(annotation.annotationType()))
                .toList();
        if (annotations.isEmpty()) {
            return null;
        }
        return annotations.iterator().next();
    }
}
