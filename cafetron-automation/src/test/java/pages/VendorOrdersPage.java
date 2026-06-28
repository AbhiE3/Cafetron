package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VendorOrdersPage extends BasePage {

    private static final By PAGE = By.id("vendor-orders-page");
    private static final By BACK_TO_MENU = By.id("vendor-orders-back-to-menu-link");
    private static final By ORDER_CARDS = By.cssSelector("[id^='vendor-order-card-']");
    private static final By ACCEPT_FIRST = By.id("vendor-order-accept-btn-1");
    private static final By DECLINE_FIRST = By.id("vendor-order-decline-btn-1");
    private static final By DECLINE_REASON_FIRST = By.id("vendor-order-decline-reason-input-1");
    private static final By SUCCESS_NOTICE = By.id("vendor-orders-success-notice");
    private static final By EMPTY_STATE = By.id("vendor-orders-empty-state");

    public VendorOrdersPage(WebDriver driver) {
        super(driver);
    }

    public VendorOrdersPage open() {
        openPath("/vendor/orders");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public int orderCount() {
        return count(ORDER_CARDS);
    }

    public boolean hasQueueState() {
        return orderCount() > 0 || isVisible(EMPTY_STATE);
    }

    public void acceptFirstPendingOrder() {
        click(ACCEPT_FIRST);
    }

    public void declineFirstPendingOrder(String reason) {
        type(DECLINE_REASON_FIRST, reason);
        click(DECLINE_FIRST);
    }

    public boolean hasSuccessNotice() {
        return isVisible(SUCCESS_NOTICE);
    }

    public void clickBackToMenu() {
        click(BACK_TO_MENU);
    }
}
