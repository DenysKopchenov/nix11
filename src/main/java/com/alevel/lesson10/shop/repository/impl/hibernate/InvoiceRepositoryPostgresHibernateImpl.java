package com.alevel.lesson10.shop.repository.impl.hibernate;

import com.alevel.lesson10.shop.annotations.Singleton;
import com.alevel.lesson10.shop.model.Invoice;
import com.alevel.lesson10.shop.repository.InvoiceRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton
public class InvoiceRepositoryPostgresHibernateImpl implements InvoiceRepository {

    private final EntityManager entityManager;

    public InvoiceRepositoryPostgresHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Invoice invoice) {
        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Invoice> findAllInvoicesWithSumGreaterThen(double sum) {
        return entityManager.createQuery("select i from Invoice i where i.sum > :sum", Invoice.class)
                .setParameter("sum", sum)
                .getResultList();
    }

    @Override
    public Optional<Invoice> findById(String id) {
        return Optional.ofNullable(entityManager.find(Invoice.class, id));
    }

    @Override
    public void update(Invoice invoice) {
        entityManager.getTransaction().begin();
        entityManager.merge(invoice);
        entityManager.getTransaction().commit();
    }

    @Override
    public long getInvoiceCount() {
        return (long) entityManager.createQuery("select count(id) from Invoice")
                .getSingleResult();
    }

    @Override
    public Map<Double, Long> groupBySum() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Invoice> root = criteriaQuery.from(Invoice.class);
        criteriaQuery.multiselect(criteriaBuilder.count(root.get("id")), root.get("sum"));
        criteriaQuery.groupBy(root.get("sum"));
        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> resultList = query.getResultList();
        Map<Double, Long> result = new HashMap<>();
        for (Object[] objects : resultList) {
            result.put((Double) objects[1], (Long) objects[0]);
        }
        return result;
    }
}
