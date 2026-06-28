package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {

    private static final By PAGE = By.id("profile-page");
    private static final By LOGOUT = By.id("profile-logout-btn");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public ProfilePage open() {
        openPath("/profile");
        visible(PAGE);
        return this;
    }

    public void logout() {
        click(LOGOUT);
    }
}
