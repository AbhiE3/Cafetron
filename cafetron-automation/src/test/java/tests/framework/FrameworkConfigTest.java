package tests.framework;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class FrameworkConfigTest {

    @Test(groups = {"framework"}, description = "Verify automation framework configuration is loaded")
    public void verifyConfigLoads() {
        Assert.assertNotNull(ConfigReader.get("baseUrl"), "baseUrl should be configured");
        Assert.assertNotNull(ConfigReader.get("apiBaseUrl"), "apiBaseUrl should be configured");
        Assert.assertNotNull(ConfigReader.get("browser"), "browser should be configured");
    }
}

