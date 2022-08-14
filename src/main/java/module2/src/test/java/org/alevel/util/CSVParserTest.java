package org.alevel.util;

import org.alevel.model.product.Product;
import org.alevel.model.product.ProductType;
import org.alevel.model.product.telephone.Telephone;
import org.alevel.model.product.television.Television;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVParserTest {

    List<String> lines;

    @BeforeEach
    void setUp() {
        lines = new ArrayList<>();
        lines.add("type,series,model,diagonal,screen type,country,price");
        lines.add("Telephone,S-10,Samsung,none,QLED,none,13000");
        lines.add("Television,FJS-92,none,43,QLED,China,14000");
    }

    @Test
    void parseCSVAndMapToProductsPositive() {
        List<Product> products = CSVParser.parseCSVAndMapToProducts(lines);

        Telephone telephone = (Telephone) products.get(0);

        assertEquals(ProductType.TELEPHONE, telephone.getProductType());
        assertEquals("S-10", telephone.getSeries());
        assertEquals("Samsung", telephone.getModel());
        assertEquals("QLED", telephone.getScreenType());
        assertEquals(13000, telephone.getPrice());

        Television television = (Television) products.get(1);

        assertEquals(ProductType.TELEVISION, television.getProductType());
        assertEquals("FJS-92", television.getSeries());
        assertEquals("43", television.getDiagonal());
        assertEquals("QLED", television.getScreenType());
        assertEquals("China", television.getCountry());
        assertEquals(13000, telephone.getPrice());
    }

    @Test
    void parseCSVAndMapToProductsNegative() {
        lines.set(1, "");
        lines.set(2, "");
        List<Product> products = CSVParser.parseCSVAndMapToProducts(lines);

        assertTrue(products.isEmpty());
    }
}