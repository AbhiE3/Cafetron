package pages;

import org.openqa.selenium.WebDriver;

public class VendorOrdersPage extends BasePage {

    public VendorOrdersPage(WebDriver driver) {
        super(driver);
    }

    public VendorOrdersPage open() {
        openPath("/vendor/orders");
        urlContains("/vendor/orders");
        return this;
    }
}

