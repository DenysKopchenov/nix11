package com.alevel.lesson10.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity()
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(schema = "hibernate_shop")
public abstract class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;
    @Column
    protected String title;
    @Column
    protected int count;
    @Column
    protected long price;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    protected Invoice invoice;

    protected Product() {
    }

    protected Product(String title, int count, long price) {
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
