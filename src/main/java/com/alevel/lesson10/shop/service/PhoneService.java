package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.annotations.Autowired;
import com.alevel.lesson10.shop.annotations.Singleton;
import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.ProductRepository;

@Singleton
public class PhoneService extends AbstractProductService<Phone> {

    @Autowired
    public PhoneService(ProductRepository<Phone> repository) {
        super(repository);
    }

    @Override
    public Phone createProduct() {
        return new Phone("Title - " + RANDOM.nextInt(1000),
                RANDOM.nextInt(100),
                RANDOM.nextLong(100000),
                "Model - " + RANDOM.nextInt(1000),
                getRandomManufacturer());
    }

    private Manufacturer getRandomManufacturer() {
        Manufacturer[] values = Manufacturer.values();
        int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public boolean checkDetailExists(String detailToCheck) {
        return findAll()
                .stream()
                .flatMap(phone -> phone.getDetails().stream())
                .anyMatch(detail -> detail.equals(detailToCheck));
    }
}
