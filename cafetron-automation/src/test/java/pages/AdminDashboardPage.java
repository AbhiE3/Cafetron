package pages;

import org.openqa.selenium.WebDriver;

public class AdminDashboardPage extends BasePage {

    public AdminDashboardPage(WebDriver driver) {
        super(driver);
    }

    public AdminDashboardPage open() {
        openPath("/admin/dashboard");
        urlContains("/admin");
        return this;
    }
}

