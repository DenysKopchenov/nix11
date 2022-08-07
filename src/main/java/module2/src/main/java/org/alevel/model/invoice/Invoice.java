package org.alevel.model.invoice;

import lombok.*;
import org.alevel.model.customer.Customer;
import org.alevel.model.product.Product;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    private List<Product> products;
    private Customer customer;
    private InvoiceType type;
    private LocalDateTime created;
}
