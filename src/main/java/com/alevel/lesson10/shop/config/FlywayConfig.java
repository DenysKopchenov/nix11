package com.alevel.lesson10.shop.config;

import org.flywaydb.core.Flyway;

public class FlywayConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/nix11";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private static final String SCHEMA = "shop_hibernate";
    private static final String LOCATION = "db/migration";

    private FlywayConfig() {
    }

    public static Flyway configureFlyway() {
        return Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .baselineOnMigrate(true)
                .schemas(SCHEMA)
                .locations(LOCATION)
                .load();
    }
}
