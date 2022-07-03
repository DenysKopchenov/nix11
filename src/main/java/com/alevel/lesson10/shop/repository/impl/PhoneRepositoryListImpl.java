package com.alevel.lesson10.shop.repository.impl;

import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhoneRepositoryListImpl implements PhoneRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneRepositoryListImpl.class);
    private final List<Phone> phones;

    public PhoneRepositoryListImpl() {
        phones = new ArrayList<>();
    }

    @Override
    public void save(Phone product) {
        phones.add(product);
        LOGGER.info("Phone {} saved", product.getId());
    }

    @Override
    public void saveAll(List<Phone> products) {
        phones.addAll(products);
    }

    @Override
    public List<Phone> findAll() {
        return List.copyOf(phones);
    }

    @Override
    public Optional<Phone> findById(String id) {
        return phones.stream()
                .filter(phone -> phone.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Phone product) {
        phones.stream()
                .filter(phone -> phone.getId().equals(product.getId()))
                .findFirst().ifPresent(phone -> {
                    phone.setTitle(product.getTitle());
                    phone.setCount(product.getCount());
                    phone.setPrice(product.getPrice());
                    phone.setModel(product.getModel());
                    phone.setManufacturer(product.getManufacturer());
                });
    }

    @Override
    public void delete(String id) {
        if (phones.removeIf(phone -> phone.getId().equals(id))) {
            LOGGER.info("Phone {} deleted", id);
        }
    }
}
