package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminVendorsPage extends BasePage {

    private static final By PAGE = By.id("vendor-manage-page");
    private static final By ADD_VENDOR_BUTTON = By.id("vendor-manage-add-vendor-btn");
    private static final By VENDOR_CARDS = By.cssSelector("[id^='vendor-manage-card-']");
    private static final By VENDOR_FORM = By.id("vendor-form-modal");
    private static final By NAME_INPUT = By.id("vendor-name-input");
    private static final By EMAIL_INPUT = By.id("vendor-email-input");
    private static final By PHONE_INPUT = By.id("vendor-phone-input");
    private static final By CONTACT_INPUT = By.id("vendor-contact-person-input");
    private static final By SUBMIT_BUTTON = By.id("vendor-form-submit-btn");
    private static final By ERROR_ALERT = By.id("vendor-form-error-alert");
    private static final By TOAST = By.id("vendor-manage-toast");
    private static final By FIRST_CREATED_AT = By.id("vendor-manage-created-at-1");
    private static final By FIRST_DELETE = By.id("vendor-manage-delete-btn-1");

    public AdminVendorsPage(WebDriver driver) {
        super(driver);
    }

    public AdminVendorsPage open() {
        openPath("/admin/vendors");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public int vendorCount() {
        return count(VENDOR_CARDS);
    }

    public void openCreateForm() {
        click(ADD_VENDOR_BUTTON);
        visible(VENDOR_FORM);
    }

    public void createVendor(String name, String email, String phone, String contactPerson) {
        openCreateForm();
        type(NAME_INPUT, name);
        type(EMAIL_INPUT, email);
        type(PHONE_INPUT, phone);
        type(CONTACT_INPUT, contactPerson);
        click(SUBMIT_BUTTON);
    }

    public boolean hasFormError() {
        return isVisible(ERROR_ALERT);
    }

    public boolean hasCredentialSetupFields() {
        return isPresent(By.cssSelector("#vendor-form input[name='employeeId'], #vendor-form input[id*='employee'], #vendor-form input[type='password']"));
    }

    public boolean hasToast() {
        return isVisible(TOAST);
    }

    public String firstCreatedAtText() {
        return text(FIRST_CREATED_AT);
    }

    public void deleteFirstVendor() {
        click(FIRST_DELETE);
    }

    public boolean hasVendorNamed(String vendorName) {
        return isPresent(By.xpath("//article[starts-with(@id,'vendor-manage-card-')][.//h3[normalize-space()=" + xpathLiteral(vendorName) + "]]"));
    }

    public void deleteVendorNamed(String vendorName) {
        click(By.xpath("//article[starts-with(@id,'vendor-manage-card-')][.//h3[normalize-space()="
                + xpathLiteral(vendorName)
                + "]]//button[contains(@id,'vendor-manage-delete-btn-')]"));
    }

    public String createdAtForVendorNamed(String vendorName) {
        return text(By.xpath("//article[starts-with(@id,'vendor-manage-card-')][.//h3[normalize-space()="
                + xpathLiteral(vendorName)
                + "]]//*[contains(@id,'vendor-manage-created-at-')]"));
    }

    private static String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "', \"'\", '") + "')";
    }
}
