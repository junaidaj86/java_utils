package com.postnord.ndm.base.jpa_utils.model;

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
class NamedEntityTest extends BaseTest {

    @Test
    void testPersistNamedEntityShouldSucceed() {
        vertx.runOnContext(() -> {
            final TestNamedEntity testNamedEntity = TestEntityHelper.createTestNamedEntity("entityName", "entityDescription", Status.INACTIVE);
            session.withTransaction(tx -> session.persist(testNamedEntity));
            session.find(TestNamedEntity.class, testNamedEntity.getId())
                    .invoke(resultNamedEntity -> {
                        assertNotNull(resultNamedEntity);
                        assertEquals(resultNamedEntity.getId(), resultNamedEntity.getId());
                        assertNotNull(resultNamedEntity.getCreatedAt());
                        assertNotNull(resultNamedEntity.getUpdatedAt());
                        assertEquals(0, resultNamedEntity.getVersion());
                        assertEquals("entityName", resultNamedEntity.getName());
                        assertEquals("entityDescription", resultNamedEntity.getDescription());
                        assertEquals(Status.INACTIVE, resultNamedEntity.getStatus());
                    });

        });
    }
}
