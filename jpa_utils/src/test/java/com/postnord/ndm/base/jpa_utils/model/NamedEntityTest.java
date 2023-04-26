package com.postnord.ndm.base.jpa_utils.model;

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
class NamedEntityTest {

    @Inject
    EntityManager entityManager;

    @BeforeEach
    @Transactional
    public void setUp() {
        entityManager.createQuery("select tne from " + TestNamedEntity.class.getSimpleName() + " tne", TestNamedEntity.class)
                .getResultList().forEach(entityManager::remove);
    }

    @Test
    @Transactional
    void testPersistNamedEntityShouldSucceed() {
        final TestNamedEntity testNamedEntity = TestEntityHelper.createTestNamedEntity("entityName", "entityDescription", Status.INACTIVE);
        entityManager.persist(testNamedEntity);
        entityManager.flush();
        final TestNamedEntity resultNamedEntity = entityManager.find(TestNamedEntity.class, testNamedEntity.getId());

        assertNotNull(resultNamedEntity);
        assertEquals(resultNamedEntity.getId(), resultNamedEntity.getId());
        assertNotNull(resultNamedEntity.getCreatedAt());
        assertNotNull(resultNamedEntity.getUpdatedAt());
        assertEquals(0, resultNamedEntity.getVersion());
        assertEquals("entityName", resultNamedEntity.getName());
        assertEquals("entityDescription", resultNamedEntity.getDescription());
        assertEquals(Status.INACTIVE, resultNamedEntity.getStatus());
    }
}
