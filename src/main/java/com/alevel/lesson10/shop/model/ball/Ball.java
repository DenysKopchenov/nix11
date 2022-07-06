package com.alevel.lesson10.shop.model.ball;

import com.alevel.lesson10.shop.model.Product;

public class Ball extends Product {

    private Size size;

    public Ball(String title, int count, long price, Size size) {
        super(title, count, price);
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ball{");
        sb.append("size=").append(size);
        sb.append(", id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", count=").append(count);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
