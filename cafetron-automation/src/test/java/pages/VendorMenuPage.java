package pages;

import org.openqa.selenium.WebDriver;

public class VendorMenuPage extends BasePage {

    public VendorMenuPage(WebDriver driver) {
        super(driver);
    }

    public VendorMenuPage open() {
        openPath("/menu/manage");
        urlContains("/menu/manage");
        return this;
    }
}

