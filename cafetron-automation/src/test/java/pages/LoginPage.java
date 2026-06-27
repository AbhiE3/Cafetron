package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By LOGIN_PAGE = By.id("login-page");
    private static final By EMPLOYEE_ID_INPUT = By.id("login-employee-id-input");
    private static final By PASSWORD_INPUT = By.id("login-password-input");
    private static final By SUBMIT_BUTTON = By.id("login-submit-btn");
    private static final By ERROR_MESSAGE = By.id("login-error-message");
    private static final By REGISTER_LINK = By.id("login-register-link");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        openPath("/login");
        visible(LOGIN_PAGE);
        return this;
    }

    public void loginAs(String employeeId, String password) {
        type(EMPLOYEE_ID_INPUT, employeeId);
        type(PASSWORD_INPUT, password);
        click(SUBMIT_BUTTON);
    }

    public RegisterPage goToRegister() {
        click(REGISTER_LINK);
        return new RegisterPage(driver);
    }

    public boolean isLoaded() {
        return visible(LOGIN_PAGE).isDisplayed();
    }

    public String errorMessage() {
        return visible(ERROR_MESSAGE).getText();
    }
}

