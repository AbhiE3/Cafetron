package tests.defect;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.AdminVendorsPage;
import pages.WalletPage;
import tests.support.CafetronUiTest;
import utils.UserRole;

import java.time.Year;

public class AdminWalletVendorDefectRetest extends CafetronUiTest {

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-059 DF-025 Verify dashboard CSV controls")
    public void tc059DashboardCsvControlsVisibleAndEnabled() {
        openAs(UserRole.ADMIN, "/admin/dashboard");

        AdminDashboardPage dashboardPage = new AdminDashboardPage(driver());
        Assert.assertTrue(dashboardPage.hasCsvControls(), "Dashboard CSV controls should be visible.");
        dashboardPage.clickDailyCsv();
        Assert.assertFalse(driver().getCurrentUrl().contains("/login"), "CSV action should not log the admin out.");
    }

    @Test(groups = {"defect", "sanity", "regression", "uat"}, description = "TC-065 DF-027 Verify admin creates valid vendor")
    public void tc065AdminVendorCreationIncludesLoginCredentialSetup() {
        openAs(UserRole.ADMIN, "/admin/vendors");

        AdminVendorsPage vendorsPage = new AdminVendorsPage(driver());
        vendorsPage.openCreateForm();

        Assert.assertTrue(
                vendorsPage.hasCredentialSetupFields(),
                "Admin vendor creation should include employee ID/password or equivalent credential setup."
        );
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-091 DF-019 Verify wallet top-up upper boundary")
    public void tc091WalletTopUpUpperBoundary() {
        openAs(UserRole.EMPLOYEE, "/wallet");

        WalletPage walletPage = new WalletPage(driver());
        walletPage.topUp("999999999");

        Assert.assertTrue(walletPage.hasErrorMessage(), "Impractically high top-up should show validation error.");
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-092 DF-020 Verify wallet timestamp uses local application time")
    public void tc092WalletTimestampUsesLocalApplicationTime() {
        openAs(UserRole.EMPLOYEE, "/wallet");

        WalletPage walletPage = new WalletPage(driver());
        Assert.assertFalse(walletPage.updatedAtText().isBlank(), "Wallet updated timestamp should be shown.");
        Assert.assertTrue(
                walletPage.updatedAtText().contains(String.valueOf(Year.now().getValue())),
                "Wallet timestamp should show the current local application year."
        );
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-097 DF-005 Verify admin vendor phone rejects letters and excessive length")
    public void tc097AdminVendorPhoneRejectsInvalidValue() {
        openAs(UserRole.ADMIN, "/admin/vendors");

        AdminVendorsPage vendorsPage = new AdminVendorsPage(driver());
        vendorsPage.createVendor(
                "Auto Bad Phone " + System.currentTimeMillis(),
                "bad.phone." + System.currentTimeMillis() + "@cafetron.local",
                "ABCphone999999999999999999999999999999",
                "Automation"
        );

        Assert.assertTrue(vendorsPage.hasFormError(), "Invalid phone should be rejected with validation feedback.");
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-098 DF-020 Verify admin vendor created timestamp uses local application time")
    public void tc098AdminVendorCreatedTimestampUsesLocalTime() {
        String vendorName = "Auto Timestamp Vendor " + System.currentTimeMillis();
        openAs(UserRole.ADMIN, "/admin/vendors");

        AdminVendorsPage vendorsPage = new AdminVendorsPage(driver());
        vendorsPage.createVendor(
                vendorName,
                "timestamp.vendor." + System.currentTimeMillis() + "@cafetron.local",
                "9999999999",
                "Automation"
        );

        Assert.assertTrue(vendorsPage.hasVendorNamed(vendorName), "Created vendor should appear in list.");
        Assert.assertTrue(
                vendorsPage.createdAtForVendorNamed(vendorName).contains(String.valueOf(Year.now().getValue())),
                "Created timestamp should show current local application year."
        );
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-099 DF-030 Verify admin can delete dummy vendor")
    public void tc099AdminCanDeleteDummyVendor() {
        String vendorName = "Auto Delete Vendor " + System.currentTimeMillis();
        openAs(UserRole.ADMIN, "/admin/vendors");

        AdminVendorsPage vendorsPage = new AdminVendorsPage(driver());
        vendorsPage.createVendor(
                vendorName,
                "delete.vendor." + System.currentTimeMillis() + "@cafetron.local",
                "9999999999",
                "Automation"
        );
        Assert.assertTrue(vendorsPage.hasVendorNamed(vendorName), "Disposable vendor should be created before delete.");

        vendorsPage.deleteVendorNamed(vendorName);

        Assert.assertTrue(vendorsPage.hasToast() || !vendorsPage.hasVendorNamed(vendorName), "Admin delete should complete or show clear feedback.");
    }
}
