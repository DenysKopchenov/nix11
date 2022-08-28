package com.alevel.lesson10.shop.command;

import com.alevel.lesson10.shop.model.Invoice;
import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hibernate implements Command {

    private static final BallService BALL_SERVICE = ServiceContainer.getBallService();
    private static final PhoneService PHONE_SERVICE = ServiceContainer.getPhoneService();
    private static final LaptopService LAPTOP_SERVICE = ServiceContainer.getLaptopService();
    private static final InvoiceService INVOICE_SERVICE = ServiceContainer.getInvoiceService();

    @Override
    public void execute() {
        int numberOfProducts = 10;
        BALL_SERVICE.createAndFillRepository(numberOfProducts);
        PHONE_SERVICE.createAndFillRepository(numberOfProducts);
        LAPTOP_SERVICE.createAndFillRepository(numberOfProducts);

        List<Product> products = new ArrayList<>(BALL_SERVICE.findAll());
        products.addAll(PHONE_SERVICE.findAll());
        products.addAll(LAPTOP_SERVICE.findAll());

        Collections.shuffle(products);

        int skip = 0;
        int limit = 3;
        for (int i = 0; i < products.size() / limit; i++) {
            INVOICE_SERVICE.createAndSaveInvoice(products.stream()
                    .skip(skip)
                    .limit(limit)
                    .toList());
            skip += 3;
        }

        System.out.println("countOfInvoices = " + INVOICE_SERVICE.getCountOfInvoices());
        int referSum = 20000;
        List<Invoice> allInvoicesWithSumGreaterThan = INVOICE_SERVICE.getAllInvoicesWithSumGreaterThan(referSum);
        allInvoicesWithSumGreaterThan.forEach(invoice -> System.out.println("InvoicesWithSumGreaterThan 20000 = \n" + invoice));

        String id = allInvoicesWithSumGreaterThan.get(0).getId();
        LocalDateTime updatedDate = LocalDateTime.of(1, 1, 1, 0, 0);
        System.out.println("Before date updating");
        System.out.println("invoice = " + INVOICE_SERVICE.findById(id));

        INVOICE_SERVICE.updateInvoiceDate(updatedDate, id);

        System.out.println("After date updating");
        System.out.println("invoice = " + INVOICE_SERVICE.findById(id));

        System.out.println("groupInvoiceBySum = " + INVOICE_SERVICE.groupInvoiceBySum());
    }
}
