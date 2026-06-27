package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage extends BasePage {

    private static final By REGISTER_PAGE = By.id("register-page");
    private static final By NAME_INPUT = By.id("register-name-input");
    private static final By EMAIL_INPUT = By.id("register-email-input");
    private static final By PASSWORD_INPUT = By.id("register-password-input");
    private static final By EMPLOYEE_ID_INPUT = By.id("register-employee-id-input");
    private static final By DEPARTMENT_INPUT = By.id("register-department-input");
    private static final By ROLE_SELECT = By.id("register-role-select");
    private static final By SUBMIT_BUTTON = By.id("register-submit-btn");
    private static final By LOGIN_LINK = By.id("register-login-link");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage open() {
        openPath("/register");
        visible(REGISTER_PAGE);
        return this;
    }

    public void register(String name, String email, String password, String employeeId, String department, String role) {
        type(NAME_INPUT, name);
        type(EMAIL_INPUT, email);
        type(PASSWORD_INPUT, password);
        type(EMPLOYEE_ID_INPUT, employeeId);
        type(DEPARTMENT_INPUT, department);
        new Select(visible(ROLE_SELECT)).selectByValue(role);
        click(SUBMIT_BUTTON);
    }

    public LoginPage goToLogin() {
        click(LOGIN_LINK);
        return new LoginPage(driver);
    }

    public boolean isLoaded() {
        return visible(REGISTER_PAGE).isDisplayed();
    }
}

