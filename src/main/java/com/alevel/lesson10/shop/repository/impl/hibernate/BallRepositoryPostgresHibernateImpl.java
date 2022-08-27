package com.alevel.lesson10.shop.repository.impl.hibernate;

import com.alevel.lesson10.shop.annotations.Autowired;
import com.alevel.lesson10.shop.annotations.Singleton;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.repository.BallRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.alevel.lesson10.shop.config.HibernateUtil.HIBERNATE_BATCH_SIZE;

@Singleton
public class BallRepositoryPostgresHibernateImpl implements BallRepository {

    private final EntityManager entityManager;

    @Autowired
    public BallRepositoryPostgresHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Ball product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public void saveAll(List<Ball> products) {
        entityManager.getTransaction().begin();
        int counter = 0;
        for (Ball product : products) {
            entityManager.persist(product);
            if (++counter % HIBERNATE_BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Ball> findAll() {
        return entityManager.createQuery("select b from Ball b", Ball.class)
                .getResultList();
    }

    @Override
    public Optional<Ball> findById(String id) {
        return Optional.ofNullable(entityManager.find(Ball.class, id));
    }

    @Override
    public void update(Ball product) {
        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(String id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Ball.class, id));
        entityManager.getTransaction().commit();
    }
}
