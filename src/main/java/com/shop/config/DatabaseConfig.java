package com.shop.config;

public final class DatabaseConfig {
    private static final String URL =
            "jdbc:mysql://localhost:3306/internet_shop?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "shop_user";
    private static final String PASSWORD = "change_me";

    private DatabaseConfig() {
    }

    public static String getUrl() {
        return URL;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
