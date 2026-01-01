package dev.sobue.openapi.workshop.api.error;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, ProblemTypes.INVALID_REQUEST, "Invalid request", message);
    }
}
