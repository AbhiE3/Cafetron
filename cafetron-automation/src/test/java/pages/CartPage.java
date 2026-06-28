package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private static final By PAGE = By.id("cart-page");
    private static final By ITEM_CARDS = By.cssSelector("[id^='cart-item-card-']");
    private static final By CHECKOUT_BUTTON = By.id("cart-checkout-btn");
    private static final By CLEAR_BUTTON = By.id("cart-clear-btn");
    private static final By EMPTY_STATE = By.id("cart-empty-state");
    private static final By FIRST_QUANTITY = By.id("cart-item-quantity-1");
    private static final By FIRST_INCREASE = By.id("cart-item-increase-btn-1");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage open() {
        openPath("/cart");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public int itemCount() {
        return count(ITEM_CARDS);
    }

    public CheckoutPage checkout() {
        click(CHECKOUT_BUTTON);
        return new CheckoutPage(driver);
    }

    public void clearCart() {
        if (isVisible(CLEAR_BUTTON)) {
            click(CLEAR_BUTTON);
        }
    }

    public boolean isEmpty() {
        return isVisible(EMPTY_STATE);
    }

    public int firstQuantity() {
        return Integer.parseInt(text(FIRST_QUANTITY).trim());
    }

    public void increaseFirstItem() {
        click(FIRST_INCREASE);
    }
}
