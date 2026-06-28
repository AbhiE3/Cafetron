package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(ConfigReader.getInt("explicitWaitSeconds", 10))
        );
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> visibleElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        clickable(locator).click();
    }

    protected void type(By locator, String value) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected boolean urlContains(String value) {
        return wait.until(ExpectedConditions.urlContains(value));
    }

    protected boolean isVisible(By locator) {
        try {
            return visible(locator).isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }

    protected boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected int count(By locator) {
        return driver.findElements(locator).size();
    }

    protected String text(By locator) {
        return visible(locator).getText();
    }

    protected String attribute(By locator, String attributeName) {
        return visible(locator).getAttribute(attributeName);
    }

    protected WebElement firstVisible(By locator) {
        return visibleElements(locator).get(0);
    }

    protected void clickFirst(By locator) {
        firstVisible(locator).click();
    }

    protected void openPath(String path) {
        String baseUrl = ConfigReader.get("baseUrl", "http://localhost:4200").replaceAll("/+$", "");
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        driver.get(baseUrl + normalizedPath);
    }
}
