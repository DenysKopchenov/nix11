package com.alevel.lesson10.shop.controller;

import com.alevel.lesson10.shop.command.Command;
import com.alevel.lesson10.shop.command.Commands;
import com.alevel.lesson10.shop.command.Utils;
import com.alevel.lesson10.shop.config.FlywayConfig;
import com.alevel.lesson10.shop.config.HibernateUtil;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class Controller {


    public void run() {
        init();
        try {
            int userAction = chooseAction();
            Commands[] commands = Commands.values();
            Command command = commands[userAction].getCommand();
            command.execute();
            repeatOrOtherAction(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {
        Flyway flyway = FlywayConfig.configureFlyway();
        flyway.clean();
        HibernateUtil.getEntityManager();
        flyway.migrate();
    }

    private static int chooseAction() throws IOException {
        System.out.println("Choose action");
        List<String> commandNames = Arrays.stream(Commands.values()).map(Commands::getName).toList();
        return Utils.getInput(commandNames);
    }

    private static void repeatOrOtherAction(Command command) throws IOException {
        int input = Utils.getInput(List.of("Repeat", "Other action"));
        while (input >= 0) {
            switch (input) {
                case 0 -> {
                    command.execute();
                    input = Utils.getInput(List.of("Repeat", "Other action"));
                }
                case 1 -> {
                    int userAction = chooseAction();
                    Commands[] commands = Commands.values();
                    command = commands[userAction].getCommand();
                    command.execute();
                    input = Utils.getInput(List.of("Repeat", "Other action"));
                }
                default -> {
                    System.out.println("Wrong input");
                    input = Utils.getInput(List.of("Repeat", "Other action"));
                }
            }
        }
    }
}
