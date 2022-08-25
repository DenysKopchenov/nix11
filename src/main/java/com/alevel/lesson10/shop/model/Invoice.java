package com.alevel.lesson10.shop.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
public class Invoice {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column
    private double sum;
    @OneToMany(mappedBy = "invoice",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private List<Product> products;
    private LocalDateTime time;
}
