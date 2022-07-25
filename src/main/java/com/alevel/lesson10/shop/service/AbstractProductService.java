package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ProductType;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.model.laptop.CPU;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.ProductRepository;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractProductService<T extends Product> {
    protected static final Random RANDOM = new Random();
    private ProductRepository<T> repository;

    private BiPredicate<T, Long> checkPriceIsGreaterThanReferPrice = (product, referPrice) -> product.getPrice() > referPrice;

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

    public void printProductsWithPriceGreaterThenReferPrice(long referPrice) {
        repository.findAll()
                .stream()
                .filter(product -> checkPriceIsGreaterThanReferPrice.test(product, referPrice))
                .forEach(product -> System.out.println(product.getTitle() + " price: " + product.getPrice()));
    }

    public int countAllProducts() {
        return repository.findAll()
                .stream()
                .map(Product::getCount)
                .reduce(0, Integer::sum);
    }

    public Map<String, String> collectSortedDistinctProductsToMap() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getTitle))
                .distinct()
                .collect(Collectors.toMap(Product::getId, product -> product.getClass().getSimpleName(), (o1, o2) -> o2));
    }

    public LongSummaryStatistics getProductsPriceSummaryStatistic() {
        return repository.findAll()
                .stream()
                .mapToLong(Product::getPrice)
                .summaryStatistics();
    }

    public Product mapProduct(Map<String, Object> fields) {
        Function<Map<String, Object>, Product> mapToProduct = (map) -> {
            Object productType = map.get("productType");
            if (productType instanceof ProductType type) {
                return switch (type) {
                    case PHONE -> new Phone(map.getOrDefault("title", "Default").toString(),
                            (Integer) map.getOrDefault("count", 0),
                            (Long) map.getOrDefault("price", 0L),
                            map.getOrDefault("model", "Default").toString(),
                            Manufacturer.valueOf(map.getOrDefault("manufacturer", Manufacturer.NOKIA).toString()));
                    case LAPTOP -> new Laptop(map.getOrDefault("title", "Default").toString(),
                            (Integer) map.getOrDefault("count", 0),
                            (Long) map.getOrDefault("price", 0L),
                            CPU.valueOf(map.getOrDefault("cpu", CPU.AMD).toString()));
                    case BALL -> new Ball(map.getOrDefault("title", "Default").toString(),
                            (Integer) map.getOrDefault("count", 0),
                            (Long) map.getOrDefault("price", 0L),
                            Size.valueOf(map.getOrDefault("size", Size.NONE).toString()));
                };
            }
            throw new IllegalArgumentException();
        };
        return mapToProduct.apply(fields);
    }
}
