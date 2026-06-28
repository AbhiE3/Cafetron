package tests.support;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import pages.CartPage;
import pages.CheckoutPage;
import pages.MenuPage;
import utils.AuthSession;
import utils.SessionUtils;
import utils.TestAccount;
import utils.TestAccountService;
import utils.TestDataService;
import utils.UserRole;
import utils.WaitUtils;

public abstract class CafetronUiTest extends BaseTest {

    protected void signInAs(UserRole role) {
        AuthSession session = TestAccountService.sessionFor(role);
        SessionUtils.signIn(driver(), baseUrl(), session);
    }

    protected void openAs(UserRole role, String path) {
        signInAs(role);
        openPath(path);
    }

    protected void clearSession() {
        SessionUtils.clear(driver(), baseUrl());
    }

    protected TestAccount account(UserRole role) {
        return TestAccountService.accountFor(role);
    }

    protected void assertCurrentUrlContains(String fragment, String message) {
        Assert.assertTrue(
                WaitUtils.urlContains(driver(), fragment),
                message + " Current URL: " + driver().getCurrentUrl()
        );
    }

    protected void assertVisible(By locator, String message) {
        Assert.assertTrue(WaitUtils.visible(driver(), locator).isDisplayed(), message);
    }

    protected CheckoutPage prepareCartAndOpenCheckout() {
        TestDataService.createAvailableMenuItem();
        signInAs(UserRole.EMPLOYEE);
        MenuPage menuPage = new MenuPage(driver()).open();
        menuPage.addFirstAvailableItem();
        Assert.assertTrue(menuPage.cartCount() > 0, "Cart count should increase after adding item.");
        return menuPage.checkoutFromDrawer();
    }

    protected CartPage prepareCartPage() {
        TestDataService.createAvailableMenuItem();
        signInAs(UserRole.EMPLOYEE);
        MenuPage menuPage = new MenuPage(driver()).open();
        menuPage.addFirstAvailableItem();
        return new CartPage(driver()).open();
    }
}
