package org.alevel.service;

import org.alevel.model.customer.Customer;
import org.alevel.model.invoice.Invoice;
import org.alevel.model.invoice.InvoiceType;
import org.alevel.model.product.Product;
import org.alevel.util.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.LongPredicate;

public class ShopService {

    private final Random random;
    private final Logger logger;
    private List<Product> products;

    public ShopService(Random random) {
        this.random = random;
        products = new ArrayList<>();
        logger = LoggerFactory.getLogger(ShopService.class);
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
}
