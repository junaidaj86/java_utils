package com.postnord.ndm.base.jpa_utils.utils;

import com.postnord.ndm.base.jwt_handler.model.AllowedAccounts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import jakarta.inject.Inject;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
@QuarkusTestResource(PostgresTestResource.class)
@EnabledIfSystemProperty(named = "env.has.postgres", matches = "true")
class MultiTenancyServiceTest {
    private static final String ACCOUNT_ID_1 = UUID.randomUUID().toString();
    private static final String ACCOUNT_ID_2 = UUID.randomUUID().toString();
    private static final String ATTRIBUTE_1 = "one";
    private static final String ATTRIBUTE_2 = "two";

    @Inject
    MultiTenancyService multiTenancyService;

    @InjectMock
    AllowedAccounts allowedAccounts;

    @BeforeEach
    void setUp() {
        Mockito.when(allowedAccounts.toIds()).thenReturn(Set.of(ACCOUNT_ID_1));
    }

    @Test
    void testAccountIdOneShouldBeAuthorized() {
        Assertions.assertTrue(multiTenancyService.isAuthorized(ACCOUNT_ID_1));
    }

    @Test
    void testAccountIdTwoShouldBeUnAuthorized() {
        Assertions.assertFalse(multiTenancyService.isAuthorized(ACCOUNT_ID_2));
    }

    @Test
    void testAccountIdTwoShouldBeAuthorizedWhenSuperUser() {
        Mockito.when(allowedAccounts.containsSuperUser()).thenReturn(true);
        Assertions.assertTrue(multiTenancyService.isAuthorized(ACCOUNT_ID_2));
    }

    @Test
    void testAccountIdOneOrTwoShouldBeAuthorized() {
        Assertions.assertTrue(multiTenancyService.isAuthorized(ACCOUNT_ID_1, ACCOUNT_ID_2));
    }

    @Test
    void testCreateEqualsRSQLFilterForOneAttribute() {
        Assertions.assertEquals("one==" + ACCOUNT_ID_1, multiTenancyService.createRsqlFilter(ATTRIBUTE_1, true));
    }

    @Test
    void testCreateContainsRSQLFilterForOneAttribute() {
        Assertions.assertEquals("one=contains=" + ACCOUNT_ID_1, multiTenancyService.createRsqlFilter(ATTRIBUTE_1, false));
    }

    @Test
    void testCreateEqualsRSQLFilterForTwoAttributes() {
        Assertions.assertTrue(
                Stream.of("one==" + ACCOUNT_ID_1 + ",two==" + ACCOUNT_ID_1, "two==" + ACCOUNT_ID_1 + ",one==" + ACCOUNT_ID_1)
                        .anyMatch(t -> t.equals(multiTenancyService.createRsqlFilter(Map.of(ATTRIBUTE_1, true, ATTRIBUTE_2, true))))
        );
    }

    @Test
    void testCreateContainsRSQLFilterForTwoAttributes() {
        Assertions.assertTrue(
                Stream.of("one=contains=" + ACCOUNT_ID_1 + ",two=contains=" + ACCOUNT_ID_1, "two=contains=" + ACCOUNT_ID_1 + ",one=contains=" + ACCOUNT_ID_1)
                        .anyMatch(t -> t.equals(multiTenancyService.createRsqlFilter(Map.of(ATTRIBUTE_1, false, ATTRIBUTE_2, false))))
        );
    }

    @Test
    void testCreateMixedRSQLFilterForTwoAttributes() {
        Assertions.assertTrue(
                Stream.of("one==" + ACCOUNT_ID_1 + ",two=contains=" + ACCOUNT_ID_1, "two=contains=" + ACCOUNT_ID_1 + ",one==" + ACCOUNT_ID_1)
                        .anyMatch(t -> t.equals(multiTenancyService.createRsqlFilter(Map.of(ATTRIBUTE_1, true, ATTRIBUTE_2, false))))
        );
    }
}
