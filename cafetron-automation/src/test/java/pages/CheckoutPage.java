package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutPage extends BasePage {

    private static final By PAGE = By.cssSelector("main.checkout-page");
    private static final By CARDS_VIEW = By.id("checkout-cards-view-btn");
    private static final By OVERVIEW_VIEW = By.id("checkout-overview-view-btn");
    private static final By CARD_SLOT_BUTTONS = By.cssSelector("[id^='checkout-card-pickup-slot-']");
    private static final By OVERVIEW_SLOT_BUTTONS = By.cssSelector("[id^='checkout-overview-pickup-slot-']");
    private static final By NEXT_STEP_BUTTON = By.id("checkout-card-next-step-btn");
    private static final By CARD_PLACE_ORDER_BUTTON = By.id("checkout-card-place-order-btn");
    private static final By OVERVIEW_PLACE_ORDER_BUTTON = By.id("checkout-overview-place-order-btn");
    private static final By CARD_ERROR = By.id("checkout-card-place-order-error");
    private static final By OVERVIEW_ERROR = By.id("checkout-overview-place-order-error");
    private static final By ERROR_ALERT = By.id("checkout-error-alert");
    private static final By TOAST = By.id("checkout-toast");
    private static final By WALLET_BALANCE = By.id("checkout-card-wallet-balance");
    private static final By CARD_CART_ITEMS = By.cssSelector("[id^='checkout-card-cart-item-']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage open() {
        openPath("/checkout");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public void useCardsView() {
        click(CARDS_VIEW);
    }

    public void useOverviewView() {
        click(OVERVIEW_VIEW);
    }

    public void selectFirstAvailableCardSlot() {
        selectFirstAvailable(CARD_SLOT_BUTTONS);
    }

    public void selectFirstAvailableOverviewSlot() {
        selectFirstAvailable(OVERVIEW_SLOT_BUTTONS);
    }

    public void goToPlaceOrderCard() {
        for (int i = 0; i < 3; i++) {
            click(NEXT_STEP_BUTTON);
        }
        visible(CARD_PLACE_ORDER_BUTTON);
    }

    public void placeOrderFromCards() {
        click(CARD_PLACE_ORDER_BUTTON);
    }

    public void placeOrderFromOverview() {
        click(OVERVIEW_PLACE_ORDER_BUTTON);
    }

    public boolean hasVisibleOrderError() {
        return isVisible(CARD_ERROR) || isVisible(OVERVIEW_ERROR) || isVisible(ERROR_ALERT) || isVisible(TOAST);
    }

    public boolean hasWalletPayment() {
        return isVisible(WALLET_BALANCE);
    }

    public boolean hasFoodPreviewItem() {
        return count(CARD_CART_ITEMS) > 0;
    }

    private void selectFirstAvailable(By locator) {
        List<WebElement> slots = driver.findElements(locator);
        for (WebElement slot : slots) {
            String ariaDisabled = slot.getAttribute("aria-disabled");
            if (slot.isDisplayed() && !"true".equalsIgnoreCase(ariaDisabled)) {
                slot.click();
                return;
            }
        }
        throw new IllegalStateException("No available pickup slot was found.");
    }
}
