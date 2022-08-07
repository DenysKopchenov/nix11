package org.alevel.service;

import lombok.SneakyThrows;
import org.alevel.model.customer.Customer;
import org.alevel.model.invoice.Invoice;
import org.alevel.model.product.Product;
import org.alevel.model.product.ProductType;
import org.alevel.model.product.telephone.Telephone;
import org.alevel.model.product.television.Television;
import org.alevel.util.CSVParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceTest {

    private ShopService target;
    private BufferedReader reader;

    @BeforeEach
    void setUp() {
        target = new ShopService(new Random());
        reader = mock(BufferedReader.class);
    }

    @SneakyThrows
    @Test
    void createProducts() {
        when(reader.readLine()).thenReturn(null);
        try (MockedStatic<CSVParser> mockedStatic = mockStatic(CSVParser.class)) {
            Telephone telephone = new Telephone(ProductType.TELEPHONE);
            mockedStatic.when(() -> CSVParser.parseCSVAndMapToProducts(anyList())).thenReturn(List.of(telephone));
            List<Product> products = target.createProducts(reader);

            assertEquals(telephone, products.get(0));
            verify(reader, times(1)).readLine();
        }
    }

    @SneakyThrows
    @Test
    void generateRandomInvoice() {
        List<Product> products = List.of(new Telephone(ProductType.TELEPHONE), new Television(ProductType.TELEVISION), new Telephone(ProductType.TELEPHONE));
        for (Field declaredField : target.getClass().getDeclaredFields()) {
            if (declaredField.getName().endsWith("products")) {
                declaredField.setAccessible(true);
                declaredField.set(target, products);
            }
        }
        Customer customer = new Customer();
        Invoice invoice = target.generateRandomInvoice((i -> i > 100), customer);

        assertFalse(invoice.getProducts().isEmpty());
        assertSame(customer, invoice.getCustomer());
    }

    @Test
    void getInvoiceSum() {
        Telephone telephone1 = new Telephone(ProductType.TELEPHONE);
        telephone1.setPrice(100);
        Telephone telephone2 = new Telephone(ProductType.TELEPHONE);
        telephone2.setPrice(100);

        long actual = target.getInvoiceSum(List.of(telephone1, telephone2));
        long expected = 200;

        assertEquals(expected, actual);
    }
}