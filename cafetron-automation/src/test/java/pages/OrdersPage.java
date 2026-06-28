package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrdersPage extends BasePage {

    private static final By PAGE = By.id("orders-page");
    private static final By ORDER_CARDS = By.cssSelector("[id^='orders-card-']");
    private static final By EMPTY_STATE = By.id("orders-empty-state");

    public OrdersPage(WebDriver driver) {
        super(driver);
    }

    public OrdersPage open() {
        openPath("/orders");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public int orderCount() {
        return count(ORDER_CARDS);
    }

    public boolean hasEmptyState() {
        return isVisible(EMPTY_STATE);
    }
}
