package com.alevel.lesson10.shop.command;

import com.alevel.lesson10.shop.model.ProductType;
import com.alevel.lesson10.shop.service.ProductFactory;

import java.io.IOException;
import java.util.Arrays;

public class CreateProduct implements Command {

    @Override
    public void execute() {
        System.out.println("What product do you want to create?");
        try {
            ProductType[] types = ProductType.values();
            int productTypeIndex = Utils.getInput(Arrays.stream(types).map(Enum::name).toList());
            ProductFactory.createAndSave(types[productTypeIndex]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
