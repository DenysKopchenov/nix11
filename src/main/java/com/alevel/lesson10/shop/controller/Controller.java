package com.alevel.lesson10.shop.controller;

import com.alevel.lesson10.shop.command.Command;
import com.alevel.lesson10.shop.command.Commands;
import com.alevel.lesson10.shop.command.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class Controller {

    private Controller() {
    }

    public static void run() {
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

    private static int chooseAction() throws IOException {
        System.out.println("Choose action");
        List<String> commandNames = Arrays.stream(Commands.values()).map(Commands::getName).toList();
        return Utils.getInput(commandNames);
    }

    private static void repeatOrOtherAction(Command command) throws IOException {
        int input = Utils.getInput(List.of("Repeat", "Other action"));
        switch (input) {
            case 0 -> {
                command.execute();
                repeatOrOtherAction(command);
            }
            case 1 -> run();
            default -> {
                System.out.println("Wrong input");
                repeatOrOtherAction(command);
            }
        }
    }
}
