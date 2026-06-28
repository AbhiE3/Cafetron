package tests.defect;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.VendorMenuPage;
import tests.support.CafetronUiTest;
import utils.TestDataService;
import utils.UserRole;

public class VendorMenuDefectRetest extends CafetronUiTest {

    @Test(groups = {"defect", "sanity", "regression", "usability"}, description = "TC-053 DF-024 Verify vendor item form required validation")
    public void tc053VendorItemFormDoesNotShowRequiredValidationImmediately() {
        openAs(UserRole.VENDOR, "/menu/manage");

        VendorMenuPage vendorMenuPage = new VendorMenuPage(driver());
        vendorMenuPage.openCreateForm();

        Assert.assertFalse(
                vendorMenuPage.hasRequiredValidation(),
                "Create form should not show required validation before user input or save."
        );
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-081 DF-010 Verify vendor queue route is implemented")
    public void tc081VendorQueueRouteImplemented() {
        openAs(UserRole.VENDOR, "/vendor/queue");

        Assert.assertTrue(driver().getCurrentUrl().contains("/vendor/queue"), "Vendor queue route should be reachable.");
        Assert.assertTrue(
                driver().findElements(By.cssSelector(".pending-page")).isEmpty(),
                "Vendor queue route should show implemented queue content, not a placeholder."
        );
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-082 DF-011 Verify vendor cannot create duplicate menu item name for same vendor")
    public void tc082VendorCannotCreateDuplicateItemName() {
        String duplicateName = "Auto Duplicate " + System.currentTimeMillis();
        TestDataService.createAvailableMenuItem(duplicateName);
        openAs(UserRole.VENDOR, "/menu/manage");

        VendorMenuPage vendorMenuPage = new VendorMenuPage(driver());
        vendorMenuPage.createItem(duplicateName, "120", "5", "Automation");

        Assert.assertTrue(vendorMenuPage.hasFormError(), "Duplicate item name should be rejected with form feedback.");
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-083 DF-012 Verify vendor menu item stock zero validation")
    public void tc083VendorMenuItemStockZeroValidation() {
        openAs(UserRole.VENDOR, "/menu/manage");

        VendorMenuPage vendorMenuPage = new VendorMenuPage(driver());
        vendorMenuPage.createItem("Auto Zero Ui " + System.currentTimeMillis(), "50", "0", "Automation");

        Assert.assertTrue(vendorMenuPage.hasFormError(), "Creating zero-stock item should be blocked with validation.");
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-084 DF-013 Verify toggling zero-stock vendor item does not logout vendor")
    public void tc084TogglingZeroStockItemDoesNotLogoutVendor() {
        TestDataService.TestMenuItem item = TestDataService.createZeroStockMenuItem();
        openAs(UserRole.VENDOR, "/menu/manage");

        VendorMenuPage vendorMenuPage = new VendorMenuPage(driver());
        vendorMenuPage.setStatusFilter("unavailable");
        vendorMenuPage.toggleItemNamed(item.name());

        Assert.assertFalse(driver().getCurrentUrl().contains("/login"), "Vendor should remain logged in after zero-stock toggle.");
        Assert.assertTrue(vendorMenuPage.hasToast() || vendorMenuPage.hasFormError(), "Zero-stock toggle should show clear feedback.");
    }

    @Test(groups = {"defect", "sanity", "regression", "usability"}, description = "TC-085 DF-014 Verify vendor does not see unusable delete action")
    public void tc085VendorDoesNotSeeUnusableDeleteAction() {
        TestDataService.createAvailableMenuItem();
        openAs(UserRole.VENDOR, "/menu/manage");

        VendorMenuPage vendorMenuPage = new VendorMenuPage(driver());
        Assert.assertFalse(
                vendorMenuPage.firstDeleteActionVisible() && vendorMenuPage.firstDeleteActionDisabled(),
                "Vendor should not see a disabled delete action without useful context."
        );
    }

    @Test(groups = {"defect", "sanity", "regression", "usability"}, description = "TC-086 DF-015 Verify admin menu vendor/status dropdown options are readable")
    public void tc086AdminMenuDropdownControlsVisible() {
        TestDataService.createAvailableMenuItem();
        openAs(UserRole.ADMIN, "/menu/manage");

        VendorMenuPage vendorMenuPage = new VendorMenuPage(driver());
        Assert.assertTrue(vendorMenuPage.hasReadableFilters(), "Admin vendor/status filters should be visible and usable.");
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-087 DF-016 Verify admin menu vendor filter returns vendor items")
    public void tc087AdminMenuShowsVendorItems() {
        TestDataService.TestMenuItem item = TestDataService.createAvailableMenuItem();
        openAs(UserRole.ADMIN, "/menu/manage");

        VendorMenuPage vendorMenuPage = new VendorMenuPage(driver());
        Assert.assertTrue(vendorMenuPage.hasItemNamed(item.name()), "Admin menu should list item created for vendor.");
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-088 DF-017 Verify admin edit menu save does not logout")
    public void tc088AdminEditMenuSaveDoesNotLogout() {
        TestDataService.TestMenuItem item = TestDataService.createAvailableMenuItem();
        openAs(UserRole.ADMIN, "/menu/manage");

        VendorMenuPage vendorMenuPage = new VendorMenuPage(driver());
        vendorMenuPage.editItemNamed(item.name());
        vendorMenuPage.saveOpenForm();

        Assert.assertFalse(driver().getCurrentUrl().contains("/login"), "Admin should remain logged in after saving menu edit.");
    }

    @Test(groups = {"defect", "sanity", "regression"}, description = "TC-089 DF-029 Verify admin can delete menu item")
    public void tc089AdminCanDeleteMenuItem() {
        TestDataService.TestMenuItem item = TestDataService.createAvailableMenuItem();
        openAs(UserRole.ADMIN, "/menu/manage");

        VendorMenuPage vendorMenuPage = new VendorMenuPage(driver());
        vendorMenuPage.deleteItemNamed(item.name());

        Assert.assertTrue(vendorMenuPage.hasToast() || !vendorMenuPage.hasItemNamed(item.name()), "Admin delete should complete or show clear feedback.");
    }
}
