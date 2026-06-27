package pages;

import org.openqa.selenium.WebDriver;

public class WalletPage extends BasePage {

    public WalletPage(WebDriver driver) {
        super(driver);
    }

    public WalletPage open() {
        openPath("/wallet");
        urlContains("/wallet");
        return this;
    }
}

