package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmField;
import com.postnord.ndm.api.common.validation.constraints.NdmJsonMergePatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import jakarta.json.Json;
import jakarta.json.JsonMergePatch;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SuppressWarnings("PMD.TooManyStaticImports")
@ExtendWith(MockitoExtension.class)
class NdmJsonMergePatchValidatorTest {
    private static final String JSON_MERGE_PATCH = "jsonMergePatch";
    private static final String FIELD_ALLOWED = "allowed";
    private static final String FIELD_ALSO_ALLOWED = "alsoAllowed";
    private static final String FIELD_ALLOWED_ARRAY = "allowedArray";
    private static final String FIELD_NESTED_OBJECT = "nestedObject";
    private static final String FIELD_NESTED_ALLOWED = "nestedAllowed";
    private static final String ORIG = "orig";
    private static final String EDIT = "edit";

    private NdmJsonMergePatchValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder mockConstraintViolationBuilder;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        final class SampleClass {
            @NdmJsonMergePatch(fields = {
                    @NdmField(name = FIELD_ALLOWED),
                    @NdmField(name = FIELD_ALSO_ALLOWED),
                    @NdmField(name = FIELD_ALLOWED_ARRAY, isArray = true),
                    @NdmField(name = FIELD_NESTED_OBJECT),
                    @NdmField(name = FIELD_NESTED_ALLOWED, path = {FIELD_NESTED_OBJECT})
            })
            public JsonMergePatch jsonMergePatch;
        }
        final NdmJsonMergePatch annotation = SampleClass.class.getField(JSON_MERGE_PATCH).getAnnotation(NdmJsonMergePatch.class);
        cut = new NdmJsonMergePatchValidator();
        cut.initialize(annotation);
    }

    @Test
    void whenValidatorIsInitializedThenAnnotationIsAccepted() throws NoSuchFieldException {
        final class SampleClass {
            @NdmJsonMergePatch(fields = {@NdmField(name = "allowed1"), @NdmField(name = "allowed2")})
            public JsonMergePatch jsonMergePatch;
        }
        final NdmJsonMergePatch annotation = SampleClass.class.getField(JSON_MERGE_PATCH).getAnnotation(NdmJsonMergePatch.class);
        assertDoesNotThrow(() -> cut.initialize(annotation), "exception should not occur during initialization");
    }

    @Test
    void whenPatchHasSupportedFieldThenItIsConsideredValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmJsonMergePatch.DEFAULT_MESSAGE);

        assertTrue(cut.isValid(createJsonMergePatch(FIELD_ALLOWED), mockConstraintValidatorContext));
    }

    @Test
    void whenPatchHasSupportedFieldWithNullValueThenItIsConsideredValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmJsonMergePatch.DEFAULT_MESSAGE);

        assertTrue(cut.isValid(createJsonMergePatch(true, FIELD_ALLOWED), mockConstraintValidatorContext));
    }

    @Test
    void whenPatchHasUnsupportedFieldThenItIsConsideredInvalid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmJsonMergePatch.DEFAULT_MESSAGE);

        assertFalse(cut.isValid(createJsonMergePatch("notInEnumString"), mockConstraintValidatorContext));
    }

    // CPD-OFF
    @Test
    void whenPatchHasSupportedArrayThenItIsConsideredValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmJsonMergePatch.DEFAULT_MESSAGE);

        final JsonMergePatch jsonMergePatch = Json.createMergeDiff(
                Json.createObjectBuilder().add(FIELD_ALLOWED_ARRAY, Json.createArrayBuilder().add(ORIG).build()).build(),
                Json.createObjectBuilder().add(FIELD_ALLOWED_ARRAY, Json.createArrayBuilder().add(EDIT).build()).build()
        );

        assertTrue(cut.isValid(jsonMergePatch, mockConstraintValidatorContext));
    }

    @Test
    void whenPatchHasUnsupportedArrayThenItIsConsideredInvalid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmJsonMergePatch.DEFAULT_MESSAGE);

        final JsonMergePatch jsonMergePatch = Json.createMergeDiff(
                Json.createObjectBuilder().add(FIELD_ALLOWED, Json.createArrayBuilder().add(ORIG).build()).build(),
                Json.createObjectBuilder().add(FIELD_ALLOWED, Json.createArrayBuilder().add(EDIT).build()).build()
        );

        assertFalse(cut.isValid(jsonMergePatch, mockConstraintValidatorContext));
    }

    @Test
    void whenPatchHasSupportedNestedFieldThenItIsConsideredValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmJsonMergePatch.DEFAULT_MESSAGE);

        final JsonMergePatch jsonMergePatch = Json.createMergeDiff(
                Json.createObjectBuilder().add(FIELD_NESTED_OBJECT, Json.createObjectBuilder().add(FIELD_NESTED_ALLOWED, ORIG).build()).build(),
                Json.createObjectBuilder().add(FIELD_NESTED_OBJECT, Json.createObjectBuilder().add(FIELD_NESTED_ALLOWED, EDIT).build()).build()
        );

        assertTrue(cut.isValid(jsonMergePatch, mockConstraintValidatorContext));
    }

    @Test
    void whenPatchHasSupportedNestedFieldWithNullValueThenItIsConsideredValid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmJsonMergePatch.DEFAULT_MESSAGE);

        final JsonMergePatch jsonMergePatch = Json.createMergeDiff(
                Json.createObjectBuilder().add(FIELD_NESTED_OBJECT, Json.createObjectBuilder().add(FIELD_NESTED_ALLOWED, ORIG).build()).build(),
                Json.createObjectBuilder().add(FIELD_NESTED_OBJECT, Json.createObjectBuilder().addNull(FIELD_NESTED_ALLOWED).build()).build()
        );

        assertTrue(cut.isValid(jsonMergePatch, mockConstraintValidatorContext));
    }

    @Test
    void whenPatchHasUnsupportedNestedFieldThenItIsConsideredInvalid() {

        when(mockConstraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(mockConstraintViolationBuilder);
        when(mockConstraintValidatorContext.getDefaultConstraintMessageTemplate()).thenReturn(NdmJsonMergePatch.DEFAULT_MESSAGE);

        final JsonMergePatch jsonMergePatch = Json.createMergeDiff(
                Json.createObjectBuilder().add(FIELD_NESTED_OBJECT, Json.createObjectBuilder().add("nestedInvalid", ORIG).build()).build(),
                Json.createObjectBuilder().add(FIELD_NESTED_OBJECT, Json.createObjectBuilder().add("nestedInvalid", EDIT).build()).build()
        );

        assertFalse(cut.isValid(jsonMergePatch, mockConstraintValidatorContext));
    }
    // CPD-ON

    @Test
    void whenPatchIsNullAndNullNotAllowedThenItIsConsideredInvalid() {
        assertFalse(cut.isValid(null, mockConstraintValidatorContext));
    }

    @Test
    void whenPatchIsEmptyAndEmptyNotAllowedThenItIsConsideredInvalid() {
        assertFalse(cut.isValid(createJsonMergePatch(), mockConstraintValidatorContext));
    }

    @Test
    void whenPatchIsNullAndNullAllowedThenItIsConsideredValid() throws NoSuchFieldException {
        final class SampleClass {
            @NdmJsonMergePatch(nullAllowed = true, fields = {@NdmField(name = "allowed5"), @NdmField(name = "allowed6")})
            public JsonMergePatch jsonMergePatch;
        }
        final NdmJsonMergePatch annotation = SampleClass.class.getField(JSON_MERGE_PATCH).getAnnotation(NdmJsonMergePatch.class);
        cut.initialize(annotation);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext));
    }

    @Test
    void whenPatchIsEmptyAndEmptyAllowedThenItIsConsideredValid() throws NoSuchFieldException {
        final class SampleClass {
            @NdmJsonMergePatch(emptyAllowed = true, fields = {@NdmField(name = "allowed5"), @NdmField(name = "allowed6")})
            public JsonMergePatch jsonMergePatch;
        }
        final NdmJsonMergePatch annotation = SampleClass.class.getField(JSON_MERGE_PATCH).getAnnotation(NdmJsonMergePatch.class);
        cut.initialize(annotation);

        assertTrue(cut.isValid(createJsonMergePatch(), mockConstraintValidatorContext));
    }

    static JsonMergePatch createJsonMergePatch(final String... fields) {
        return createJsonMergePatch(false, fields);
    }

    static JsonMergePatch createJsonMergePatch(final boolean patchNull, final String... fields) {
        final JsonObjectBuilder sourceObjectBuilder = Json.createObjectBuilder();
        Arrays.stream(fields).forEach(field -> sourceObjectBuilder.add(field, field));
        final JsonObjectBuilder targetObjectBuilder = Json.createObjectBuilder();
        Arrays.stream(fields).forEach(field -> {
            if (patchNull) {
                targetObjectBuilder.addNull(field);
            } else {
                targetObjectBuilder.add(field, "patched-" + field);
            }
        });

        return Json.createMergeDiff(sourceObjectBuilder.build(), targetObjectBuilder.build());
    }
}
