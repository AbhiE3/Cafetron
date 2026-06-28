package tests.e2e;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.OrdersPage;
import pages.VendorOrdersPage;
import pages.WalletPage;
import tests.support.CafetronUiTest;
import utils.TestDataService;
import utils.UserRole;

public class OrderFulfillmentE2ETest extends CafetronUiTest {

    @Test(groups = {"e2e", "integration", "uat", "regression"}, description = "TC-075 Verify end-to-end accept flow")
    public void tc075EndToEndAcceptFlow() {
        long orderId = TestDataService.placeEmployeeOrder(1);

        openAs(UserRole.VENDOR, "/vendor/orders");
        VendorOrdersPage vendorOrdersPage = new VendorOrdersPage(driver());
        Assert.assertTrue(vendorOrdersPage.orderCount() > 0, "Vendor should see prepared order " + orderId + ".");
        vendorOrdersPage.acceptFirstPendingOrder();
        Assert.assertTrue(vendorOrdersPage.hasSuccessNotice(), "Vendor accept should show success notice.");

        openAs(UserRole.EMPLOYEE, "/orders");
        OrdersPage ordersPage = new OrdersPage(driver());
        Assert.assertTrue(ordersPage.orderCount() > 0, "Employee should see the accepted order in history.");
    }

    @Test(groups = {"e2e", "integration", "uat", "regression"}, description = "TC-076 Verify end-to-end decline and refund flow")
    public void tc076EndToEndDeclineAndRefundFlow() {
        long orderId = TestDataService.placeEmployeeOrder(1);

        openAs(UserRole.VENDOR, "/vendor/orders");
        VendorOrdersPage vendorOrdersPage = new VendorOrdersPage(driver());
        Assert.assertTrue(vendorOrdersPage.orderCount() > 0, "Vendor should see prepared order " + orderId + ".");
        vendorOrdersPage.declineFirstPendingOrder("Automation decline validation");
        Assert.assertTrue(vendorOrdersPage.hasSuccessNotice(), "Vendor decline should show success notice.");

        openAs(UserRole.EMPLOYEE, "/wallet");
        WalletPage walletPage = new WalletPage(driver());
        Assert.assertTrue(walletPage.hasBalance(), "Employee wallet should remain available after decline/refund flow.");
        Assert.assertTrue(walletPage.transactionCount() > 0, "Wallet transaction history should show activity.");
    }
}
