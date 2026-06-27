package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TestDataFactory {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private TestDataFactory() {
    }

    public static String uniqueEmployeeId(String prefix) {
        return prefix + LocalDateTime.now().format(FORMATTER);
    }

    public static String uniqueEmail(String prefix) {
        return prefix + "." + LocalDateTime.now().format(FORMATTER) + "@cafetron.local";
    }

    public static String defaultPassword() {
        return "Test@12345";
    }
}

