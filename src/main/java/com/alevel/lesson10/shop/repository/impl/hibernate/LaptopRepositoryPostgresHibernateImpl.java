package com.alevel.lesson10.shop.repository.impl.hibernate;

import com.alevel.lesson10.shop.annotations.Autowired;
import com.alevel.lesson10.shop.annotations.Singleton;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.repository.LaptopRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.alevel.lesson10.shop.config.HibernateUtil.HIBERNATE_BATCH_SIZE;

@Singleton
public class LaptopRepositoryPostgresHibernateImpl implements LaptopRepository {

    private final EntityManager entityManager;

    @Autowired
    public LaptopRepositoryPostgresHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Laptop product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public void saveAll(List<Laptop> products) {
        entityManager.getTransaction().begin();
        int counter = 0;
        for (Laptop product : products) {
            entityManager.persist(product);
            if (++counter % HIBERNATE_BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Laptop> findAll() {
        return entityManager.createQuery("select b from Laptop b", Laptop.class)
                .getResultList();
    }

    @Override
    public Optional<Laptop> findById(String id) {
        return Optional.ofNullable(entityManager.find(Laptop.class, id));
    }

    @Override
    public void update(Laptop product) {
        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(String id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Laptop.class, id));
        entityManager.getTransaction().commit();
    }
}
