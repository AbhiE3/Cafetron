package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class QrDisplayPage extends BasePage {

    private static final By PAGE = By.id("qr-display-page");
    private static final By IMAGE = By.id("qr-display-image");
    private static final By ERROR = By.id("qr-display-error");
    private static final By BACK = By.cssSelector("#qr-display-page [id*='back'], #qr-display-page a[href*='orders']");

    public QrDisplayPage(WebDriver driver) {
        super(driver);
    }

    public QrDisplayPage open(long orderId) {
        openPath("/pickup/qr/" + orderId);
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public boolean hasQrOrDisabledMessage() {
        return isVisible(IMAGE) || isVisible(ERROR);
    }

    public boolean hasBackNavigation() {
        return isPresent(BACK);
    }
}
