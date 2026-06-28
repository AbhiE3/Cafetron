package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MenuPage extends BasePage {

    private static final By PAGE = By.id("menu-browse-page");
    private static final By ITEM_CARDS = By.cssSelector("[id^='menu-item-card-']");
    private static final By ADD_BUTTONS = By.cssSelector("[id^='menu-item-add-btn-']");
    private static final By CART_COUNT = By.id("menu-cart-count");
    private static final By CART_DRAWER = By.id("menu-cart-drawer");
    private static final By CART_CHECKOUT_BUTTON = By.id("menu-cart-checkout-btn");
    private static final By CART_MESSAGE = By.id("menu-cart-message-alert");
    private static final By SEARCH_INPUT = By.id("menu-search-input");
    private static final By EMPTY_STATE = By.id("menu-empty-state");
    private static final By LOGOUT_BUTTON = By.id("menu-logout-btn");

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public MenuPage open() {
        openPath("/menu");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public int itemCount() {
        return count(ITEM_CARDS);
    }

    public void addFirstAvailableItem() {
        List<WebElement> buttons = driver.findElements(ADD_BUTTONS);
        for (WebElement button : buttons) {
            if (button.isDisplayed() && button.isEnabled()) {
                button.click();
                return;
            }
        }
        throw new IllegalStateException("No available menu item add button was found.");
    }

    public int cartCount() {
        String countText = text(CART_COUNT).trim();
        return countText.isBlank() ? 0 : Integer.parseInt(countText);
    }

    public boolean hasCartMessage() {
        return isVisible(CART_MESSAGE);
    }

    public void openCartDrawer() {
        click(By.id("menu-cart-preview-btn"));
        visible(CART_DRAWER);
    }

    public CheckoutPage checkoutFromDrawer() {
        openCartDrawer();
        click(CART_CHECKOUT_BUTTON);
        return new CheckoutPage(driver);
    }

    public void search(String term) {
        type(SEARCH_INPUT, term);
    }

    public boolean hasEmptyState() {
        return isVisible(EMPTY_STATE);
    }

    public boolean hasLogoutButton() {
        return isVisible(LOGOUT_BUTTON);
    }
}
