package com.postnord.ndm.base.openapi;

import com.postnord.ndm.base.openapi.model.Item;
import com.postnord.ndm.base.openapi.model.ItemDetails;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
abstract class AbstractOpenApiTest {

    static final String NAME = "test";

    static final String TAG = "test:tag";

    static final String DESCRIPTION = "description";

    static final String ADDITIONAL_DATA_KEY = "key";

    static final String ADDITIONAL_DATA_VALUE = "value";

    static final UUID ID = UUID.randomUUID();

    static final Instant CREATED_AT = Instant.now();

    static final Instant UPDATED_AT = CREATED_AT.plusSeconds(5);

    Item buildItem() {
        final var item = new Item();
        setItemFields(item);
        return item;
    }

    ItemDetails buildItemDetails() {
        final var itemDetails = new ItemDetails()
                .id(ID)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .putAdditionalDataItem(ADDITIONAL_DATA_KEY, ADDITIONAL_DATA_VALUE);
        setItemFields(itemDetails);
        return itemDetails;
    }

    void setItemFields(final Item item) {
        item.name(NAME)
                .addPropertiesItem(Item.PROPERTIES_CREATED)
                .addPropertiesItem(Item.PROPERTIES_APPROVED)
                .addTagsItem(TAG)
                .description(DESCRIPTION)
                .status(Item.STATUS_ACTIVE);
    }

    void assertItem(final Item item, final String objectType) {
        assertNotNull(item);
        assertEquals(objectType, item.getObjectType());
        assertEquals(NAME, item.getName());
        assertEquals(2, item.getProperties().size());
        assertEquals(1, item.getTags().size());
        assertEquals(Item.STATUS_ACTIVE, item.getStatus());
    }

    void assertItemDetails(final ItemDetails itemDetails) {
        assertEquals(ID, itemDetails.getId());
        assertEquals(CREATED_AT, itemDetails.getCreatedAt());
        assertEquals(UPDATED_AT, itemDetails.getUpdatedAt());
        assertEquals(1, itemDetails.getAdditionalData().size());
        assertEquals(ADDITIONAL_DATA_VALUE, itemDetails.getAdditionalData().get(ADDITIONAL_DATA_KEY));
    }
}
