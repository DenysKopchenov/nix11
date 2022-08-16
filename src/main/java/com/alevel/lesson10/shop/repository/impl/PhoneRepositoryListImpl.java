package com.alevel.lesson10.shop.repository.impl;

import com.alevel.lesson10.shop.annotations.Autowired;
import com.alevel.lesson10.shop.annotations.Singleton;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class PhoneRepositoryListImpl implements PhoneRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneRepositoryListImpl.class);
    private final List<Phone> phones;

    @Autowired
    public PhoneRepositoryListImpl() {
        phones = new ArrayList<>();
    }

    @Override
    public void save(Phone product) {
        if (product != null) {
            phones.add(product);
            LOGGER.info("Phone {} saved", product.getId());
        } else {
            throw new IllegalArgumentException("Phone can not be null");
        }
    }

    @Override
    public void saveAll(List<Phone> products) {
        if (products != null) {
            phones.addAll(products);
        } else {
            throw new IllegalArgumentException("List can not be null");
        }
    }

    @Override
    public List<Phone> findAll() {
        return phones;
    }

    @Override
    public Optional<Phone> findById(String id) {
        return phones.stream()
                .filter(phone -> phone.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Phone product) {
        Optional<Phone> optionalPhone = findById(product.getId());
        if (optionalPhone.isPresent()) {
            Phone phone = optionalPhone.get();
            phone.setTitle(product.getTitle());
            phone.setCount(product.getCount());
            phone.setPrice(product.getPrice());
            phone.setModel(product.getModel());
        } else {
            throw new IllegalArgumentException("No phone found");
        }
    }

    @Override
    public void delete(String id) {
        if (phones.removeIf(phone -> phone.getId().equals(id))) {
            LOGGER.info("Phone {} deleted", id);
        }
    }
}
