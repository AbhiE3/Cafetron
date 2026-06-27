package pages;

import org.openqa.selenium.WebDriver;

public class MenuPage extends BasePage {

    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public MenuPage open() {
        openPath("/menu");
        urlContains("/menu");
        return this;
    }
}

