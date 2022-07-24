package com.alevel.lesson10.shop.command;

public class Exit implements Command {
    @Override
    public void execute() {
        System.exit(0);
    }
}
