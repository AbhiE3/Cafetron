package utils;

public record TestAccount(
        UserRole role,
        String name,
        String email,
        String employeeId,
        String password,
        String department
) {
}
