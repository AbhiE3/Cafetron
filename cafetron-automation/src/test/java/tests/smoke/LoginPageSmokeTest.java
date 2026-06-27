package tests.smoke;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginPageSmokeTest extends BaseTest {

    @Test(groups = {"smoke", "sanity"}, description = "Verify Cafetron login page opens")
    public void verifyLoginPageOpens() {
        LoginPage loginPage = new LoginPage(driver()).open();

        Assert.assertTrue(loginPage.isLoaded(), "Login page should be visible.");
        Assert.assertTrue(driver().getCurrentUrl().contains("/login"), "Current URL should contain /login.");
    }
}

