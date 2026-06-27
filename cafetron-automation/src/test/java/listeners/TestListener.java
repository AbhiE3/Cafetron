package listeners;

import base.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ConfigReader;
import utils.ScreenshotUtils;

public class TestListener implements ITestListener {

    private static final ExtentReports REPORTER = ExtentReportManager.getReporter();
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = REPORTER.createTest(result.getMethod().getMethodName());
        String description = result.getMethod().getDescription();
        if (description != null && !description.isBlank()) {
            extentTest.info(description);
        }
        TEST.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TEST.get().pass("Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        TEST.get().fail(result.getThrowable());

        if (ConfigReader.getBoolean("screenshotOnFailure", true)) {
            attachScreenshot(result);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TEST.get().skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        REPORTER.flush();
        TEST.remove();
    }

    private void attachScreenshot(ITestResult result) {
        try {
            WebDriver driver = DriverFactory.getDriver();
            String screenshotPath = ScreenshotUtils.capture(driver, result.getMethod().getMethodName());
            TEST.get().addScreenCaptureFromPath(screenshotPath);
        } catch (Exception ignored) {
            TEST.get().warning("Screenshot capture was not available for this failure.");
        }
    }
}

