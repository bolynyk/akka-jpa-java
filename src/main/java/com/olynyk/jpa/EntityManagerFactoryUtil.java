package com.olynyk.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryUtil {
    private static EntityManagerFactory entityManagerFactory;

    private EntityManagerFactoryUtil() {
    }

    public static EntityManagerFactory getInstance() {
        if (entityManagerFactory == null) {
            synchronized (EntityManagerFactoryUtil.class) {
                if (entityManagerFactory == null) {
                    entityManagerFactory = Persistence.createEntityManagerFactory("accounts-persistence-unit");
                }
            }
        }
        return entityManagerFactory;
    }

    public static synchronized void close() {
        if(entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }
}
