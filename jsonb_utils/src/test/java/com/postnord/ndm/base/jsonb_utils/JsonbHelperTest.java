package com.postnord.ndm.base.jsonb_utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import javax.json.Json;

class JsonbHelperTest {
    private static final String STRING_FIELD = "string_field";
    private static final String INTEGER_FIELD = "integer_field";
    private static final String LIST_FIELD = "list_field";
    private static final TestBean REFERENCE_BEAN = new TestBean.Builder().build();

    @Test
    void mergePatchShouldReplaceStringField() {
        final TestBean testBean = JsonbHelper.mergePatch(
                Json.createMergePatch(Json.createObjectBuilder().add(STRING_FIELD, "test").build()), REFERENCE_BEAN, TestBean.class
        );
        Assertions.assertNotNull(testBean);
        Assertions.assertEquals("test", testBean.getStringField());
        Assertions.assertEquals(REFERENCE_BEAN.getIntegerField(), testBean.getIntegerField());
        Assertions.assertEquals(REFERENCE_BEAN.getListField(), testBean.getListField());
    }

    @Test
    void mergePatchShouldRemoveStringField() {
        final TestBean testBean = JsonbHelper.mergePatch(
                Json.createMergePatch(Json.createObjectBuilder().addNull(STRING_FIELD).build()), REFERENCE_BEAN, TestBean.class
        );
        Assertions.assertNotNull(testBean);
        Assertions.assertNull(testBean.getStringField());
        Assertions.assertEquals(REFERENCE_BEAN.getIntegerField(), testBean.getIntegerField());
        Assertions.assertEquals(REFERENCE_BEAN.getListField(), testBean.getListField());
    }

    @Test
    void mergePatchShouldReplaceIntegerField() {
        final TestBean testBean = JsonbHelper.mergePatch(
                Json.createMergePatch(Json.createObjectBuilder().add(INTEGER_FIELD, 1).build()), REFERENCE_BEAN, TestBean.class
        );
        Assertions.assertNotNull(testBean);
        Assertions.assertEquals(REFERENCE_BEAN.getStringField(), testBean.getStringField());
        Assertions.assertEquals(1, testBean.getIntegerField());
        Assertions.assertEquals(REFERENCE_BEAN.getListField(), testBean.getListField());
    }

    @Test
    void mergePatchShouldRemoveIntegerField() {
        final TestBean testBean = JsonbHelper.mergePatch(
                Json.createMergePatch(Json.createObjectBuilder().addNull(INTEGER_FIELD).build()), REFERENCE_BEAN, TestBean.class
        );
        Assertions.assertNotNull(testBean);
        Assertions.assertEquals(REFERENCE_BEAN.getStringField(), testBean.getStringField());
        Assertions.assertNull(testBean.getIntegerField());
        Assertions.assertEquals(REFERENCE_BEAN.getListField(), testBean.getListField());
    }

    @Test
    void mergePatchShouldReplaceListField() {
        final TestBean testBean = JsonbHelper.mergePatch(
                Json.createMergePatch(
                        Json.createObjectBuilder().add(LIST_FIELD, Json.createArrayBuilder().add("test list")).build()),
                REFERENCE_BEAN,
                TestBean.class
        );
        Assertions.assertNotNull(testBean);
        Assertions.assertEquals(REFERENCE_BEAN.getStringField(), testBean.getStringField());
        Assertions.assertEquals(REFERENCE_BEAN.getIntegerField(), testBean.getIntegerField());
        Assertions.assertEquals(Collections.singletonList("test list"), testBean.getListField());
    }

    @Test
    void mergePatchShouldRemoveListField() {
        final TestBean testBean = JsonbHelper.mergePatch(
                Json.createMergePatch(Json.createObjectBuilder().addNull(LIST_FIELD).build()),
                REFERENCE_BEAN,
                TestBean.class
        );
        Assertions.assertNotNull(testBean);
        Assertions.assertEquals(REFERENCE_BEAN.getStringField(), testBean.getStringField());
        Assertions.assertEquals(REFERENCE_BEAN.getIntegerField(), testBean.getIntegerField());
        Assertions.assertNull(testBean.getListField());
    }
}
