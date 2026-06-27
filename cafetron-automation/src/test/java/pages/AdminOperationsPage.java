package pages;

import org.openqa.selenium.WebDriver;

public class AdminOperationsPage extends BasePage {

    public AdminOperationsPage(WebDriver driver) {
        super(driver);
    }

    public AdminOperationsPage open() {
        openPath("/admin/operations");
        urlContains("/admin/operations");
        return this;
    }
}

