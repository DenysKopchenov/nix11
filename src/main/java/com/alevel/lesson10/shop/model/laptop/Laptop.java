package com.alevel.lesson10.shop.model.laptop;

import com.alevel.lesson10.shop.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Laptop extends Product {

    private CPU cpu;

    public void setCpu(CPU cpu) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Laptop laptop = (Laptop) o;
        return cpu == laptop.cpu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cpu);
    }

    public static class Builder {
        private final Laptop laptop;

        public Builder(long price, CPU cpu) {
            if (cpu == null) {
                throw new IllegalArgumentException("CPU can not be null");
            }
            laptop = new Laptop();
            laptop.setPrice(price);
            laptop.setCpu(cpu);
        }

        public Builder setTittle(String title) {
            if (title.length() > 20) {
                throw new IllegalArgumentException("Title cant be more then 20 symbols");
            }
            laptop.setTitle(title);
            return this;
        }

        public Builder setCount(int count) {
            if (count < 0) {
                throw new IllegalArgumentException("Count cant be less then 0");
            }
            laptop.setCount(count);
            return this;
        }

        public Laptop build() {
            return laptop;
        }
    }

}
