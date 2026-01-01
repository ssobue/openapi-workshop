package dev.sobue.openapi.workshop.api.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ProblemTypes.NOT_FOUND, "Not found", message);
    }
}
