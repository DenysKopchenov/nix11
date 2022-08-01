package com.example;

import com.example.model.Product;
import com.example.service.NotificationService;
import com.example.service.ProductFactory;
import com.example.service.ProductService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        NotificationService notificationService = new NotificationService();
        List<Product> products = new ArrayList<>();
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.forEach(productService::save);

        System.out.println(productService.getAll());
        System.out.println("notifications sent: " + notificationService.filterNotifiableProductsAndSendNotifications());
    }
}
