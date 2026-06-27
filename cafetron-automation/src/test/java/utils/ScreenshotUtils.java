package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    private ScreenshotUtils() {
    }

    public static String capture(WebDriver driver, String testName) {
        try {
            Path screenshotDir = Path.of("target", "screenshots");
            Files.createDirectories(screenshotDir);

            String safeTestName = testName.replaceAll("[^a-zA-Z0-9-_]", "_");
            String fileName = safeTestName + "-" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".png";
            Path destination = screenshotDir.resolve(fileName);

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(source.toPath(), destination);
            return destination.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to capture screenshot.", e);
        }
    }
}

