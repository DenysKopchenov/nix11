package com.alevel.lesson10.shop.repository;

import com.alevel.lesson10.shop.model.Invoice;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InvoiceRepository {

    void save(Invoice invoice);

    List<Invoice> findAllInvoicesWithSumGreaterThen(double sum);

    Optional<Invoice> findById(String id);

    void update(Invoice invoice);

    long getInvoiceCount();

    Map<Long, Long> groupBySum();
}
