package com.postnord.ndm.base.openapi;

import com.postnord.ndm.base.openapi.model.Item;
import com.postnord.ndm.base.openapi.model.ItemDetails;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.bind.Jsonb;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class PolymorphicDeserializationTest extends AbstractOpenApiTest {

    @Inject
    Jsonb jsonb;

    @Test
    void testDeserializationOfParentClassShouldSucceed() {
        final var json = jsonb.toJson(buildItem());
        assertTrue(json.contains("\"" + Item.OBJECT_TYPE_ITEM + "\""));
        assertItem(jsonb.fromJson(json, Item.class), Item.OBJECT_TYPE_ITEM);
    }

    @Test
    void testDeserializationOfChildClassShouldSucceed() {
        final var json = jsonb.toJson(buildItemDetails());
        assertTrue(json.contains("\"" + Item.OBJECT_TYPE_ITEM_DETAILS + "\""));
        final var item = jsonb.fromJson(json, Item.class);
        assertItem(item, Item.OBJECT_TYPE_ITEM_DETAILS);
        assertTrue(item instanceof ItemDetails);
        assertItemDetails((ItemDetails) item);
    }
}
