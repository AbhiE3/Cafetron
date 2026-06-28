package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public final class SessionUtils {

    private SessionUtils() {
    }

    public static void signIn(WebDriver driver, String baseUrl, AuthSession session) {
        String normalizedBaseUrl = baseUrl.replaceAll("/+$", "");
        driver.get(normalizedBaseUrl + "/login");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "localStorage.setItem('jwt_token', arguments[0]);"
                        + "localStorage.removeItem('cafetron_token');"
                        + "localStorage.removeItem('auth_token');"
                        + "localStorage.setItem('cafetron_user', arguments[1]);",
                session.token(),
                session.userJson()
        );
    }

    public static void clear(WebDriver driver, String baseUrl) {
        String normalizedBaseUrl = baseUrl.replaceAll("/+$", "");
        driver.get(normalizedBaseUrl + "/login");
        ((JavascriptExecutor) driver).executeScript(
                "localStorage.removeItem('jwt_token');"
                        + "localStorage.removeItem('cafetron_token');"
                        + "localStorage.removeItem('auth_token');"
                        + "localStorage.removeItem('cafetron_user');"
        );
    }
}
