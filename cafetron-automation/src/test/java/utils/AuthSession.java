package utils;

public record AuthSession(
        String token,
        String email,
        String name,
        String role
) {
    public String userJson() {
        return "{"
                + "\"token\":\"" + escape(token) + "\","
                + "\"email\":\"" + escape(email) + "\","
                + "\"name\":\"" + escape(name) + "\","
                + "\"role\":\"" + escape(role) + "\""
                + "}";
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
