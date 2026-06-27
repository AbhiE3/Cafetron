package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Path;

public final class ExtentReportManager {

    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getReporter() {
        if (extentReports == null) {
            Path reportPath = Path.of("target", "extent-report", "cafetron-automation-report.html");
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath.toString());
            sparkReporter.config().setReportName("Cafetron Automation Report");
            sparkReporter.config().setDocumentTitle("Cafetron Test Results");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Project", "Cafetron");
            extentReports.setSystemInfo("Framework", "Selenium Java TestNG");
        }
        return extentReports;
    }
}

