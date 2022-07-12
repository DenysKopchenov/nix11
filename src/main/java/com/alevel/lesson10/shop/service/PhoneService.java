package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.repository.ProductRepository;

public class PhoneService extends AbstractProductService<Phone> {

    public PhoneService(ProductRepository<Phone> repository) {
        super(repository);
    }

    @Override
    protected Phone createProduct() {
        return new Phone("Title - " + RANDOM.nextInt(),
                RANDOM.nextInt(),
                RANDOM.nextLong(),
                "Model - " + RANDOM.nextInt(),
                getRandomManufacturer());
    }

    private Manufacturer getRandomManufacturer() {
        Manufacturer[] values = Manufacturer.values();
        int index = RANDOM.nextInt(values.length);
        return values[index];
    }

}
