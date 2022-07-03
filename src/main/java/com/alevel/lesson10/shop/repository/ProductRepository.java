package com.alevel.lesson10.shop.repository;

import com.alevel.lesson10.shop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository<E extends Product> {

    void save(E product);

    void saveAll(List<E> products);

    List<E> findAll();

    Optional<E> findById(String id);

    void update(E product);

    void delete(String id);

}
