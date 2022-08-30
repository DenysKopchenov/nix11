package com.alevel.lesson10.shop.command;

public class MongoDB implements Command {


    @Override
    public void execute() {
        new Hibernate().execute();
    }
}
