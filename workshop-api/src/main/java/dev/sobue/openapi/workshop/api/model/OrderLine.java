package dev.sobue.openapi.workshop.api.model;

import java.math.BigDecimal;

public record OrderLine(String id, String orderId, String productId, Integer quantity, BigDecimal unitPrice) {}
