package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class QrUploadPage extends BasePage {

    private static final By PAGE = By.id("qr-upload-page");
    private static final By FILE_INPUT = By.id("qr-upload-file-input");
    private static final By SUBMIT = By.id("qr-upload-submit-btn");
    private static final By SUCCESS = By.id("qr-upload-success-message");
    private static final By ERROR = By.id("qr-upload-error-message");

    public QrUploadPage(WebDriver driver) {
        super(driver);
    }

    public QrUploadPage open() {
        openPath("/pickup/upload");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public boolean hasUploadControls() {
        return isVisible(FILE_INPUT) && isVisible(SUBMIT);
    }

    public boolean hasResultFeedback() {
        return isVisible(SUCCESS) || isVisible(ERROR);
    }
}
