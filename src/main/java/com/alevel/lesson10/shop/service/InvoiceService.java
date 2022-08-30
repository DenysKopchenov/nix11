package com.alevel.lesson10.shop.service;

import com.alevel.lesson10.shop.annotations.Autowired;
import com.alevel.lesson10.shop.annotations.Singleton;
import com.alevel.lesson10.shop.model.Invoice;
import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.repository.InvoiceRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void createAndSaveInvoice(List<Product> invoiceProducts) {
        Invoice invoice = new Invoice();
        invoice.setTime(LocalDateTime.now());
        invoice.setSum(invoiceProducts.stream().mapToDouble(Product::getPrice).sum());
        invoiceProducts.forEach(product -> product.setInvoice(invoice));
        invoice.setProducts(new ArrayList<>(invoiceProducts));
        invoice.setProductIds(invoiceProducts.stream()
                .map(Product::getId)
                .toList());
        invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoicesWithSumGreaterThan(double sum) {
        return invoiceRepository.findAllInvoicesWithSumGreaterThen(sum);
    }

    public long getCountOfInvoices() {
        return invoiceRepository.getInvoiceCount();
    }

    public void updateInvoiceDate(LocalDateTime newDate, String id) {
        invoiceRepository.findById(id).ifPresentOrElse(invoice -> {
            invoice.setTime(newDate);
            invoiceRepository.update(invoice);
        }, () -> {
            throw new IllegalArgumentException("");
        });
    }

    public Map<Double, Long> groupInvoiceBySum() {
        return invoiceRepository.groupBySum();
    }

    public Invoice findById(String id) {
        return invoiceRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
