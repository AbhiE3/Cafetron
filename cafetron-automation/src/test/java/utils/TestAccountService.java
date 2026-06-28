package utils;

import org.testng.SkipException;

import java.util.EnumMap;
import java.util.Map;

public final class TestAccountService {

    private static final Map<UserRole, AuthSession> SESSION_CACHE = new EnumMap<>(UserRole.class);

    private TestAccountService() {
    }

    public static synchronized AuthSession sessionFor(UserRole role) {
        AuthSession cached = SESSION_CACHE.get(role);
        if (cached != null) {
            return cached;
        }

        TestAccount account = accountFor(role);
        AuthSession session = ApiClient.login(account.employeeId(), account.password())
                .orElseGet(() -> createThenLogin(account));

        SESSION_CACHE.put(role, session);
        return session;
    }

    public static TestAccount accountFor(UserRole role) {
        return switch (role) {
            case EMPLOYEE -> new TestAccount(
                    role,
                    ConfigReader.get("employeeName", "E2E Test Employee"),
                    ConfigReader.get("employeeEmail", "e2e.employee@cafetron.local"),
                    ConfigReader.get("employeeId", "E2E1001"),
                    ConfigReader.get("employeePassword", TestDataFactory.defaultPassword()),
                    ConfigReader.get("employeeDepartment", "QA")
            );
            case VENDOR -> new TestAccount(
                    role,
                    ConfigReader.get("vendorName", "E2E Vendor User"),
                    ConfigReader.get("vendorEmail", "e2e.vendor@cafetron.local"),
                    ConfigReader.get("vendorEmployeeId", "E2EVENDOR1"),
                    ConfigReader.get("vendorPassword", TestDataFactory.defaultPassword()),
                    ConfigReader.get("vendorDepartment", "QA")
            );
            case ADMIN -> new TestAccount(
                    role,
                    ConfigReader.get("adminName", "E2E Admin User"),
                    ConfigReader.get("adminEmail", "e2e.admin@cafetron.local"),
                    ConfigReader.get("adminEmployeeId", "E2EADMIN1"),
                    ConfigReader.get("adminPassword", TestDataFactory.defaultPassword()),
                    ConfigReader.get("adminDepartment", "QA")
            );
        };
    }

    private static AuthSession createThenLogin(TestAccount account) {
        if (!ConfigReader.getBoolean("autoCreateTestAccounts", true)) {
            throw missingAccount(account);
        }

        ApiClient.register(account);
        return ApiClient.login(account.employeeId(), account.password())
                .orElseThrow(() -> missingAccount(account));
    }

    private static SkipException missingAccount(TestAccount account) {
        return new SkipException(
                "Unable to prepare " + account.role() + " account "
                        + account.employeeId()
                        + ". Provide credentials with -D"
                        + account.role().name().toLowerCase()
                        + "EmployeeId/-D"
                        + account.role().name().toLowerCase()
                        + "Password or enable test account creation."
        );
    }
}
