package org.alevel.controller;

import org.alevel.model.invoice.Invoice;
import org.alevel.model.invoice.InvoiceType;
import org.alevel.model.product.Product;
import org.alevel.model.product.ProductType;
import org.alevel.service.PersonService;
import org.alevel.service.ShopService;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;

public class Controller {

    private LongPredicate longPredicate;
    private final BufferedReader reader;
    private final ShopService shopService;
    private final PersonService personService;

    private final List<Invoice> invoices;

    public Controller() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        shopService = new ShopService(new Random());
        personService = new PersonService();
        invoices = new ArrayList<>();
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
                invoices.add(shopService.generateRandomInvoice(longPredicate, personService.generateRandomCustomer()));
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
        Comparator<Invoice> compareByAge = Comparator.comparing(invoice -> invoice.getCustomer().getAge(), Comparator.reverseOrder());
        Comparator<Invoice> compareByProductsListSize = Comparator.comparing(invoice -> invoice.getProducts().size());
        Comparator<Invoice> compareByInvoiceSum = Comparator.comparing(invoice -> shopService.getInvoiceSum(invoice.getProducts()));

        invoices.stream()
                .sorted(compareByAge
                        .thenComparing(compareByProductsListSize)
                        .thenComparing(compareByInvoiceSum))
                .forEach(System.out::println);

    }

    private void printInvoicesByPersonsUnder18Age() {
        System.out.println("Invoices of persons under 18 age:");
        List<Invoice> invoicesUnder18Age = invoices.stream()
                .filter(invoice -> invoice.getCustomer().getAge() < 18)
                .toList();
        if (!invoicesUnder18Age.isEmpty()) {
            invoicesUnder18Age.forEach(invoice -> {
                invoice.setType(InvoiceType.LOW_AGE);
                System.out.println(invoice);
            });
        } else {
            System.out.println("0 invoices of persons under 18 age found");
        }
    }

    private void printFirstThreeInvoices() {
        System.out.println("First 3 invoices: ");
        invoices.stream()
                .sorted(Comparator.comparing(Invoice::getCreated))
                .limit(3)
                .forEach(System.out::println);
    }

    private void printInvoicesWithOnlyOneProductType() {
        System.out.println("Invoices with same product type:");
        List<Invoice> onlyOneType = new ArrayList<>();
        invoices.forEach(invoice -> {
            if (checkContainsOnlyOneProductType(invoice.getProducts(), ProductType.TELEVISION)) {
                onlyOneType.add(invoice);
            }
            if (checkContainsOnlyOneProductType(invoice.getProducts(), ProductType.TELEPHONE)) {
                onlyOneType.add(invoice);
            }
        });

        if (onlyOneType.isEmpty()) {
            System.out.println("No one invoice have products of same type");
        } else {
            onlyOneType.forEach(System.out::println);
        }
    }

    private boolean checkContainsOnlyOneProductType(List<Product> products, ProductType productType) {
        return products.stream()
                .allMatch(product -> product.getProductType().equals(productType));
    }

    private void printInvoiceTypeCount(InvoiceType type) {
        long count = invoices.stream()
                .filter(invoice -> invoice.getType().equals(type))
                .count();
        System.out.println(type.toString() + " invoices = " + count);
    }

    private void printSumAllInvoices() {
        long invoicesSum = shopService.getInvoiceSum(invoices.stream()
                .flatMap(invoice -> invoice.getProducts().stream())
                .toList());
        System.out.println("Sum of all invoices = " + invoicesSum);

    }

    private void printLowesInvoiceSum() {
        System.out.print("Lowest invoice: ");
        invoices.stream()
                .collect(Collectors.toMap(k -> k.getProducts()
                                .stream()
                                .mapToLong(Product::getPrice)
                                .sum(),
                        Invoice::getCustomer,
                        (p1, p2) -> p1,
                        TreeMap::new))
                .entrySet()
                .stream()
                .findFirst()
                .ifPresent(o -> System.out.println(o.getValue() + " sum: " + o.getKey()));
    }

    private void printCountOfSoldProducts(ProductType productType) {
        System.out.print(productType.getName() + "s sold: ");
        long count = invoices.stream()
                .flatMap(invoice -> invoice.getProducts().stream())
                .filter(product -> product.getProductType().equals(productType))
                .count();
        System.out.println(count);
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
