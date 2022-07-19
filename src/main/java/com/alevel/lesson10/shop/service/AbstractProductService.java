package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.repository.ProductRepository;

import java.util.List;
import java.util.Random;

public abstract class AbstractProductService<T extends Product> {
    protected static final Random RANDOM = new Random();
    private ProductRepository<T> repository;

    protected AbstractProductService(ProductRepository<T> repository) {
        this.repository = repository;
    }

    public void createAndFillRepository(int count) {
        for (int i = 0; i < count; i++) {
            repository.save(createProduct());
        }
    }

    protected abstract T createProduct();

    public void printAll() {
        repository.findAll().forEach(System.out::println);
        System.out.println("=".repeat(20));
    }

    public T findById(String id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public void update(Product product) {
        repository.update((T) product);
    }

    public void delete(String id) {
        repository.delete(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void save(T product) {
        repository.save(product);
    }
}
