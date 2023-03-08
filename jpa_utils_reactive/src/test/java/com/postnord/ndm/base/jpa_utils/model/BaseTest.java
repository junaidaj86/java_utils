package com.postnord.ndm.base.jpa_utils.model;

import org.hibernate.reactive.mutiny.Mutiny;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;

import io.vertx.mutiny.core.Vertx;

public class BaseTest {

    @Inject
    Mutiny.SessionFactory mutinyFactory;

    Mutiny.Session session;

    @Inject
    Vertx vertx;

    protected static final String TEST = "test";

    @BeforeEach
    public void setUp() {
        vertx.runOnContext(() -> {
            session = (Mutiny.Session) mutinyFactory.openSession();
            session.createQuery("select te from " + TestEntity.class.getSimpleName() + " te", TestEntity.class)
                    .getResultList()
                    .invoke(list -> list.forEach(entity -> session.remove(entity)))
                    .invoke(() -> session.flush());
        });
    }

    @AfterEach
    public void end() {
        vertx.runOnContext(() -> {
            session.close();
        });
    }

}
