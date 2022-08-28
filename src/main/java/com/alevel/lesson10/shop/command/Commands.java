package com.alevel.lesson10.shop.command;

public enum Commands {
    CREATE("Create", new CreateProduct()),
    UPDATE("Update", new Update()),
    DELETE("Delete", new Delete()),
    PRINT("Print all", new Print()),
    PARSE("Parse files", new Parse()),
    HIBERNATE("Hibernate", new Hibernate()),
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
