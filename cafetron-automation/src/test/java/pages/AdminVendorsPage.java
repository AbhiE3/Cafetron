package pages;

import org.openqa.selenium.WebDriver;

public class AdminVendorsPage extends BasePage {

    public AdminVendorsPage(WebDriver driver) {
        super(driver);
    }

    public AdminVendorsPage open() {
        openPath("/admin/vendors");
        urlContains("/admin/vendors");
        return this;
    }
}

