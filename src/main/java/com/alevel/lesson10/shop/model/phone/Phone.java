package com.alevel.lesson10.shop.model.phone;

import com.alevel.lesson10.shop.model.Product;

public class Phone extends Product {

    private String model;
    private Manufacturer manufacturer;

    public Phone(String title, int count, long price, String model, Manufacturer manufacturer) {
        super(title, count, price);
        this.model = model;
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Phone{");
        sb.append("model='").append(model).append('\'');
        sb.append(", manufacturer=").append(manufacturer);
        sb.append(", id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", count=").append(count);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
