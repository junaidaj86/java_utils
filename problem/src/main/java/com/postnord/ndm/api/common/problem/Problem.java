package com.postnord.ndm.api.common.problem;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import lombok.*;
import lombok.Builder.Default;

import java.net.URI;
import java.util.*;

@ToString
@Builder(buildMethodName = "buildInternal")
@Getter
public class Problem {

    @JsonbTransient
    private static final Set<String> RESERVED_PROPERTIES = new HashSet<>(Arrays.asList(
            "type", "title", "status", "detail", "instance"));
    @JsonbTransient
    private static final String UNSET = "UNSET";
    @Default
    @NonNull
    @JsonbProperty("type")
    private URI type = URI.create(UNSET);
    @Default
    @NonNull
    @JsonbProperty("title")
    private String title = UNSET;
    @Default
    @NonNull
    @JsonbProperty("status")
    private int status;
    @Default
    @NonNull
    @JsonbProperty("detail")
    private String detail = UNSET;
    @Default
    @NonNull
    @JsonbProperty("instance")
    private URI instance = URI.create(UNSET);
    @Singular //will also create an unmodifiable collection
    @NonNull
    @JsonbProperty("parameters")
    private Map<String, Object> parameters = new LinkedHashMap<>();

    public static class ProblemBuilder {

        private Problem obj;

        public Problem build() {
            obj = this.buildInternal();

            validate();

            return obj;
        }

        public void validate() {

            Objects.requireNonNull(obj.type, "type cannot be null");
            Objects.requireNonNull(obj.title, "title cannot be null");
            Objects.requireNonNull(obj.detail, "detail cannot be null");
            Objects.requireNonNull(obj.instance, "instance cannot be null");
            //check for null, empty is ok, not allowed values etc

            for (final String key : obj.parameters.keySet()) {
                if (RESERVED_PROPERTIES.contains(key)) {
                    throw new IllegalArgumentException(
                            "Property " + key + " is reserved, reserved properties are " + RESERVED_PROPERTIES);
                }
            }
        }
    }
}
