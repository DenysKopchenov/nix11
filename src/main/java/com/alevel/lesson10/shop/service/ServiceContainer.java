package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.context.ApplicationContext;

import java.util.Map;

public final class ServiceContainer {
    private static final BallService ballService;
    private static final PhoneService phoneService;
    private static final LaptopService laptopService;
    private static final InvoiceService invoiceService;

    private ServiceContainer() {
    }

    static {
        ApplicationContext applicationContext = new ApplicationContext();
        Map<Class<?>, Object> cached = applicationContext.cacheSingletons();
        ballService = (BallService) cached.get(BallService.class);
        phoneService = (PhoneService) cached.get(PhoneService.class);
        laptopService = (LaptopService) cached.get(LaptopService.class);
        invoiceService = (InvoiceService) cached.get(InvoiceService.class);
    }

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
