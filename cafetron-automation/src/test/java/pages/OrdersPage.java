package pages;

import org.openqa.selenium.WebDriver;

public class OrdersPage extends BasePage {

    public OrdersPage(WebDriver driver) {
        super(driver);
    }

    public OrdersPage open() {
        openPath("/orders");
        urlContains("/orders");
        return this;
    }
}

