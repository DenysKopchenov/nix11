package com.alevel.lesson10.shop.model.laptop;

import com.alevel.lesson10.shop.model.Product;

public class Laptop extends Product {

    private CPU cpu;

    public Laptop(String title, int count, long price, CPU cpu) {
        super(title, count, price);
        this.cpu = cpu;
    }

    public CPU getCpu() {
        return cpu;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Laptop{");
        sb.append("cpu=").append(cpu);
        sb.append(", id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", count=").append(count);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
