package org.alevel.controller;

import org.alevel.model.invoice.Invoice;
import org.alevel.model.invoice.InvoiceType;
import org.alevel.model.product.ProductType;
import org.alevel.service.PersonService;
import org.alevel.service.ShopService;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.function.LongPredicate;

public class Controller {

    private LongPredicate longPredicate;
    private final BufferedReader reader;
    private final ShopService shopService;
    private final PersonService personService;


    public Controller() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        shopService = new ShopService();
        personService = new PersonService();
    }

    public void run() throws IOException {
        String separator = "~".repeat(25);
        System.out.println("Hello");
        setPredicate();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("products.csv"))))) {
            shopService.createProducts(bufferedReader);
            for (int i = 0; i < 15; i++) {
                shopService.saveInvoice(shopService.generateRandomInvoice(longPredicate, personService.generateRandomCustomer()));
            }
        }
        System.out.println(separator);
        printCountOfSoldProducts(ProductType.TELEPHONE);
        System.out.println(separator);
        printCountOfSoldProducts(ProductType.TELEVISION);
        System.out.println(separator);

        printLowesInvoiceSum();
        System.out.println(separator);

        printSumAllInvoices();
        System.out.println(separator);

        printInvoiceTypeCount(InvoiceType.RETAIL);
        System.out.println(separator);
        printInvoiceTypeCount(InvoiceType.WHOLESALE);
        System.out.println(separator);

        printInvoicesWithOnlyOneProductType();
        System.out.println(separator);

        printFirstThreeInvoices();
        System.out.println(separator);

        printInvoicesByPersonsUnder18Age();
        System.out.println(separator);

        printSortedInvoices();
        System.out.println(separator);
    }

    private void printSortedInvoices() {
        System.out.println("Sorted invoices");
        List<Invoice> sortedInvoices = shopService.getSortedInvoices();
        if (!sortedInvoices.isEmpty()) {
            sortedInvoices.forEach(System.out::println);
        } else {
            System.out.println("0 invoices found");
        }
    }

    private void printInvoicesByPersonsUnder18Age() {
        System.out.println("Invoices of persons under 18 age:");
        List<Invoice> invoicesByPersonsUnder18Age = shopService.getInvoicesByPersonsUnder18Age();
        if (!invoicesByPersonsUnder18Age.isEmpty()) {
            invoicesByPersonsUnder18Age.forEach(System.out::println);
        } else {
            System.out.println("0 invoices of persons under 18 age found");
        }
    }

    private void printFirstThreeInvoices() {
        System.out.println("First 3 invoices: ");
        List<Invoice> firstThreeInvoices = shopService.getFirstThreeInvoices();
        if (!firstThreeInvoices.isEmpty()) {
            firstThreeInvoices.forEach(System.out::println);
        } else {
            System.out.println("0 invoices found");
        }
    }

    private void printInvoicesWithOnlyOneProductType() {
        System.out.println("Invoices with same product type:");
        List<Invoice> invoicesWithOnlyOneProductType = shopService.getInvoicesWithOnlyOneProductType();
        if (!invoicesWithOnlyOneProductType.isEmpty()) {
            invoicesWithOnlyOneProductType.forEach(System.out::println);
        } else {
            System.out.println("No one invoice have products of same type");
        }
    }

    private void printInvoiceTypeCount(InvoiceType invoiceType) {
        System.out.println(invoiceType.toString() + " invoices = " + shopService.getInvoiceTypeCount(invoiceType));
    }

    private void printSumAllInvoices() {
        System.out.println("Sum of all invoices = " + shopService.getSumAllInvoices());
    }

    private void printLowesInvoiceSum() {
        System.out.print("Lowest invoice: ");
        shopService.getLowesInvoiceSum().ifPresentOrElse(o -> System.out.println(o.getValue() + " sum: " + o.getKey()), () -> System.out.println("0 invoices found"));
    }

    private void printCountOfSoldProducts(ProductType productType) {
        System.out.print(productType.getName() + "s sold: ");
        System.out.println(shopService.getCountOfSoldProducts(productType));
    }


    private void setPredicate() throws IOException {
        String line;
        while (true) {
            System.out.println("Input invoice limit on what will be based invoice type");
            line = reader.readLine();
            if (StringUtils.isNumeric(line)) {
                long limit = Long.parseLong(line);
                longPredicate = sum -> sum > limit;
                return;
            }
            System.out.println("Wrong input");
        }
    }
}
