package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.repository.ProductRepositoryImpl;

import java.util.List;

public class ProductService {

    private ProductRepository<Product> repository = ProductRepositoryImpl.getInstance();

    public void save(Product product) {
        repository.save(product);
    }

    public List<Product> getAll() {
        return repository.getAll();
    }
}
