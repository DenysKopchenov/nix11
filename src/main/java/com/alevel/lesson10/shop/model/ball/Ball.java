package com.alevel.lesson10.shop.model.ball;

import com.alevel.lesson10.shop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ball extends Product {

    @Column
    private Size size;

    public Ball(String title, int count, long price, Size size) {
        super(title, count, price);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ball ball = (Ball) o;
        return size == ball.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size);
    }
}
