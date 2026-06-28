package utils;

import org.testng.SkipException;

public final class TestDataService {

    private TestDataService() {
    }

    public static TestMenuItem createAvailableMenuItem() {
        AuthSession vendorSession = TestAccountService.sessionFor(UserRole.VENDOR);
        long vendorId = currentVendorId(vendorSession);
        String name = "Auto Meal " + System.currentTimeMillis();
        return createMenuItem(vendorSession, vendorId, name, 120.0, 20, "Automation");
    }

    public static TestMenuItem createAvailableMenuItem(String name) {
        AuthSession vendorSession = TestAccountService.sessionFor(UserRole.VENDOR);
        long vendorId = currentVendorId(vendorSession);
        return createMenuItem(vendorSession, vendorId, name, 120.0, 20, "Automation");
    }

    public static TestMenuItem createLowStockMenuItem() {
        AuthSession vendorSession = TestAccountService.sessionFor(UserRole.VENDOR);
        long vendorId = currentVendorId(vendorSession);
        String name = "Auto Low Stock " + System.currentTimeMillis();
        return createMenuItem(vendorSession, vendorId, name, 50.0, 1, "Automation");
    }

    public static TestMenuItem createZeroStockMenuItem() {
        AuthSession vendorSession = TestAccountService.sessionFor(UserRole.VENDOR);
        long vendorId = currentVendorId(vendorSession);
        String name = "Auto Zero Stock " + System.currentTimeMillis();
        return createMenuItem(vendorSession, vendorId, name, 50.0, 0, "Automation");
    }

    public static long placeEmployeeOrder(int quantity) {
        TestMenuItem menuItem = createAvailableMenuItem();
        AuthSession employeeSession = TestAccountService.sessionFor(UserRole.EMPLOYEE);
        String body = "{"
                + "\"pickupSlot\":\"Lunch (12:30 PM - 01:00 PM)\","
                + "\"pickupSlotTime\":\"12:30\","
                + "\"location\":\"Cafeteria Block A\","
                + "\"pickupTimeZone\":\"Asia/Kolkata\","
                + "\"items\":[{\"menuItemId\":" + menuItem.id() + ",\"quantity\":" + quantity + "}]"
                + "}";
        ApiClient.ApiResponse response = ApiClient.post("/orders", body, employeeSession);
        if (!response.isSuccess()) {
            throw new SkipException("Unable to prepare employee order through API: " + response.body());
        }
        long orderId = ApiClient.longField(response.body(), "orderId");
        if (orderId < 1) {
            throw new SkipException("Order API did not return an orderId: " + response.body());
        }
        return orderId;
    }

    public static void closeOrderingWindowIfOpen() {
        AuthSession adminSession = TestAccountService.sessionFor(UserRole.ADMIN);
        ApiClient.ApiResponse current = ApiClient.get("/admin/config", adminSession);
        if (!current.isSuccess()) {
            throw new SkipException("Unable to read admin operations config: " + current.body());
        }
        if (current.body().contains("\"windowOpen\":true")) {
            ApiClient.post("/admin/window/toggle", "{}", adminSession);
        }
    }

    public static void openOrderingWindowIfClosed() {
        AuthSession adminSession = TestAccountService.sessionFor(UserRole.ADMIN);
        ApiClient.ApiResponse current = ApiClient.get("/admin/config", adminSession);
        if (current.isSuccess() && current.body().contains("\"windowOpen\":false")) {
            ApiClient.post("/admin/window/toggle", "{}", adminSession);
        }
    }

    public static void setCutoff(String time) {
        AuthSession adminSession = TestAccountService.sessionFor(UserRole.ADMIN);
        ApiClient.ApiResponse response = ApiClient.put("/admin/cutoff", "{\"time\":\"" + time + "\"}", adminSession);
        if (!response.isSuccess()) {
            throw new SkipException("Unable to set admin cutoff through API: " + response.body());
        }
    }

    private static long currentVendorId(AuthSession vendorSession) {
        ApiClient.ApiResponse response = ApiClient.get("/vendors/active", vendorSession);
        if (!response.isSuccess()) {
            throw new SkipException("Unable to read active vendor for vendor account: " + response.body());
        }
        long vendorId = ApiClient.longField(response.body(), "id");
        if (vendorId < 1) {
            throw new SkipException("Vendor account has no active vendor profile: " + response.body());
        }
        return vendorId;
    }

    private static TestMenuItem createMenuItem(
            AuthSession session,
            long vendorId,
            String name,
            double price,
            int stock,
            String foodType
    ) {
        String body = "{"
                + "\"itemName\":\"" + name + "\","
                + "\"price\":" + price + ","
                + "\"stock\":" + stock + ","
                + "\"foodType\":\"" + foodType + "\","
                + "\"vendorId\":" + vendorId
                + "}";
        ApiClient.ApiResponse response = ApiClient.post("/menu", body, session);
        if (!response.isSuccess()) {
            throw new SkipException("Unable to create menu item through API: " + response.body());
        }
        long itemId = ApiClient.longField(response.body(), "id");
        if (itemId < 1) {
            throw new SkipException("Menu item API did not return an item id: " + response.body());
        }
        return new TestMenuItem(itemId, name);
    }

    public record TestMenuItem(long id, String name) {
    }
}
