package base;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ApiClient;
import utils.ConfigReader;

import java.time.Duration;

public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        if (ConfigReader.getBoolean("skipWhenAppUnavailable", true)
                && !ApiClient.isReachable(baseUrl())) {
            throw new SkipException("Frontend is not reachable at " + baseUrl() + ".");
        }

        WebDriver driver = DriverFactory.createDriver();
        DriverFactory.setDriver(driver);

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigReader.getInt("pageLoadTimeoutSeconds", 30))
        );
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected WebDriver driver() {
        return DriverFactory.getDriver();
    }

    protected String baseUrl() {
        return ConfigReader.get("baseUrl", "http://localhost:4200");
    }

    protected void openPath(String path) {
        String normalizedBaseUrl = baseUrl().replaceAll("/+$", "");
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        driver().get(normalizedBaseUrl + normalizedPath);
    }
}

