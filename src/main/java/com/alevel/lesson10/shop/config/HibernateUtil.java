package com.alevel.lesson10.shop.config;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class HibernateUtil {
    private HibernateUtil() {
    }

    public static final int HIBERNATE_BATCH_SIZE = 20;

    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("model").createEntityManager();
            entityManager.unwrap(Session.class).setJdbcBatchSize(HIBERNATE_BATCH_SIZE);
        }
        return entityManager;
    }

}
