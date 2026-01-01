package dev.sobue.openapi.workshop.api.model;

import java.math.BigDecimal;

public record OrderLineInput(String productId, Integer quantity, BigDecimal unitPrice) {}
