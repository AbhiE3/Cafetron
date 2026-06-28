package tests.defect;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.AdminOperationsPage;
import pages.CartPage;
import pages.CheckoutPage;
import pages.MenuPage;
import tests.support.CafetronUiTest;
import utils.TestDataService;
import utils.UserRole;

public class OrderingCutoffStockDefectRetest extends CafetronUiTest {

    @AfterMethod(alwaysRun = true)
    public void restoreOrderingWindow() {
        TestDataService.openOrderingWindowIfClosed();
    }

    @Test(groups = {"defect", "sanity", "regression", "integration"}, description = "TC-030 DF-003 Verify cutoff blocks order after cutoff")
    public void tc030CutoffBlocksOrderAfterCutoff() {
        TestDataService.setCutoff("00:00");
        CheckoutPage checkoutPage = prepareCartAndOpenCheckout();
        checkoutPage.useCardsView();
        checkoutPage.selectFirstAvailableCardSlot();
        checkoutPage.goToPlaceOrderCard();
        checkoutPage.placeOrderFromCards();

        Assert.assertTrue(checkoutPage.hasVisibleOrderError(), "Order should be blocked with visible feedback after cutoff.");
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-062 DF-031 Verify cutoff status after cutoff has passed")
    public void tc062CutoffStatusAfterCutoffPassed() {
        TestDataService.setCutoff("00:00");
        openAs(UserRole.ADMIN, "/admin/operations");

        AdminOperationsPage operationsPage = new AdminOperationsPage(driver());
        Assert.assertTrue(
                operationsPage.cutoffStatus().toLowerCase().contains("reached")
                        || operationsPage.orderingAllowed().equalsIgnoreCase("No"),
                "Operations page should show cutoff reached and ordering not allowed."
        );
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-063 DF-032 Verify cutoff update validation")
    public void tc063CutoffUpdateValidation() {
        openAs(UserRole.ADMIN, "/admin/operations");

        AdminOperationsPage operationsPage = new AdminOperationsPage(driver());
        operationsPage.clearCutoffInput();

        Assert.assertTrue(operationsPage.isCutoffSaveDisabled(), "Blank cutoff should disable save or show validation.");
    }

    @Test(groups = {"defect", "sanity", "regression", "integration"}, description = "TC-093 DF-021 Verify employee order is blocked when admin closes ordering window")
    public void tc093EmployeeOrderBlockedWhenAdminClosesWindow() {
        TestDataService.closeOrderingWindowIfOpen();
        CheckoutPage checkoutPage = prepareCartAndOpenCheckout();
        checkoutPage.useCardsView();
        checkoutPage.selectFirstAvailableCardSlot();
        checkoutPage.goToPlaceOrderCard();
        checkoutPage.placeOrderFromCards();

        Assert.assertTrue(checkoutPage.hasVisibleOrderError(), "Closed ordering window should block order placement.");
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-094 DF-022 Verify cart prevents quantity higher than stock")
    public void tc094CartPreventsQuantityHigherThanStock() {
        TestDataService.TestMenuItem lowStockItem = TestDataService.createLowStockMenuItem();
        signInAs(UserRole.EMPLOYEE);
        MenuPage menuPage = new MenuPage(driver()).open();
        menuPage.search(lowStockItem.name());
        menuPage.addFirstAvailableItem();

        CartPage cartPage = new CartPage(driver()).open();
        cartPage.increaseFirstItem();

        Assert.assertTrue(cartPage.firstQuantity() <= 1, "Cart quantity should not exceed available stock.");
    }

    @Test(groups = {"defect", "sanity", "regression", "usability"}, description = "TC-095 DF-022 Verify over-stock checkout error appears in Cards flow")
    public void tc095OverStockCheckoutErrorVisibleInCardsFlow() {
        TestDataService.TestMenuItem lowStockItem = TestDataService.createLowStockMenuItem();
        signInAs(UserRole.EMPLOYEE);
        MenuPage menuPage = new MenuPage(driver()).open();
        menuPage.search(lowStockItem.name());
        menuPage.addFirstAvailableItem();

        CartPage cartPage = new CartPage(driver()).open();
        cartPage.increaseFirstItem();
        CheckoutPage checkoutPage = cartPage.checkout();
        checkoutPage.useCardsView();
        checkoutPage.selectFirstAvailableCardSlot();
        checkoutPage.goToPlaceOrderCard();
        checkoutPage.placeOrderFromCards();

        Assert.assertTrue(checkoutPage.hasVisibleOrderError(), "Cards checkout should show stock error next to place order.");
    }
}
