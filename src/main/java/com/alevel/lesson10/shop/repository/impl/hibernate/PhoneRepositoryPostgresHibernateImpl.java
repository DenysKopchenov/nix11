package com.alevel.lesson10.shop.repository.impl.hibernate;

import com.alevel.lesson10.shop.annotations.Autowired;
import com.alevel.lesson10.shop.annotations.Singleton;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.PhoneRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Singleton
public class PhoneRepositoryPostgresHibernateImpl implements PhoneRepository {

    private final EntityManager entityManager;

    @Autowired
    public PhoneRepositoryPostgresHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Phone product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public void saveAll(List<Phone> products) {
        entityManager.getTransaction().begin();
        int counter = 0;
        for (Phone product : products) {
            entityManager.persist(product);
            if (counter++ % products.size() == 0) {
                entityManager.flush();
            }
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Phone> findAll() {
        return entityManager.createQuery("select b from Phone b", Phone.class)
                .getResultList();
    }

    @Override
    public Optional<Phone> findById(String id) {
        return Optional.ofNullable(entityManager.find(Phone.class, id));
    }

    @Override
    public void update(Phone product) {
        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(String id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Phone.class, id));
        entityManager.getTransaction().commit();
    }
}
