package com.postnord.ndm.base.jpa_utils.model;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;
import com.postnord.ndm.base.jpa_utils.utils.PostgresTestResource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(PostgresTestResource.class)
@EnabledIfSystemProperty(named = "env.has.postgres", matches = "true")
class BaseEntityWithUUIDTest {

    @Inject
    EntityManager entityManager;

    @BeforeEach
    @Transactional
    public void setUp() {
        entityManager.createQuery("select te from " + TestEntityWithUUID.class.getSimpleName() + " te", TestEntityWithUUID.class)
                .getResultList().forEach(entityManager::remove);
    }

    @Test
    @Transactional
    void testPersistBaseEntityWithUUIDShouldSucceed() {
        final TestEntityWithUUID testEntity = TestEntityHelper.createTestEntityWithUUID(InstantHelper.getInstantAndStripNano());
        entityManager.persist(testEntity);
        entityManager.flush();
        final TestEntityWithUUID resultEntity = entityManager.find(TestEntityWithUUID.class, testEntity.getId());

        assertNotNull(resultEntity);
        assertEquals(testEntity.getId(), resultEntity.getId());
        assertNotNull(resultEntity.getCreatedAt());
        assertNotNull(resultEntity.getUpdatedAt());
        assertEquals(0, resultEntity.getVersion());
    }
}
