package com.alevel.lesson10.shop.config;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class HibernateUtil {

    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("model").createEntityManager();
        }
        return entityManager;
    }

}
