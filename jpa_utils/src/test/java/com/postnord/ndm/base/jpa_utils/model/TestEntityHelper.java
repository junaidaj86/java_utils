package com.postnord.ndm.base.jpa_utils.model;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;


public final class TestEntityHelper {
    private static final String TAG_1 = "tag_1";
    private static final String TAG_2 = "tag_2";
    private static final Status STATUS = Status.ACTIVE;

    public static TestEntity createTestEntity() {
        return new TestEntity();
    }

    public static TestEntityWithUUID createTestEntityWithUUID(final Instant... times) {
        return createTestEntityWithUUID("test", 1, STATUS, times);
    }

    public static TestEntityWithUUID createTestEntityWithUUID(final String name, final int number, final Status status,
                                                              final Instant... times) {
        final TestEntityWithUUID testEntity = new TestEntityWithUUID();
        testEntity.setName(name);
        testEntity.setNumber(number);
        testEntity.setType(TestType.TEST);
        testEntity.setListOfStrings(Arrays.asList(TAG_1, TAG_2));
        testEntity.setSetOfTimes(Set.of(times));
        testEntity.setStatus(status);
        return testEntity;
    }

    public static TestNamedEntity createTestNamedEntity(final String name, final String description, final Status status) {
        final TestNamedEntity testNamedEntity = new TestNamedEntity();
        testNamedEntity.setStatus(status);
        testNamedEntity.setName(name);
        testNamedEntity.setDescription(description);
        return testNamedEntity;
    }

    private TestEntityHelper() {
    }
}
