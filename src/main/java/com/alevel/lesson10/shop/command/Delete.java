package com.alevel.lesson10.shop.command;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ProductType;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.service.AbstractProductService;
import com.alevel.lesson10.shop.service.ServiceContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Delete implements Command {

    private static final AbstractProductService<Ball> BALL_SERVICE = ServiceContainer.getBallService();
    private static final AbstractProductService<Phone> PHONE_SERVICE = ServiceContainer.getPhoneService();
    private static final AbstractProductService<Laptop> LAPTOP_SERVICE = ServiceContainer.getLaptopService();
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void execute() {
        System.out.println("What product do you want to delete");
        try {
            ProductType[] types = ProductType.values();
            int productTypeIndex = Utils.getInput(Arrays.stream(types).map(Enum::name).toList());
            switch (types[productTypeIndex]) {
                case PHONE -> delete(PHONE_SERVICE);
                case LAPTOP -> delete(LAPTOP_SERVICE);
                case BALL -> delete(BALL_SERVICE);
                default -> throw new IllegalStateException("Unknown type");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void delete(AbstractProductService<? extends Product> service) {
        while (true) {
            System.out.println("Enter product ID");
            try {
                String id = READER.readLine();
                service.findById(id);
                service.delete(id);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong ID. Try again");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
