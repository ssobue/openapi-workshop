package dev.sobue.openapi.workshop.api.model;

public record ProblemDetails(String type, String title, int status, String detail, String instance) {}
