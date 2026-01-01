package dev.sobue.openapi.workshop.api.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.sobue.openapi.workshop.api.model.ProblemDetails;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;

class ApiExceptionHandlerTest {
    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void handleApiExceptionReturnsProblemDetails() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/products");
        ApiException ex = new BadRequestException("name must not be empty");

        ResponseEntity<ProblemDetails> response = handler.handleApiException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MediaType.valueOf("application/problem+json"), response.getHeaders().getContentType());

        ProblemDetails body = response.getBody();
        assertNotNull(body);
        assertEquals(400, body.status());
        assertEquals("Invalid request", body.title());
        assertTrue(body.instance().contains("/products"));
    }

    @Test
    void handleInvalidJsonReturnsBadRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/products");
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("invalid", (HttpInputMessage) null);

        ResponseEntity<ProblemDetails> response = handler.handleInvalidJson(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(MediaType.valueOf("application/problem+json"), response.getHeaders().getContentType());
        ProblemDetails body = response.getBody();
        assertNotNull(body);
        assertEquals("Request body is missing or invalid", body.detail());
    }

    @Test
    void handleUnexpectedReturnsInternalError() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/orders");
        ResponseEntity<ProblemDetails> response = handler.handleUnexpected(new IllegalStateException("boom"), request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(MediaType.valueOf("application/problem+json"), response.getHeaders().getContentType());
        ProblemDetails body = response.getBody();
        assertNotNull(body);
        assertEquals("Internal server error", body.title());
    }
}
