package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminOperationsPage extends BasePage {

    private static final By PAGE = By.id("admin-operations-page");
    private static final By WINDOW_TOGGLE = By.id("admin-operations-window-toggle-btn");
    private static final By WINDOW_STATE = By.id("admin-operations-table-window-state");
    private static final By CUTOFF_INPUT = By.id("admin-operations-cutoff-input");
    private static final By CUTOFF_SAVE = By.id("admin-operations-cutoff-save-btn");
    private static final By CUTOFF_STATUS = By.id("admin-operations-table-cutoff-status");
    private static final By ORDERING_ALLOWED = By.id("admin-operations-table-ordering-allowed");
    private static final By TOAST = By.id("admin-operations-toast");

    public AdminOperationsPage(WebDriver driver) {
        super(driver);
    }

    public AdminOperationsPage open() {
        openPath("/admin/operations");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public void toggleOrderingWindow() {
        click(WINDOW_TOGGLE);
    }

    public String windowState() {
        return text(WINDOW_STATE);
    }

    public String cutoffStatus() {
        return text(CUTOFF_STATUS);
    }

    public String orderingAllowed() {
        return text(ORDERING_ALLOWED);
    }

    public void clearCutoffInput() {
        visible(CUTOFF_INPUT).clear();
    }

    public boolean isCutoffSaveDisabled() {
        return !visible(CUTOFF_SAVE).isEnabled();
    }

    public boolean hasToast() {
        return isVisible(TOAST);
    }
}
