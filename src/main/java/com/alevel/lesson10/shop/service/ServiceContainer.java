package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.repository.impl.mongo.BallRepositoryMongoImpl;
import com.alevel.lesson10.shop.repository.impl.mongo.InvoiceRepositoryMongoImpl;
import com.alevel.lesson10.shop.repository.impl.mongo.LaptopRepositoryMongoImpl;
import com.alevel.lesson10.shop.repository.impl.mongo.PhoneRepositoryMongoImpl;

public final class ServiceContainer {

    //    private static final EntityManager entityManager = HibernateUtil.getEntityManager();
    private static final BallService ballService = new BallService(new BallRepositoryMongoImpl());
    private static final PhoneService phoneService = new PhoneService(new PhoneRepositoryMongoImpl());
    private static final LaptopService laptopService = new LaptopService(new LaptopRepositoryMongoImpl());
    private static final InvoiceService invoiceService = new InvoiceService(new InvoiceRepositoryMongoImpl());

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
