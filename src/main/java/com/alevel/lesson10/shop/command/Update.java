package com.alevel.lesson10.shop.command;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ProductType;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.service.*;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Update implements Command {

    private static final AbstractProductService<Ball> BALL_SERVICE = ServiceContainer.getBallService();
    private static final AbstractProductService<Phone> PHONE_SERVICE = ServiceContainer.getPhoneService();
    private static final AbstractProductService<Laptop> LAPTOP_SERVICE = ServiceContainer.getLaptopService();
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        System.out.println("What product do you want to update");
        try {
            ProductType[] types = ProductType.values();
            int productTypeIndex = Utils.getInput(Arrays.stream(types).map(Enum::name).toList());
            switch (types[productTypeIndex]) {
                case PHONE -> update(PHONE_SERVICE);
                case LAPTOP -> update(LAPTOP_SERVICE);
                case BALL -> update(BALL_SERVICE);
                default -> throw new IllegalStateException("Unknown type");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void update(AbstractProductService<? extends Product> service) {
        while (true) {
            System.out.println("Enter product ID");
            try {
                String id = READER.readLine();
                Product product = service.findById(id);
                updateTitle(product);
                updatePrice(product);
                updateCount(product);
                service.update(product);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong ID. Try again");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateCount(Product product) throws IOException {
        System.out.println("Enter new count");
        String count = READER.readLine();
        if (StringUtils.isNumeric(count)) {
            product.setCount(Integer.parseInt(count));
        } else {
            System.out.println("Wrong input");
            updateCount(product);
        }
    }

    private void updatePrice(Product product) throws IOException {
        System.out.println("Enter new Price");
        String price = READER.readLine();
        if (StringUtils.isNumeric(price)) {
            product.setPrice(Long.parseLong(price));
        } else {
            System.out.println("Wrong input");
            updatePrice(product);
        }
    }

    private void updateTitle(Product product) throws IOException {
        System.out.println("Enter new Title");
        String title = READER.readLine();
        product.setTitle(title);
    }
}

