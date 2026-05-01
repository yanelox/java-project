package com.shop.config;

public final class DatabaseConfig {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/internet_shop?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "root";

    private DatabaseConfig() {
    }

    public static String getUrl() {
        return getEnvOrDefault("DB_URL", DEFAULT_URL);
    }

    public static String getUsername() {
        return getEnvOrDefault("DB_USERNAME", DEFAULT_USERNAME);
    }

    public static String getPassword() {
        return getEnvOrDefault("DB_PASSWORD", DEFAULT_PASSWORD);
    }

    private static String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
