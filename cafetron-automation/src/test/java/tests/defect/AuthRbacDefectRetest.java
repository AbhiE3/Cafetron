package tests.defect;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;
import pages.VendorOrdersPage;
import tests.support.CafetronUiTest;
import utils.UserRole;

public class AuthRbacDefectRetest extends CafetronUiTest {

    @Test(groups = {"defect", "sanity", "regression", "rbac"}, description = "TC-069 DF-028 Verify admin and vendor users are blocked from employee-only pages")
    public void tc069NonEmployeeUsersBlockedFromEmployeeOnlyPages() {
        openAs(UserRole.VENDOR, "/menu");
        Assert.assertFalse(
                driver().getCurrentUrl().contains("/menu")
                        && !driver().findElements(By.id("menu-browse-page")).isEmpty(),
                "Vendor should not see employee menu browsing page."
        );

        openAs(UserRole.ADMIN, "/menu");
        Assert.assertFalse(
                driver().getCurrentUrl().contains("/menu")
                        && !driver().findElements(By.id("menu-browse-page")).isEmpty(),
                "Admin should not see employee menu browsing page."
        );
    }

    @Test(groups = {"defect", "sanity", "regression", "rbac"}, description = "TC-077 DF-006 Verify logged-in employee opening /login is not shown login form")
    public void tc077AuthenticatedEmployeeCannotSeePublicLogin() {
        openAs(UserRole.EMPLOYEE, "/menu");
        openPath("/login");

        Assert.assertFalse(
                driver().getCurrentUrl().contains("/login")
                        && !driver().findElements(By.id("login-page")).isEmpty(),
                "Authenticated employee should be redirected away from public login."
        );
    }

    @Test(groups = {"defect", "sanity", "regression", "rbac"}, description = "TC-078 DF-007 Verify vendor cannot browse employee menu route")
    public void tc078VendorCannotBrowseEmployeeMenuRoute() {
        openAs(UserRole.VENDOR, "/menu");

        Assert.assertFalse(
                driver().getCurrentUrl().contains("/menu")
                        && !driver().findElements(By.id("menu-browse-page")).isEmpty(),
                "Vendor should be redirected to a vendor route instead of employee menu."
        );
    }

    @Test(groups = {"defect", "sanity", "regression", "rbac", "usability"}, description = "TC-079 DF-008 Verify vendor Orders Back button does not route to employee menu")
    public void tc079VendorOrdersBackDoesNotExposeEmployeeMenu() {
        openAs(UserRole.VENDOR, "/vendor/orders");
        new VendorOrdersPage(driver()).clickBackToMenu();

        Assert.assertFalse(
                driver().getCurrentUrl().contains("/menu")
                        && !driver().findElements(By.id("menu-browse-page")).isEmpty(),
                "Vendor Back action should not expose employee menu browsing."
        );
    }

    @Test(groups = {"defect", "sanity", "regression", "usability"}, description = "TC-080 DF-009 Verify logout is available from wallet page app shell")
    public void tc080LogoutAvailableFromWalletShell() {
        openAs(UserRole.EMPLOYEE, "/wallet");

        Assert.assertFalse(
                driver().findElements(By.cssSelector("[id$='logout-btn']")).isEmpty(),
                "An authenticated wallet page should expose a logout control."
        );
    }

    @Test(groups = {"defect", "sanity", "regression", "rbac"}, description = "TC-090 DF-018 Verify public registration does not allow admin self-registration")
    public void tc090PublicRegistrationDoesNotAllowAdminRole() {
        clearSession();
        RegisterPage registerPage = new RegisterPage(driver()).open();

        Assert.assertFalse(registerPage.hasAdminRoleOption(), "Public register page should not offer Admin role.");
    }
}
