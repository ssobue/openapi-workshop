package dev.sobue.openapi.workshop.api.model;

import java.time.Instant;

public record Order(String id, String status, Instant createdAt) {}
