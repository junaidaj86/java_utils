package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmField;
import com.postnord.ndm.api.common.validation.constraints.NdmJsonMergePatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.json.JsonArray;
import jakarta.json.JsonMergePatch;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NdmJsonMergePatchValidator implements ConstraintValidator<NdmJsonMergePatch, JsonMergePatch> {

    private Set<NdmField> fields;
    private String message = "valid fields: ";
    private boolean nullAllowed;
    private boolean emptyAllowed;

    @Override
    public void initialize(final NdmJsonMergePatch constraintAnnotation) {
        nullAllowed = constraintAnnotation.nullAllowed();
        emptyAllowed = constraintAnnotation.emptyAllowed();
        fields = Arrays.stream(constraintAnnotation.fields()).collect(Collectors.toUnmodifiableSet());
        message += "{" + Arrays.stream(constraintAnnotation.fields()).map(NdmJsonMergePatchValidator::toString).collect(Collectors.joining(",")) + "}";
    }

    @Override
    public boolean isValid(final JsonMergePatch value, final ConstraintValidatorContext context) {
        if (value == null) {
            return nullAllowed;
        }
        if (!value.toJsonValue().getValueType().equals(JsonValue.ValueType.OBJECT)) {
            return false;
        }
        final JsonObject jsonObject = value.toJsonValue().asJsonObject();
        if (jsonObject.isEmpty()) {
            return emptyAllowed;
        }

        if (NdmJsonMergePatch.DEFAULT_MESSAGE.equals(context.getDefaultConstraintMessageTemplate())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }

        return isValidObject(jsonObject, Collections.emptyList());
    }

    private boolean isValidObject(final JsonObject jsonObject, final List<String> path) {
        return jsonObject.entrySet().stream().allMatch(entry -> {
            if (!isValidField(entry.getKey(), entry.getValue().getValueType().equals(JsonValue.ValueType.ARRAY), path)) {
                return false;
            }
            if (entry.getValue().getValueType().equals(JsonValue.ValueType.ARRAY)) {
                return isValidArray(entry.getValue().asJsonArray(), appendPath(path, entry.getKey()));
            }
            if (!entry.getValue().getValueType().equals(JsonValue.ValueType.OBJECT)) {
                return true;
            }
            return isValidObject(entry.getValue().asJsonObject(), appendPath(path, entry.getKey()));
        });
    }

    private boolean isValidArray(final JsonArray jsonArray, final List<String> path) {
        return jsonArray.stream().allMatch(value ->
                !value.getValueType().equals(JsonValue.ValueType.OBJECT) || isValidObject(value.asJsonObject(), path));
    }

    private boolean isValidField(final String name, final boolean array, final List<String> path) {
        return fields.stream().anyMatch(field ->
                Objects.equals(field.name(), name) && Arrays.asList(field.path()).equals(path) && field.isArray() == array);
    }

    private static List<String> appendPath(final List<String> oldPath, final String entry) {
        final List<String> path = new ArrayList<>(oldPath);
        path.add(entry);

        return path;
    }

    private static String toString(final NdmField field) {
        final StringBuilder stringBuilder = new StringBuilder("'");
        if (field.path().length == 0) {
            stringBuilder.append(field.name());
        } else {
            stringBuilder.append(String.join(".", appendPath(Arrays.asList(field.path()), field.name())));
        }
        stringBuilder.append('\'');

        return stringBuilder.toString();
    }
}