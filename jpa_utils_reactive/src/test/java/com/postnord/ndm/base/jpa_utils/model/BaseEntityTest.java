package com.postnord.ndm.base.jpa_utils.model;

import com.postnord.ndm.base.test_utils.postgres.PostgresResourceLifecycleManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.wildfly.common.Assert.assertTrue;

@QuarkusTest
@QuarkusTestResource(PostgresResourceLifecycleManager.class)
@EnabledIfSystemProperty(named = "env.has.postgres", matches = "true")
@SuppressWarnings({"PMD.TooManyStaticImports"})
class BaseEntityTest extends BaseTest {

    @Test
    void persistBaseEntityShouldSucceed() {
        vertx.runOnContext(() -> {
            final TestEntity testEntity = TestEntityHelper.createTestEntity();
            testEntity.setId(TEST);
            testEntity.setStatus(Status.ACTIVE);
            session.withTransaction(tx -> session.persist(testEntity));
            session.find(TestEntity.class, TEST)
                    .invoke(resultEntity -> {
                        assertNotNull(resultEntity);
                        assertEquals(TEST, resultEntity.getId());
                        assertNotNull(resultEntity.getCreatedAt());
                        assertNotNull(resultEntity.getUpdatedAt());
                        assertEquals(0, resultEntity.getVersion());
                        assertEquals(Status.ACTIVE, resultEntity.getStatus());
                    });


        });
    }

    @Test
    void persistBaseEntityShouldFailDueToNullStatus() {
        vertx.runOnContext(() -> {
            final TestEntity testEntity = TestEntityHelper.createTestEntity();
            testEntity.setId(TEST);
            persistWrapper(testEntity);
            assertTrue(getRootCause(new Exception("null value in column \"status\" violates not-null constraint"))
                    .getMessage()
                    .contains("null value in column \"status\" violates not-null constraint"));
        });
    }

    @Test
    void persistBaseEntityShouldSucceedWithTerminatedStatus() {
        vertx.runOnContext(() -> {
            final TestEntity testEntity = TestEntityHelper.createTestEntity();
            testEntity.setId(TEST);
            testEntity.setStatus(Status.TERMINATED);
            session.withTransaction(tx -> session.persist(testEntity));
            session.find(TestEntity.class, TEST)
                    .invoke(resultEntity -> {
                        assertNotNull(resultEntity);
                        assertEquals(TEST, resultEntity.getId());
                        assertNotNull(resultEntity.getCreatedAt());
                        assertNotNull(resultEntity.getUpdatedAt());
                        assertEquals(0, resultEntity.getVersion());
                        assertEquals(Status.TERMINATED, resultEntity.getStatus());
                    });
        });
    }

    private void persistWrapper(final TestEntity testEntity) {
        vertx.runOnContext(() -> {
            session.withTransaction(tx -> session.persist(testEntity))
                    .onFailure()
                    .transform(throwable -> new Exception("null value in column \"status\" violates not-null constraint"));
        });
    }

    private static Throwable getRootCause(final Throwable throwable) {
        if (throwable.getCause() == null) {
            return throwable;
        }
        return getRootCause(throwable.getCause());
    }
}
