package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.config.HibernateUtil;
import com.alevel.lesson10.shop.repository.impl.hibernate.BallRepositoryPostgresHibernateImpl;
import com.alevel.lesson10.shop.repository.impl.hibernate.InvoiceRepositoryPostgresHibernateImpl;
import com.alevel.lesson10.shop.repository.impl.hibernate.LaptopRepositoryPostgresHibernateImpl;
import com.alevel.lesson10.shop.repository.impl.hibernate.PhoneRepositoryPostgresHibernateImpl;

import javax.persistence.EntityManager;

public final class ServiceContainer {

    private static final EntityManager entityManager = HibernateUtil.getEntityManager();
    private static final BallService ballService = new BallService(new BallRepositoryPostgresHibernateImpl(entityManager));
    private static final PhoneService phoneService = new PhoneService(new PhoneRepositoryPostgresHibernateImpl(entityManager));
    private static final LaptopService laptopService = new LaptopService(new LaptopRepositoryPostgresHibernateImpl(entityManager));
    private static final InvoiceService invoiceService = new InvoiceService(new InvoiceRepositoryPostgresHibernateImpl(entityManager));

    private ServiceContainer() {
    }

//    static {
//        ApplicationContext applicationContext = new ApplicationContext();
//        Map<Class<?>, Object> cached = applicationContext.cacheSingletons();
//        ballService = (BallService) cached.get(BallService.class);
//        phoneService = (PhoneService) cached.get(PhoneService.class);
//        laptopService = (LaptopService) cached.get(LaptopService.class);
//        invoiceService = (InvoiceService) cached.get(InvoiceService.class);
//    }

    public static BallService getBallService() {
        return ballService;
    }

    public static LaptopService getLaptopService() {
        return laptopService;
    }

    public static PhoneService getPhoneService() {
        return phoneService;
    }

    public static InvoiceService getInvoiceService() {
        return invoiceService;
    }

}
