package dev.sobue.openapi.workshop.api.model;

import java.math.BigDecimal;

public record ProductPatch(String name, BigDecimal price) {}
