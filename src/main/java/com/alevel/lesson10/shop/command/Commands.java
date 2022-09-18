package com.alevel.lesson10.shop.command;

import com.alevel.lesson10.shop.command.primenumber.PrimeNumberCounter;
import com.alevel.lesson10.shop.command.robots.CreateDetail;

public enum Commands {
    CREATE("Create", new CreateProduct()),
    UPDATE("Update", new Update()),
    DELETE("Delete", new Delete()),
    PRINT("Print all", new Print()),
    PARSE("Parse files", new Parse()),
    HIBERNATE("Hibernate", new Hibernate()),
    //        MONGODB("MongoDB", new MongoDB()),
    ORDER_THREAD("Start 50 threads in order", new StartThreadsInOrder()),
    PRIME_NUMBER("Prime number finder in range 1-1000 with 2 threads", new PrimeNumberCounter()),
    CREATE_DETAILS("Robot factory create details", new CreateDetail()),
    EXIT("Exit", new Exit());

    private final String name;
    private final Command command;

    Commands(String name, Command command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public Command getCommand() {
        return command;
    }
}
