package org.alevel.util;

import org.alevel.exception.BrokenStringException;
import org.alevel.model.product.Product;
import org.alevel.service.ProductFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CSVParser {

    private static final String MESSAGE = "Invalid input";

    private CSVParser() {
    }

    public static List<Product> parseCSVAndMapToProducts(List<String> lines) {
        String[] fields = lines.get(0).split(",");
        Map<String, String> result = new HashMap<>();
        List<Product> products = new ArrayList<>();
        lines.stream()
                .skip(1)
                .map(l -> l.split(","))
                .forEach(values -> {
                    for (int i = 0; i < values.length; i++) {
                        result.put(fields[i], values[i]);
                    }
                    if (result.containsValue("")) {
                        try {
                            throw new BrokenStringException(MESSAGE);
                        } catch (BrokenStringException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        products.add(ProductFactory.createProductFromMap(result));
                    }

                });
        return products;
    }
}
