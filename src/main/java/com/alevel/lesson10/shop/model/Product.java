package com.alevel.lesson10.shop.model;

import java.util.UUID;

public abstract class Product {

    protected final String id;
    protected String title;
    protected int count;
    protected long price;

    protected Product(String title, int count, long price) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.count = count;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
