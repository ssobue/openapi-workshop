package dev.sobue.openapi.workshop.api.error;

public final class ProblemTypes {
    public static final String INVALID_REQUEST = "https://example.com/problems/invalid-request";
    public static final String NOT_FOUND = "https://example.com/problems/not-found";
    public static final String INTERNAL_ERROR = "https://example.com/problems/internal-error";

    private ProblemTypes() {
    }
}
