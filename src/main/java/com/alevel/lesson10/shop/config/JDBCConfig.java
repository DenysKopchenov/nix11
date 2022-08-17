package com.alevel.lesson10.shop.config;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConfig {

    public static final String URL = "jdbc:postgresql://localhost:5432/nix11";
    public static final String USER = "postgres";
    public static final String PASSWORD = "root";

    private JDBCConfig() {
    }

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
