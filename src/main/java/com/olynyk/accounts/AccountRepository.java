package com.olynyk.accounts;

import com.olynyk.jpa.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

public class AccountRepository {

    protected AccountRepository() {

    }

    protected void create(Account account) {
        final EntityManager entityManager = EntityManagerFactoryUtil.getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(account);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    protected Optional<Account> find(UUID uuid) {
        final EntityManager entityManager = EntityManagerFactoryUtil.getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            final Optional<Account> account = Optional.ofNullable(entityManager.find(Account.class, uuid));
            entityManager.getTransaction().commit();
            return account;
        } finally {
            entityManager.close();
        }
    }

    protected Account update(Account account) {
        final EntityManager entityManager = EntityManagerFactoryUtil.getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            final Account updatedAccount = entityManager.merge(account);
            entityManager.getTransaction().commit();
            return updatedAccount;
        } finally {
            entityManager.close();
        }
    }
}
