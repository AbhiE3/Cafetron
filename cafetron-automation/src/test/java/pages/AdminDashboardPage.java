package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminDashboardPage extends BasePage {

    private static final By PAGE = By.id("admin-dashboard-page");
    private static final By TOTAL_ORDERS_KPI = By.id("admin-dashboard-total-orders-kpi");
    private static final By REVENUE_KPI = By.id("admin-dashboard-revenue-kpi");
    private static final By ITEMS_SOLD_KPI = By.id("admin-dashboard-items-sold-kpi");
    private static final By DECLINES_KPI = By.id("admin-dashboard-vendor-declines-kpi");
    private static final By RANGE_CSV = By.id("admin-dashboard-range-csv-btn");
    private static final By TOP_ITEMS_CSV = By.id("admin-dashboard-top-items-csv-btn");
    private static final By DAILY_CSV = By.id("admin-dashboard-daily-csv-btn");

    public AdminDashboardPage(WebDriver driver) {
        super(driver);
    }

    public AdminDashboardPage open() {
        openPath("/admin/dashboard");
        visible(PAGE);
        return this;
    }

    public boolean isLoaded() {
        return isVisible(PAGE);
    }

    public boolean hasSummaryCards() {
        return isVisible(TOTAL_ORDERS_KPI)
                && isVisible(REVENUE_KPI)
                && isVisible(ITEMS_SOLD_KPI)
                && isVisible(DECLINES_KPI);
    }

    public boolean hasCsvControls() {
        return isVisible(RANGE_CSV) && isVisible(TOP_ITEMS_CSV) && isVisible(DAILY_CSV);
    }

    public void clickDailyCsv() {
        click(DAILY_CSV);
    }
}
