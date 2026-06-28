package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class VendorMenuPage extends BasePage {

    private static final By PAGE = By.id("menu-manage-page");
    private static final By ADD_ITEM_BUTTON = By.id("menu-manage-add-item-btn");
    private static final By ITEM_CARDS = By.cssSelector("[id^='menu-manage-item-card-']");
    private static final By FILTER_BAR = By.id("menu-manage-filter-bar");
    private static final By VENDOR_FILTER = By.id("menu-manage-vendor-filter-select");
    private static final By STATUS_FILTER = By.id("menu-manage-status-filter-select");
    private static final By FORM = By.id("menu-item-form-modal");
    private static final By ITEM_NAME_INPUT = By.id("menu-item-name-input");
    private static final By PRICE_INPUT = By.id("menu-item-price-input");
    private static final By STOCK_INPUT = By.id("menu-item-stock-input");
    private static final By FOOD_TYPE_INPUT = By.id("menu-item-food-type-input");
    private static final By SUBMIT_BUTTON = By.id("menu-item-form-submit-btn");
    private static final By FORM_ERROR = By.id("menu-item-form-error-alert");
    private static final By NAME_ERROR = By.id("menu-item-name-error");
    private static final By PRICE_ERROR = By.id("menu-item-price-error");
    private static final By TOAST = By.id("menu-manage-toast");
    private static final By FIRST_EDIT = By.id("menu-manage-item-edit-btn-1");
    private static final By FIRST_TOGGLE = By.id("menu-manage-item-toggle-btn-1");
    private static final By FIRST_DELETE = By.id("menu-manage-item-delete-btn-1");

    public VendorMenuPage(WebDriver driver) {
        super(driver);
    }

    public VendorMenuPage open() {
        openPath("/menu/manage");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public int itemCount() {
        return count(ITEM_CARDS);
    }

    public boolean hasReadableFilters() {
        return isVisible(FILTER_BAR) && isVisible(VENDOR_FILTER) && isVisible(STATUS_FILTER);
    }

    public void setStatusFilter(String value) {
        new Select(visible(STATUS_FILTER)).selectByValue(value);
    }

    public void openCreateForm() {
        click(ADD_ITEM_BUTTON);
        visible(FORM);
    }

    public void createItem(String name, String price, String stock, String foodType) {
        openCreateForm();
        type(ITEM_NAME_INPUT, name);
        type(PRICE_INPUT, price);
        type(STOCK_INPUT, stock);
        type(FOOD_TYPE_INPUT, foodType);
        click(SUBMIT_BUTTON);
    }

    public void submitBlankCreateForm() {
        openCreateForm();
        click(SUBMIT_BUTTON);
    }

    public boolean hasRequiredValidation() {
        return isVisible(NAME_ERROR) || isVisible(PRICE_ERROR);
    }

    public boolean hasFormError() {
        return isVisible(FORM_ERROR);
    }

    public boolean hasToast() {
        return isVisible(TOAST);
    }

    public void openFirstEditForm() {
        click(FIRST_EDIT);
        visible(FORM);
    }

    public void saveOpenForm() {
        click(SUBMIT_BUTTON);
    }

    public void toggleFirstItem() {
        click(FIRST_TOGGLE);
    }

    public void toggleItemNamed(String itemName) {
        click(buttonForItem(itemName, "toggle"));
    }

    public boolean firstDeleteActionVisible() {
        return isVisible(FIRST_DELETE);
    }

    public boolean firstDeleteActionDisabled() {
        return !visible(FIRST_DELETE).isEnabled();
    }

    public void deleteFirstItem() {
        click(FIRST_DELETE);
    }

    public void deleteItemNamed(String itemName) {
        click(buttonForItem(itemName, "delete"));
    }

    public void editItemNamed(String itemName) {
        click(buttonForItem(itemName, "edit"));
        visible(FORM);
    }

    public boolean hasItemNamed(String itemName) {
        return isPresent(By.xpath("//article[starts-with(@id,'menu-manage-item-card-')][.//h3[normalize-space()=" + xpathLiteral(itemName) + "]]"));
    }

    private By buttonForItem(String itemName, String action) {
        return By.xpath("//article[starts-with(@id,'menu-manage-item-card-')][.//h3[normalize-space()="
                + xpathLiteral(itemName)
                + "]]//button[contains(@id,'menu-manage-item-"
                + action
                + "-btn-')]");
    }

    private static String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "', \"'\", '") + "')";
    }
}
