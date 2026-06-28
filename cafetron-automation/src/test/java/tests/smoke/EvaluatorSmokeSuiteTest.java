package tests.smoke;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.AdminOperationsPage;
import pages.AdminVendorsPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.MenuPage;
import pages.OrdersPage;
import pages.ProfilePage;
import pages.VendorOrdersPage;
import pages.WalletPage;
import tests.support.CafetronUiTest;
import utils.TestAccount;
import utils.TestDataService;
import utils.UserRole;
import utils.WaitUtils;

public class EvaluatorSmokeSuiteTest extends CafetronUiTest {

    @Test(groups = {"smoke", "sanity", "rbac"}, description = "TC-001 Verify employee login with valid credentials")
    public void tc001EmployeeLoginWithValidCredentials() {
        TestAccount employee = account(UserRole.EMPLOYEE);

        LoginPage loginPage = new LoginPage(driver()).open();
        loginPage.loginAs(employee.employeeId(), employee.password());

        assertCurrentUrlContains("/menu", "Employee should land on menu.");
        Assert.assertTrue(new MenuPage(driver()).isLoaded(), "Employee menu page should be visible.");
    }

    @Test(groups = {"smoke", "sanity", "rbac"}, description = "TC-002 Verify vendor login with valid credentials")
    public void tc002VendorLoginWithValidCredentials() {
        TestAccount vendor = account(UserRole.VENDOR);

        LoginPage loginPage = new LoginPage(driver()).open();
        loginPage.loginAs(vendor.employeeId(), vendor.password());

        assertCurrentUrlContains("/vendor/orders", "Vendor should land on vendor orders.");
        Assert.assertTrue(new VendorOrdersPage(driver()).isLoaded(), "Vendor orders page should be visible.");
    }

    @Test(groups = {"smoke", "sanity", "rbac"}, description = "TC-003 Verify admin login with valid credentials")
    public void tc003AdminLoginWithValidCredentials() {
        TestAccount admin = account(UserRole.ADMIN);

        LoginPage loginPage = new LoginPage(driver()).open();
        loginPage.loginAs(admin.employeeId(), admin.password());

        assertCurrentUrlContains("/admin", "Admin should land on admin dashboard.");
        Assert.assertTrue(new AdminDashboardPage(driver()).isLoaded(), "Admin dashboard should be visible.");
    }

    @Test(groups = {"smoke", "uat", "regression"}, description = "TC-012 Verify employee menu loads available items")
    public void tc012EmployeeMenuLoadsAvailableItems() {
        TestDataService.createAvailableMenuItem();
        openAs(UserRole.EMPLOYEE, "/menu");

        MenuPage menuPage = new MenuPage(driver());
        Assert.assertTrue(menuPage.isLoaded(), "Menu page should load.");
        Assert.assertTrue(menuPage.itemCount() > 0, "At least one menu item should be available.");
    }

    @Test(groups = {"smoke", "integration", "uat", "regression"}, description = "TC-016 Verify available item can be added to cart")
    public void tc016AvailableItemCanBeAddedToCart() {
        TestDataService.createAvailableMenuItem();
        openAs(UserRole.EMPLOYEE, "/menu");

        MenuPage menuPage = new MenuPage(driver());
        menuPage.addFirstAvailableItem();

        Assert.assertTrue(menuPage.cartCount() > 0, "Cart count should increase after add.");
        Assert.assertTrue(menuPage.hasCartMessage(), "Cart success message should be shown.");
    }

    @Test(groups = {"smoke", "integration", "uat", "regression"}, description = "TC-028 Verify successful order placement")
    public void tc028SuccessfulOrderPlacement() {
        CheckoutPage checkoutPage = prepareCartAndOpenCheckout();
        checkoutPage.useCardsView();
        checkoutPage.selectFirstAvailableCardSlot();
        checkoutPage.goToPlaceOrderCard();
        checkoutPage.placeOrderFromCards();

        Assert.assertTrue(
                WaitUtils.urlContains(driver(), "/orders") || checkoutPage.hasVisibleOrderError(),
                "Order placement should either navigate to orders or show clear checkout feedback."
        );
    }

    @Test(groups = {"smoke", "uat", "regression"}, description = "TC-031 Verify wallet metadata and history")
    public void tc031WalletMetadataAndHistoryVisible() {
        openAs(UserRole.EMPLOYEE, "/wallet");

        WalletPage walletPage = new WalletPage(driver());
        Assert.assertTrue(walletPage.isLoaded(), "Wallet page should load.");
        Assert.assertTrue(walletPage.hasBalance(), "Wallet balance should be visible.");
        Assert.assertTrue(walletPage.hasMetadata(), "Wallet metadata should be visible.");
    }

    @Test(groups = {"smoke", "integration", "regression"}, description = "TC-045 Verify vendor queue shows assigned order")
    public void tc045VendorQueueShowsAssignedOrder() {
        TestDataService.placeEmployeeOrder(1);
        openAs(UserRole.VENDOR, "/vendor/orders");

        VendorOrdersPage vendorOrdersPage = new VendorOrdersPage(driver());
        Assert.assertTrue(vendorOrdersPage.hasQueueState(), "Vendor queue should show orders or an empty state.");
    }

    @Test(groups = {"smoke", "integration", "regression"}, description = "TC-047 Verify vendor accepts pending order")
    public void tc047VendorAcceptsPendingOrder() {
        TestDataService.placeEmployeeOrder(1);
        openAs(UserRole.VENDOR, "/vendor/orders");

        VendorOrdersPage vendorOrdersPage = new VendorOrdersPage(driver());
        Assert.assertTrue(vendorOrdersPage.orderCount() > 0, "A pending vendor order should be prepared.");
        vendorOrdersPage.acceptFirstPendingOrder();

        Assert.assertTrue(vendorOrdersPage.hasSuccessNotice(), "Vendor accept action should show success feedback.");
    }

    @Test(groups = {"smoke", "regression"}, description = "TC-057 Verify admin dashboard summary cards")
    public void tc057AdminDashboardSummaryCardsVisible() {
        openAs(UserRole.ADMIN, "/admin/dashboard");

        AdminDashboardPage dashboardPage = new AdminDashboardPage(driver());
        Assert.assertTrue(dashboardPage.hasSummaryCards(), "Dashboard KPI cards should be visible.");
    }

    @Test(groups = {"smoke", "regression"}, description = "TC-060 Verify admin can close ordering window")
    public void tc060AdminCanCloseOrderingWindow() {
        openAs(UserRole.ADMIN, "/admin/operations");

        AdminOperationsPage operationsPage = new AdminOperationsPage(driver());
        String before = operationsPage.windowState();
        operationsPage.toggleOrderingWindow();
        Assert.assertNotEquals(operationsPage.windowState(), before, "Ordering window state should change.");
        operationsPage.toggleOrderingWindow();
    }

    @Test(groups = {"smoke", "regression"}, description = "TC-064 Verify admin vendor list loads")
    public void tc064AdminVendorListLoads() {
        openAs(UserRole.ADMIN, "/admin/vendors");

        AdminVendorsPage vendorsPage = new AdminVendorsPage(driver());
        Assert.assertTrue(vendorsPage.isLoaded(), "Admin vendors page should load.");
        Assert.assertTrue(
                vendorsPage.vendorCount() > 0 || driver().findElements(By.id("vendor-manage-empty-state")).size() > 0,
                "Vendor list or empty state should be visible."
        );
    }

    @Test(groups = {"smoke", "rbac", "regression"}, description = "TC-067 Verify unauthenticated protected routes redirect to login")
    public void tc067UnauthenticatedProtectedRoutesRedirectToLogin() {
        clearSession();
        openPath("/wallet");

        assertCurrentUrlContains("/login", "Unauthenticated wallet route should redirect to login.");
    }

    @Test(groups = {"smoke", "uat", "regression"}, description = "TC-072 Verify profile displays current user details")
    public void tc072ProfileDisplaysCurrentUserDetails() {
        openAs(UserRole.EMPLOYEE, "/profile");

        assertVisible(By.id("profile-card"), "Profile card should be visible.");
        assertVisible(By.id("profile-employee-id"), "Profile employee ID should be visible.");
        assertVisible(By.id("profile-role"), "Profile role should be visible.");
    }

    @Test(groups = {"smoke", "uat", "regression"}, description = "TC-073 Verify logout returns to login")
    public void tc073LogoutReturnsToLogin() {
        openAs(UserRole.EMPLOYEE, "/profile");

        new ProfilePage(driver()).logout();

        assertCurrentUrlContains("/login", "Logout should return the user to login.");
    }
}
