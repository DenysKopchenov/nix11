package com.example.repository;

import com.example.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepositoryImpl implements ProductRepository<Product> {

    private static ProductRepositoryImpl instance;
    private Map<Long, Product> storage;


    private ProductRepositoryImpl() {
        this.storage = new HashMap<>();
    }

    public static ProductRepository<Product> getInstance() {
        if (instance == null) {
            instance = new ProductRepositoryImpl();
        }
        return instance;
    }

    public Product save(Product product) {
        return storage.put(product.getId(), product);
    }

    public List<Product> getAll() {
        return new ArrayList<>(storage.values());
    }
}
