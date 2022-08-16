package org.alevel.service;

import org.alevel.exception.BrokenStringException;
import org.alevel.model.product.Product;
import org.alevel.model.product.ProductType;
import org.alevel.model.product.telephone.Telephone;
import org.alevel.model.product.television.Television;

import java.util.Map;
import java.util.function.Function;

public final class ProductFactory {

    private static final String TYPE = "type";
    private static final String MODEL = "model";
    private static final String PRICE = "price";
    private static final String SCREEN_TYPE = "screen type";
    private static final String SERIES = "series";
    private static final String COUNTRY = "country";
    private static final String DIAGONAL = "diagonal";
    private static final String MESSAGE = "Unknown type ";

    private ProductFactory() {
    }

    public static Product createProductFromMap(Map<String, String> fields) {
        Function<Map<String, String>, Product> mapToProduct = map -> {
            switch (map.get(TYPE).toLowerCase()) {
                case "telephone" -> {
                    Telephone telephone = new Telephone(ProductType.TELEPHONE);
                    telephone.setModel(map.get(MODEL));
                    telephone.setPrice(Long.parseLong(map.get(PRICE)));
                    telephone.setScreenType(map.get(SCREEN_TYPE));
                    telephone.setSeries(map.get(SERIES));
                    return telephone;
                }
                case "television" -> {
                    Television television = new Television(ProductType.TELEVISION);
                    television.setCountry(map.get(COUNTRY));
                    television.setDiagonal(map.get(DIAGONAL));
                    television.setScreenType(map.get(SCREEN_TYPE));
                    television.setPrice(Long.parseLong(map.get(PRICE)));
                    television.setSeries(map.get(SERIES));
                    return television;
                }
                default -> throw new IllegalArgumentException(MESSAGE + map.get(TYPE));
            }
        };
        return mapToProduct.apply(fields);
    }
}
