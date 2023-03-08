package com.postnord.ndm.base.logger;

import com.postnord.ndm.base.logger.model.LogRecord;
import com.postnord.ndm.base.logger.util.PrivacyTagHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;


class PrivacyTagHelperTest {

    public static final String TAGGING_DOES_NOT_MATCH = "Tagging does NOT match!";
    public static final String SHOULD_NOT_BE_WRAPPED_WHEN_NOT_TAGGED = "Field is not tagged, it should NOT be wrapped";
    public static final String SHOULD_NOT_BE_WRAPPED_WHEN_NOT_SET = "Field tagged with NOT_SET should NOT be wrapped";
    public static final String SHOULD_APPLY_DEFAULT_TAG_WHEN_NOT_SET = "Field tagged with NOT_SET should apply default tag (ADDRESS)";

    // CPD-OFF
    @Test
    void testExtraDataWithNoDefaultTag() {
        final var testObject = new PrivacyTaggedObject("123", "456", 789L);

        final var logRecord = LogRecord.builder()
                .message("tagAnnotatedFieldShouldBeWrapped")
                .extraData(PrivacyTagHelper.toExtraData(testObject))
                .build();
        @SuppressWarnings("unchecked") final var extraData =
                (Map<String, Object>) logRecord.getExtraData().get(testObject.getClass().getSimpleName());

        Assertions.assertNotNull(logRecord);
        Assertions.assertNotNull(extraData);
        Assertions.assertEquals(NdmPrivacyType.IMSI.wrap(testObject.getTaggedImsiField()),
                extraData.get("taggedImsiField"), TAGGING_DOES_NOT_MATCH);
        Assertions.assertEquals(NdmPrivacyType.IMSI.wrap(testObject.getTaggedImsiParentField()),
                extraData.get("taggedImsiParentField"), TAGGING_DOES_NOT_MATCH);
        Assertions.assertEquals(testObject.getTaggedNonSetField(),
                extraData.get("taggedNonSetField"), SHOULD_NOT_BE_WRAPPED_WHEN_NOT_SET);
        Assertions.assertEquals(testObject.getTaggedNonSetParentField(),
                extraData.get("taggedNonSetParentField"), SHOULD_NOT_BE_WRAPPED_WHEN_NOT_SET);
        Assertions.assertEquals(testObject.getNonTaggedField(),
                extraData.get("nonTaggedField"), SHOULD_NOT_BE_WRAPPED_WHEN_NOT_TAGGED);
        Assertions.assertEquals(testObject.getNonTaggedParentField(),
                extraData.get("nonTaggedParentField"), SHOULD_NOT_BE_WRAPPED_WHEN_NOT_TAGGED);
    }

    @Test
    void testExtraDataWithDefaultTag() {
        final var testObject = new PrivacyTaggedObject("123", "456", 789L);

        final var logRecord = LogRecord.builder()
                .message("tagAnnotatedFieldShouldBeWrapped")
                .extraData(PrivacyTagHelper.toExtraData(testObject, NdmPrivacyType.ADDRESS))
                .build();
        @SuppressWarnings("unchecked") final var extraData =
                (Map<String, Object>) logRecord.getExtraData().get(testObject.getClass().getSimpleName());

        Assertions.assertNotNull(logRecord);
        Assertions.assertNotNull(extraData);
        Assertions.assertEquals(NdmPrivacyType.IMSI.wrap(testObject.getTaggedImsiField()),
                extraData.get("taggedImsiField"), TAGGING_DOES_NOT_MATCH);
        Assertions.assertEquals(NdmPrivacyType.IMSI.wrap(testObject.getTaggedImsiParentField()),
                extraData.get("taggedImsiParentField"), TAGGING_DOES_NOT_MATCH);
        Assertions.assertEquals(NdmPrivacyType.ADDRESS.wrap(testObject.getTaggedNonSetField()),
                extraData.get("taggedNonSetField"), SHOULD_APPLY_DEFAULT_TAG_WHEN_NOT_SET);
        Assertions.assertEquals(NdmPrivacyType.ADDRESS.wrap(testObject.getTaggedNonSetParentField()),
                extraData.get("taggedNonSetParentField"), SHOULD_APPLY_DEFAULT_TAG_WHEN_NOT_SET);
        Assertions.assertEquals(testObject.getNonTaggedField(),
                extraData.get("nonTaggedField"), SHOULD_NOT_BE_WRAPPED_WHEN_NOT_TAGGED);
        Assertions.assertEquals(testObject.getNonTaggedParentField(),
                extraData.get("nonTaggedParentField"), SHOULD_NOT_BE_WRAPPED_WHEN_NOT_TAGGED);
    }

    @Test
    void extraDataShouldContainParentFields() {
        final var taggedObject = new PrivacyTaggedObject("123", "456", 789L);
        @SuppressWarnings("unchecked") final var extraData =
                (Map<String, Object>) PrivacyTagHelper.toExtraData(taggedObject).get(taggedObject.getClass().getSimpleName());

        final int nrOfFields = PrivacyTaggedObject.class.getDeclaredFields().length + PrivacyTaggedParentObject.class.getDeclaredFields().length;
        Assertions.assertEquals(nrOfFields, extraData.size());
    }
}
