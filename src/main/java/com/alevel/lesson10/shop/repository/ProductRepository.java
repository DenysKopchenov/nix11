package com.alevel.lesson10.shop.repository;

import com.alevel.lesson10.shop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository<T extends Product> {

    void save(T product);

    void saveAll(List<T> products);

    List<T> findAll();

    Optional<T> findById(String id);

    void update(T product);

    void delete(String id);

}
