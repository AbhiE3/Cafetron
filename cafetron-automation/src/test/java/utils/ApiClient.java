package utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ApiClient {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(8);
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(REQUEST_TIMEOUT)
            .build();

    private ApiClient() {
    }

    public static boolean isReachable(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(3))
                    .GET()
                    .build();
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() < 500;
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return false;
        }
    }

    public static Optional<AuthSession> login(String employeeId, String password) {
        String body = "{\"employeeId\":\"" + json(employeeId) + "\",\"password\":\"" + json(password) + "\"}";
        ApiResponse response = post("/auth/login", body, null);
        if (!response.isSuccess()) {
            return Optional.empty();
        }
        return Optional.of(sessionFrom(response.body()));
    }

    public static Optional<AuthSession> register(TestAccount account) {
        String body = "{"
                + "\"name\":\"" + json(account.name()) + "\","
                + "\"email\":\"" + json(account.email()) + "\","
                + "\"password\":\"" + json(account.password()) + "\","
                + "\"employeeId\":\"" + json(account.employeeId()) + "\","
                + "\"department\":\"" + json(account.department()) + "\","
                + "\"role\":\"" + account.role().name() + "\""
                + "}";
        ApiResponse response = post("/auth/register", body, null);
        if (!response.isSuccess()) {
            return Optional.empty();
        }
        return Optional.of(sessionFrom(response.body()));
    }

    public static ApiResponse get(String path, AuthSession session) {
        HttpRequest.Builder builder = request(path).GET();
        authorize(builder, session);
        return send(builder.build());
    }

    public static ApiResponse post(String path, String jsonBody, AuthSession session) {
        HttpRequest.Builder builder = request(path)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));
        authorize(builder, session);
        return send(builder.build());
    }

    public static ApiResponse put(String path, String jsonBody, AuthSession session) {
        HttpRequest.Builder builder = request(path)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody));
        authorize(builder, session);
        return send(builder.build());
    }

    public static String textField(String json, String fieldName) {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(fieldName) + "\"\\s*:\\s*\"([^\"]*)\"")
                .matcher(json == null ? "" : json);
        return matcher.find() ? matcher.group(1) : "";
    }

    public static long longField(String json, String fieldName) {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(fieldName) + "\"\\s*:\\s*(\\d+)")
                .matcher(json == null ? "" : json);
        return matcher.find() ? Long.parseLong(matcher.group(1)) : -1L;
    }

    private static HttpRequest.Builder request(String path) {
        String apiBaseUrl = ConfigReader.get("apiBaseUrl", "http://localhost:8081/api").replaceAll("/+$", "");
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        return HttpRequest.newBuilder(URI.create(apiBaseUrl + normalizedPath))
                .timeout(REQUEST_TIMEOUT);
    }

    private static void authorize(HttpRequest.Builder builder, AuthSession session) {
        if (session != null && session.token() != null && !session.token().isBlank()) {
            builder.header("Authorization", "Bearer " + session.token());
        }
    }

    private static ApiResponse send(HttpRequest request) {
        try {
            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return new ApiResponse(response.statusCode(), response.body());
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return new ApiResponse(0, e.getMessage());
        }
    }

    private static AuthSession sessionFrom(String json) {
        return new AuthSession(
                textField(json, "token"),
                textField(json, "email"),
                textField(json, "name"),
                textField(json, "role")
        );
    }

    private static String json(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }

    public record ApiResponse(int statusCode, String body) {
        public boolean isSuccess() {
            return statusCode >= 200 && statusCode < 300;
        }
    }
}
