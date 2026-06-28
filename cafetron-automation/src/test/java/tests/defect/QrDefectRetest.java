package tests.defect;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.QrDisplayPage;
import pages.QrScannerPage;
import pages.QrUploadPage;
import tests.support.CafetronUiTest;
import utils.TestDataService;
import utils.UserRole;

public class QrDefectRetest extends CafetronUiTest {

    @Test(groups = {"defect", "sanity", "regression", "integration"}, description = "TC-042 DF-026 Verify QR upload page controls and upload readiness")
    public void tc042QrUploadControlsAndReadiness() {
        openAs(UserRole.VENDOR, "/pickup/upload");

        QrUploadPage qrUploadPage = new QrUploadPage(driver());
        Assert.assertTrue(qrUploadPage.isLoaded(), "QR upload page should load.");
        Assert.assertTrue(qrUploadPage.hasUploadControls(), "QR upload file chooser and submit button should be visible.");
    }

    @Test(groups = {"defect", "sanity", "regression", "integration"}, description = "TC-044 DF-004 Verify scanner start gives feedback")
    public void tc044ScannerStartGivesFeedback() {
        openAs(UserRole.VENDOR, "/vendor/scanner");

        QrScannerPage scannerPage = new QrScannerPage(driver());
        scannerPage.startScanner();

        Assert.assertTrue(scannerPage.hasScannerFeedback(), "Scanner start should show preview, result, prompt placeholder, or clear error.");
    }

    @Test(groups = {"defect", "sanity", "regression", "usability"}, description = "TC-096 DF-023 Verify pickup QR page has back navigation")
    public void tc096PickupQrPageHasBackNavigation() {
        long orderId = TestDataService.placeEmployeeOrder(1);
        openAs(UserRole.EMPLOYEE, "/pickup/qr/" + orderId);

        QrDisplayPage qrDisplayPage = new QrDisplayPage(driver());
        Assert.assertTrue(qrDisplayPage.isLoaded(), "Pickup QR page should load.");
        Assert.assertTrue(qrDisplayPage.hasQrOrDisabledMessage(), "QR page should show QR content or disabled message.");
        Assert.assertTrue(qrDisplayPage.hasBackNavigation(), "Pickup QR page should provide back navigation.");
    }
}
