package org.alevel.model.customer;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString(exclude = "id")
@EqualsAndHashCode
@AllArgsConstructor
public class Customer {

    private String id;
    private String email;
    private int age;

    public Customer() {
        id = UUID.randomUUID().toString();
    }
}
