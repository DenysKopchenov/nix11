package com.alevel.lesson10.shop.command;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public final class Utils {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private Utils() {
    }

    public static int getInput(List<String> names) throws IOException {
        while (true) {
            System.out.println("Please enter number between 0 and " + (names.size() - 1));
            for (int i = 0; i < names.size(); i++) {
                System.out.printf("%d) %s%n", i, names.get(i));
            }
            String input = READER.readLine();
            if (StringUtils.isNumeric(input)) {
                int userInput = Integer.parseInt(input);
                if (userInput < names.size()) {
                    return userInput;
                }
            } else {
                System.out.println("Wrong input");
            }
        }
    }
}
