package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WalletPage extends BasePage {

    private static final By PAGE = By.id("wallet-page");
    private static final By BALANCE = By.id("wallet-balance-value");
    private static final By UPDATED_AT = By.id("wallet-updated-at-field");
    private static final By TOPUP_INPUT = By.id("wallet-topup-amount-input");
    private static final By TOPUP_BUTTON = By.id("wallet-topup-submit-btn");
    private static final By SUCCESS_MESSAGE = By.id("wallet-success-message");
    private static final By ERROR_MESSAGE = By.id("wallet-error-message");
    private static final By TRANSACTION_ITEMS = By.cssSelector("[id^='wallet-transaction-item-']");
    private static final By FIRST_TRANSACTION_DATE = By.id("wallet-transaction-created-at-1");

    public WalletPage(WebDriver driver) {
        super(driver);
    }

    public WalletPage open() {
        openPath("/wallet");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public boolean hasBalance() {
        return isVisible(BALANCE);
    }

    public boolean hasMetadata() {
        return isVisible(UPDATED_AT);
    }

    public int transactionCount() {
        return count(TRANSACTION_ITEMS);
    }

    public void topUp(String amount) {
        type(TOPUP_INPUT, amount);
        click(TOPUP_BUTTON);
    }

    public boolean hasTopUpFeedback() {
        return isVisible(SUCCESS_MESSAGE) || isVisible(ERROR_MESSAGE);
    }

    public boolean hasErrorMessage() {
        return isVisible(ERROR_MESSAGE);
    }

    public String updatedAtText() {
        return text(UPDATED_AT);
    }

    public String firstTransactionDateText() {
        return text(FIRST_TRANSACTION_DATE);
    }
}
