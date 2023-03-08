package com.postnord.ndm.base.jpa_utils.model;

import com.postnord.ndm.base.jpa_utils.utils.PostgresTestResource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(PostgresTestResource.class)
@EnabledIfSystemProperty(named = "env.has.postgres", matches = "true")
class BaseEntityTest {

    @Inject
    EntityManager entityManager;
    private static final String TEST = "test";

    @BeforeEach
    @Transactional
    public void setUp() {
        entityManager.createQuery("select te from " + TestEntity.class.getSimpleName() + " te", TestEntity.class)
                .getResultList().forEach(entityManager::remove);
    }

    @Test
    @Transactional
    void persistBaseEntityShouldSucceed() {
        final TestEntity testEntity = TestEntityHelper.createTestEntity();
        testEntity.setId(TEST);
        testEntity.setStatus(Status.ACTIVE);
        entityManager.persist(testEntity);
        entityManager.flush();
        final TestEntity resultEntity = entityManager.find(TestEntity.class, TEST);

        assertNotNull(resultEntity);
        assertEquals(TEST, resultEntity.getId());
        assertNotNull(resultEntity.getCreatedAt());
        assertNotNull(resultEntity.getUpdatedAt());
        assertEquals(0, resultEntity.getVersion());
        assertEquals(Status.ACTIVE, resultEntity.getStatus());
    }

    @Test
    void persistBaseEntityShouldFailDueToNullStatus() {
        final TestEntity testEntity = TestEntityHelper.createTestEntity();
        testEntity.setId(TEST);
        final Exception exception = assertThrows(Exception.class, () -> persistWrapper(testEntity));
        assertTrue(getRootCause(exception).getMessage().contains("null value in column \"status\" violates not-null constraint"));
    }

    @Test
    @Transactional
    void persistBaseEntityShouldSucceedWithTerminatedStatus() {
        final TestEntity testEntity = TestEntityHelper.createTestEntity();
        testEntity.setId(TEST);
        testEntity.setStatus(Status.TERMINATED);
        entityManager.persist(testEntity);
        entityManager.flush();
        final TestEntity resultEntity = entityManager.find(TestEntity.class, TEST);

        assertNotNull(resultEntity);
        assertEquals(TEST, resultEntity.getId());
        assertNotNull(resultEntity.getCreatedAt());
        assertNotNull(resultEntity.getUpdatedAt());
        assertEquals(0, resultEntity.getVersion());
        assertEquals(Status.TERMINATED, resultEntity.getStatus());
    }

    @Transactional
    void persistWrapper(final TestEntity testEntity) {
        entityManager.persist(testEntity);
    }

    private static Throwable getRootCause(final Throwable throwable) {
        if (throwable.getCause() == null) {
            return throwable;
        }
        return getRootCause(throwable.getCause());
    }


    /**
     * Test case to check Entity is updated using 'merge' method
     */
    @Test
    @Transactional
    void updateBaseEntityUsingMergeShouldSucceed() throws InterruptedException {
        final TestEntity testEntity = TestEntityHelper.createTestEntity();
        testEntity.setId(TEST);
        testEntity.setStatus(Status.ACTIVE);
        entityManager.persist(testEntity);
        entityManager.flush();

        //After 1000 millisecond try to update the record so that updated dt time > created dt time
        Thread.sleep(1000);

        final TestEntity resultEntity = entityManager.find(TestEntity.class, TEST);
        resultEntity.setStatus(Status.INACTIVE);
        entityManager.merge(resultEntity);
        entityManager.flush();
        assertNotNull(resultEntity);
        assertEquals(TEST, resultEntity.getId());
        assertNotNull(resultEntity.getUpdatedAt());
        assertEquals(1, resultEntity.getVersion());
        assertEquals(Status.INACTIVE, resultEntity.getStatus());
        assertTrue(resultEntity.getUpdatedAt().isAfter(resultEntity.getCreatedAt()));

    }
}
