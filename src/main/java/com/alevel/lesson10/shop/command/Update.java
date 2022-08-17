package com.alevel.lesson10.shop.command;

import com.alevel.lesson10.shop.model.Product;
import com.alevel.lesson10.shop.model.ProductType;
import com.alevel.lesson10.shop.model.ball.Ball;
import com.alevel.lesson10.shop.model.ball.Size;
import com.alevel.lesson10.shop.model.laptop.CPU;
import com.alevel.lesson10.shop.model.laptop.Laptop;
import com.alevel.lesson10.shop.model.phone.Manufacturer;
import com.alevel.lesson10.shop.model.phone.Phone;
import com.alevel.lesson10.shop.service.AbstractProductService;
import com.alevel.lesson10.shop.service.BallService;
import com.alevel.lesson10.shop.service.ServiceContainer;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Update implements Command {

    private static final BallService BALL_SERVICE = ServiceContainer.getBallService();
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
                case PHONE -> updatePhone();
                case LAPTOP -> updateLaptop();
                case BALL -> updateBall();
                default -> throw new IllegalStateException("Unknown type");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePhone() {
        while (true) {
            System.out.println("Enter product ID");
            try {
                String id = READER.readLine();
                Phone phone = PHONE_SERVICE.findById(id);
                updateTitle(phone);
                updatePrice(phone);
                updateCount(phone);
                updateManufacturer(phone);
                updateModel(phone);
                PHONE_SERVICE.update(phone);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong ID. Try again");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateModel(Phone phone) throws IOException {
        System.out.println("Enter new Model");
        String title = READER.readLine();
        phone.setTitle(title);
    }

    private void updateManufacturer(Phone phone) throws IOException {
        System.out.println("Select new manufacturer");
        Manufacturer[] values = Manufacturer.values();
        int input = Utils.getInput(Arrays.stream(values)
                .map(Enum::name)
                .toList());
        phone.setManufacturer(values[input]);
    }

    private void updateLaptop() {
        while (true) {
            System.out.println("Enter product ID");
            try {
                String id = READER.readLine();
                Laptop laptop = LAPTOP_SERVICE.findById(id);
                updateTitle(laptop);
                updatePrice(laptop);
                updateCount(laptop);
                updateCPU(laptop);
                LAPTOP_SERVICE.update(laptop);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong ID. Try again");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateCPU(Laptop laptop) throws IOException {
        System.out.println("Select new CPU");
        CPU[] values = CPU.values();
        int input = Utils.getInput(Arrays.stream(values)
                .map(Enum::name)
                .toList());
        laptop.setCpu(values[input]);
    }

    private void updateBall() {
        while (true) {
            System.out.println("Enter product ID");
            try {
                String id = READER.readLine();
                Ball ball = BALL_SERVICE.findById(id);
                updateTitle(ball);
                updatePrice(ball);
                updateCount(ball);
                updateSize(ball);
                BALL_SERVICE.update(ball);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong ID. Try again");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateSize(Ball ball) throws IOException {
        System.out.println("Select new size");
        Size[] values = Size.values();
        int input = Utils.getInput(Arrays.stream(values)
                .map(Enum::name)
                .toList());
        ball.setSize(values[input]);
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

