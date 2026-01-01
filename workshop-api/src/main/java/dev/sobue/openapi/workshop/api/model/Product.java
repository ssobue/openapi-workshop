package dev.sobue.openapi.workshop.api.model;

import java.math.BigDecimal;

public record Product(String id, String name, BigDecimal price) {}
