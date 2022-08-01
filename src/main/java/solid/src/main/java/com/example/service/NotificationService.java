package com.example.service;

import com.example.model.NotifiableProduct;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.repository.ProductRepositoryImpl;

import java.util.List;

public class NotificationService {

    private ProductRepository<Product> repository = ProductRepositoryImpl.getInstance();

    public int filterNotifiableProductsAndSendNotifications() {
        int notifications = 0;
        List<NotifiableProduct> products = repository.getAll()
                .stream()
                .filter(NotifiableProduct.class::isInstance)
                .map(NotifiableProduct.class::cast)
                .toList();
        for (NotifiableProduct product : products) {
            //sending some notifications here
            notifications++;
        }
        return notifications;
    }
}
