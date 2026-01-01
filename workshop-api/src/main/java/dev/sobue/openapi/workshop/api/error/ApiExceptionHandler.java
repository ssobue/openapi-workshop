package dev.sobue.openapi.workshop.api.error;

import dev.sobue.openapi.workshop.api.model.ProblemDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    private static final MediaType PROBLEM_JSON = MediaType.valueOf("application/problem+json");

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ProblemDetails> handleApiException(ApiException ex, HttpServletRequest request) {
        ProblemDetails details = new ProblemDetails(
            ex.getType(),
            ex.getTitle(),
            ex.getStatus().value(),
            ex.getMessage(),
            instanceUri(request)
        );
        return ResponseEntity.status(ex.getStatus())
            .contentType(PROBLEM_JSON)
            .body(details);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetails> handleInvalidJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ProblemDetails details = new ProblemDetails(
            ProblemTypes.INVALID_REQUEST,
            "Invalid request",
            HttpStatus.BAD_REQUEST.value(),
            "Request body is missing or invalid",
            instanceUri(request)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(PROBLEM_JSON)
            .body(details);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> handleUnexpected(Exception ex, HttpServletRequest request) {
        ProblemDetails details = new ProblemDetails(
            ProblemTypes.INTERNAL_ERROR,
            "Internal server error",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Unexpected error",
            instanceUri(request)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(PROBLEM_JSON)
            .body(details);
    }

    private String instanceUri(HttpServletRequest request) {
        if (request == null) {
            return "about:blank";
        }
        return request.getRequestURL().toString();
    }
}
