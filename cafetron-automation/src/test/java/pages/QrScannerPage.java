package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class QrScannerPage extends BasePage {

    private static final By PAGE = By.id("qr-scanner-page");
    private static final By START = By.id("qr-scanner-start-btn");
    private static final By VIDEO = By.id("qr-scanner-video");
    private static final By PLACEHOLDER = By.id("qr-scanner-camera-placeholder");
    private static final By RESULT = By.id("qr-scanner-result");
    private static final By ERROR = By.id("qr-scanner-error");

    public QrScannerPage(WebDriver driver) {
        super(driver);
    }

    public QrScannerPage open() {
        openPath("/vendor/scanner");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public void startScanner() {
        click(START);
    }

    public boolean hasScannerFeedback() {
        return isVisible(VIDEO) || isVisible(PLACEHOLDER) || isVisible(RESULT) || isVisible(ERROR);
    }
}
