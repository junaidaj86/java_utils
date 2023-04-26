package com.postnord.ndm.base.jpa_utils.rsql;

import com.postnord.ndm.api.common.exception.APIException;
import com.postnord.ndm.base.common_utils.utils.InstantHelper;
import com.postnord.ndm.base.jpa_utils.model.TestEntity;
import com.postnord.ndm.base.jpa_utils.model.TestEntityHelper;
import com.postnord.ndm.base.jpa_utils.model.TestEntityWithUUID;
import com.postnord.ndm.base.rsql_parser.utils.RsqlNode;
import com.postnord.ndm.base.rsql_parser.utils.RsqlOperator;
import com.postnord.ndm.base.test_utils.postgres.PostgresResourceLifecycleManager;

import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

@QuarkusTest
@QuarkusTestResource(PostgresResourceLifecycleManager.class)
@EnabledIfSystemProperty(named = "env.has.postgres", matches = "true")
class RsqlToPredicateParserTest {

    private static final Instant NOW = InstantHelper.getInstantAndStripNano();
    private static final String INSTANT_NOW = InstantHelper.getStringFromInstant(NOW);

    @Inject
    Mutiny.SessionFactory mutinySessionFactory;

    private Mutiny.Session session;

    @Inject
    Vertx vertx;

    private CriteriaQuery<TestEntityWithUUID> criteriaQuery;
    private Root<TestEntityWithUUID> root;
    private TestEntityWithUUID testEntity;

    @BeforeEach
    public void setUp() {
        vertx.runOnContext(() -> {
            criteriaQuery = mutinySessionFactory.getCriteriaBuilder().createQuery(TestEntityWithUUID.class);
            root = criteriaQuery.from(TestEntityWithUUID.class);
            session = (Mutiny.Session) mutinySessionFactory.openSession();
            session.createQuery("select te from " + TestEntity.class.getSimpleName() + " te", TestEntity.class)
                    .getResultList()
                    .invoke(list -> list.forEach(entity -> session.remove(entity)))
                    .invoke(() -> session.flush());
            testEntity = TestEntityHelper.createTestEntityWithUUID(NOW);
            session.withTransaction(tx -> session.persist(testEntity));
        });
    }

    @AfterEach
    public void end() {
        vertx.runOnContext(() -> {
            session.close();
        });
    }

    @Test
    void testEqual() {
        final String filter = "type==TEST";
        checkWithFilter(filter);
    }

    @Test
    void testNotEqual() {
        final String filter = "name!=test";
        checkWithNotFilter(filter);
    }

    @Test
    void testGreaterThan() {
        final String filter = "number>0";
        checkWithFilter(filter);
    }

    @Test
    void testGreaterThanOrEqual() {
        final String filter = "number>=1";
        checkWithFilter(filter);
    }

    @Test
    void testLessThan() {
        final String filter = "createdAt<" + InstantHelper.getStringFromInstant();
        checkWithFilter(filter);
    }

    @Test
    void testLessThanOrEqual() {
        final String filter = "name<=test";
        checkWithFilter(filter);
    }

    @Test
    void testLike() {
        final String filter = "name==te*";
        checkWithFilter(filter);
    }

    @Test
    void testLikeAnything() {
        final String filter = "name==*";
        checkWithFilter(filter);
    }

    @Test
    void testLikeEnum() {
        final String filter = "type==*s*";
        checkWithFilter(filter);
    }

    @Test
    void testNotLike() {
        final String filter = "name!=*t";
        checkWithNotFilter(filter);
    }

    @Test
    void testNotLikeAnything() {
        final String filter = "name!=*";
        checkWithNotFilter(filter);
    }

    @Test
    void testNotLikeEnum() {
        final String filter = "type!=TES*";
        checkWithNotFilter(filter);
    }

    @Test
    void testIn() {
        final String filter = "name=in=(test,dummy)";
        checkWithFilter(filter);
    }

    @Test
    void testNotIn() {
        final String filter = "name=out=(test,dummy)";
        checkWithNotFilter(filter);
    }

    @Test
    void testContains() {
        final String filter = "listOfStrings=contains=*g_1;setOfTimes=contains=" + INSTANT_NOW;
        checkWithFilter(filter);
    }

    @Test
    void testExcludes() {
        final String filter = "listOfStrings=excludes=tag_1,setOfTimes=excludes=" + INSTANT_NOW;
        checkWithNotFilter(filter);
    }

    @Test
    void testMultipleOperators() {
        final String filter = "name=in=(test,dummy);createdAt>" + INSTANT_NOW + ";type=out=(TYPE),name==Dummy";
        checkWithFilter(filter);
    }

    @Test
    void testTwoMatchingFilters() {
        vertx.runOnContext(() -> {
            final String filter1 = "name=in=(test)";
            final String filter2 = "type==TEST";
            final Predicate predicate =
                    new RsqlToPredicateParser<>(mutinySessionFactory.getCriteriaBuilder(), root).addFilter(filter1).addFilter(filter2).execute();
            session.createQuery(criteriaQuery.select(root).where(predicate))
                    .getResultList()
                    .invoke(result -> {
                        Assertions.assertNotNull(result);
                        Assertions.assertEquals(1, result.size());
                        Assertions.assertEquals(testEntity.getName(), result.get(0).getName());
                    });

        });
    }

    @Test
    void testTwoUnMatchingFilters() {
        vertx.runOnContext(() -> {
            final String filter1 = "name==t*";
            final String filter2 = "createdAt>" + InstantHelper.getStringFromInstant();
            final Predicate predicate =
                    new RsqlToPredicateParser<>(mutinySessionFactory.getCriteriaBuilder(), root).addFilter(filter1).addFilter(filter2).execute();
            session.createQuery(criteriaQuery.select(root).where(predicate))
                    .getResultList()
                    .invoke(result -> {
                        Assertions.assertNotNull(result);
                        Assertions.assertTrue(result.isEmpty());
                    });
        });
    }

    // CPD-OFF
    @Test
    void testConvertSelectors() {
        vertx.runOnContext(() -> {
            final String filter = "created_at<=" + InstantHelper.getStringFromInstant();
            final Predicate predicate = new RsqlToPredicateParser<>(new RsqlParserContextImpl<>(mutinySessionFactory.getCriteriaBuilder()) {
                @Override
                public boolean convertFieldNames() {
                    return true;
                }
            }, root).addFilter(filter).execute();
            session.createQuery(criteriaQuery.select(root).where(predicate))
                    .getResultList()
                    .invoke(result -> {
                        Assertions.assertNotNull(result);
                        Assertions.assertEquals(1, result.size());
                        Assertions.assertEquals(testEntity.getName(), result.get(0).getName());
                    });
        });
    }

    @Test
    void testFieldConversion() {
        vertx.runOnContext(() -> {
            final String filter = "TYPE==test";
            final Predicate predicate = new RsqlToPredicateParser<>(new RsqlParserContextImpl<>(mutinySessionFactory.getCriteriaBuilder()) {
                @Override
                public boolean convertFieldNames() {
                    return true;
                }

                @Override
                public RsqlNode createNode(final Root<TestEntityWithUUID> clientContext,
                                           final String fieldName,
                                           final RsqlOperator operator,
                                           final List<String> arguments) {
                    if (Objects.equals(fieldName, "TYPE")) {
                        return RsqlNode.builder()
                                .operator(operator)
                                .fieldName(fieldName.toLowerCase(Locale.getDefault()))
                                .fieldType(String.class)
                                .arguments(arguments.stream().map(argument -> argument.toUpperCase(Locale.getDefault())).collect(Collectors.toList()))
                                .build();
                    }
                    return super.createNode(clientContext, fieldName, operator, arguments);
                }
            }, root).addFilter(filter).execute();
            session.createQuery(criteriaQuery.select(root).where(predicate))
                    .getResultList()
                    .invoke(result -> {
                        Assertions.assertNotNull(result);
                        Assertions.assertEquals(1, result.size());
                        Assertions.assertEquals(testEntity.getName(), result.get(0).getName());
                    });

        });
    }
    // CPD-ON

    @Test
    void testAPIException() {
        vertx.runOnContext(() -> {
            final String filter = "dummy==*";
            final RsqlToPredicateParser<TestEntityWithUUID> predicateParser =
                    new RsqlToPredicateParser<>(mutinySessionFactory.getCriteriaBuilder(), root).addFilter(filter);
            final APIException apiException = Assertions.assertThrows(APIException.class, predicateParser::execute);

            Assertions.assertEquals(BAD_REQUEST.getReasonPhrase(), apiException.getTitle());
            Assertions.assertEquals(BAD_REQUEST.getStatusCode(), apiException.getStatus());
        });
    }

    private void checkWithFilter(final String filter) {
        vertx.runOnContext(() -> {
            executeQuery(filter)
                    .invoke(result -> {
                        Assertions.assertNotNull(result);
                        Assertions.assertEquals(1, result.size());
                        Assertions.assertEquals(testEntity.getName(), result.get(0).getName());
                    });
        });
    }

    private void checkWithNotFilter(final String filter) {
        vertx.runOnContext(() -> {
            executeQuery(filter)
                    .invoke(result -> {
                        Assertions.assertNotNull(result);
                        Assertions.assertTrue(result.isEmpty());
                    });
        });
    }

    private Uni<List<TestEntityWithUUID>> executeQuery(final String filter) {
        final Predicate predicate = new RsqlToPredicateParser<>(mutinySessionFactory.getCriteriaBuilder(), root).addFilter(filter).execute();
        return session.createQuery(criteriaQuery.select(root).where(predicate)).getResultList();
    }
}
