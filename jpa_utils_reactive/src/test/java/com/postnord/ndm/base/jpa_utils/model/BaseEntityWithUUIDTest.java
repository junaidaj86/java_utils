package com.postnord.ndm.base.jpa_utils.model;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;
import com.postnord.ndm.base.test_utils.postgres.PostgresResourceLifecycleManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(PostgresResourceLifecycleManager.class)
@EnabledIfSystemProperty(named = "env.has.postgres", matches = "true")
class BaseEntityWithUUIDTest extends BaseTest {

    @Test
    void testPersistBaseEntityWithUUIDShouldSucceed() {
        vertx.runOnContext(() -> {
            final TestEntityWithUUID testEntity = TestEntityHelper.createTestEntityWithUUID(InstantHelper.getInstantAndStripNano());
            session.withTransaction(tx -> session.persist(testEntity));
            session.find(TestEntityWithUUID.class, testEntity.getId()).invoke(resultEntity -> {
                assertNotNull(resultEntity);
                assertEquals(testEntity.getId(), resultEntity.getId());
                assertNotNull(resultEntity.getCreatedAt());
                assertNotNull(resultEntity.getUpdatedAt());
                assertEquals(0, resultEntity.getVersion());
            });
        });
    }
}
