package com.postnord.ndm.api.common.validation.validator;

import java.util.Iterator;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validation;

public abstract class ModelValidator<T> {

    protected abstract void throwError(String message);

    public void validate(final T obj) {

        final Set<ConstraintViolation<T>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(obj);

        if (!violations.isEmpty()) {

            final ConstraintViolation<T> cv = violations.iterator().next();

            // Find the leaf field causing the violation
            final Iterator<Path.Node> iterator = cv.getPropertyPath().iterator();
            final Path.Node lastNode = iterator.next();
            final String field = lastNode.getName();

            throwError("Illegal value in field: '" + field + "', reason: " + cv.getMessage());
        }
    }
}
