package org.alevel.service;

import org.alevel.model.customer.Customer;
import org.alevel.model.invoice.Invoice;
import org.alevel.model.invoice.InvoiceType;
import org.alevel.model.product.Product;
import org.alevel.model.product.ProductType;
import org.alevel.util.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;

public class ShopService {

    private final Random random;
    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);
    private List<Product> products;
    private final List<Invoice> invoices;

    public ShopService() {
        this.random = new Random();
        products = new ArrayList<>();
        invoices = new ArrayList<>();
    }

    public List<Product> createProducts(BufferedReader reader) {
        String line;
        List<String> lines = new ArrayList<>();
        while (true) {
            try {
                if ((line = reader.readLine()) == null) {
                    break;
                }
                lines.add(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        products = CSVParser.parseCSVAndMapToProducts(lines);
        return products;
    }

    public Invoice generateRandomInvoice(LongPredicate predicate, Customer customer) {
        List<Product> randomProductList = generateRandomProductList();
        InvoiceType invoiceType = getInvoiceType(predicate, getInvoiceSum(randomProductList));
        LocalDateTime created = LocalDateTime.now();
        Invoice invoice = new Invoice(randomProductList, customer, invoiceType, created);
        logger.info("[{}] [{}] [{} {}]", created, customer, invoiceType, randomProductList);
        return invoice;
    }

    private List<Product> generateRandomProductList() {
        int rand = random.nextInt(1, 6);
        List<Product> randomList = new ArrayList<>();
        for (int i = 0; i < rand; i++) {
            randomList.add(products.get(random.nextInt(products.size())));
        }
        return randomList;
    }

    private InvoiceType getInvoiceType(LongPredicate predicate, long invoiceSum) {
        if (predicate.test(invoiceSum)) {
            return InvoiceType.WHOLESALE;
        } else {
            return InvoiceType.RETAIL;
        }
    }

    public long getInvoiceSum(List<Product> products) {
        return products.stream()
                .mapToLong(Product::getPrice)
                .sum();
    }

    public List<Invoice> getSortedInvoices() {
        return invoices.stream()
                .sorted(Comparator.comparing((Invoice invoice) -> invoice.getCustomer().getAge())
                        .reversed()
                        .thenComparing(invoice -> invoice.getProducts().size())
                        .thenComparing(invoice -> getInvoiceSum(invoice.getProducts())))
                .toList();
    }

    public List<Invoice> getInvoicesByPersonsUnder18Age() {
        return invoices.stream()
                .peek(invoice -> {
                    if (invoice.getCustomer().getAge() < 18) {
                        invoice.setType(InvoiceType.LOW_AGE);
                    }
                })
                .filter(invoice -> invoice.getType().equals(InvoiceType.LOW_AGE))
                .toList();
    }

    public List<Invoice> getFirstThreeInvoices() {
        return invoices.stream()
                .sorted(Comparator.comparing(Invoice::getCreated))
                .limit(3)
                .toList();
    }

    public List<Invoice> getInvoicesWithOnlyOneProductType() {
        List<Invoice> onlyOneType = new ArrayList<>();
        invoices.forEach(invoice -> {
            if (checkContainsOnlyOneProductType(invoice.getProducts(), ProductType.TELEVISION)) {
                onlyOneType.add(invoice);
            }
            if (checkContainsOnlyOneProductType(invoice.getProducts(), ProductType.TELEPHONE)) {
                onlyOneType.add(invoice);
            }
        });
        return onlyOneType;
    }

    private boolean checkContainsOnlyOneProductType(List<Product> products, ProductType productType) {
        return products.stream()
                .allMatch(product -> product.getProductType().equals(productType));
    }

    public long getInvoiceTypeCount(InvoiceType type) {
        return invoices.stream()
                .filter(invoice -> invoice.getType().equals(type))
                .count();
    }

    public long getSumAllInvoices() {
        return getInvoiceSum(invoices.stream()
                .flatMap(invoice -> invoice.getProducts().stream())
                .toList());
    }

    public Optional<Map.Entry<Long, Customer>> getLowesInvoiceSum() {
        return invoices.stream()
                .collect(Collectors.toMap(k -> k.getProducts()
                                .stream()
                                .mapToLong(Product::getPrice)
                                .sum(),
                        Invoice::getCustomer,
                        (p1, p2) -> p1,
                        TreeMap::new))
                .entrySet()
                .stream()
                .findFirst();
    }

    public long getCountOfSoldProducts(ProductType productType) {
        return invoices.stream()
                .flatMap(invoice -> invoice.getProducts().stream())
                .filter(product -> product.getProductType().equals(productType))
                .count();
    }

    public boolean saveInvoice(Invoice invoice) {
        return invoices.add(invoice);
    }
}
