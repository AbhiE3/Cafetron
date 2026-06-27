package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utils.ConfigReader;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount;

    @Override
    public boolean retry(ITestResult result) {
        int maxRetries = ConfigReader.getInt("retryCount", 0);
        if (retryCount < maxRetries) {
            retryCount++;
            return true;
        }
        return false;
    }
}

