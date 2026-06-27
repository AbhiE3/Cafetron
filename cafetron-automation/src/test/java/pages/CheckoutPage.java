package pages;

import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage open() {
        openPath("/checkout");
        urlContains("/checkout");
        return this;
    }
}

