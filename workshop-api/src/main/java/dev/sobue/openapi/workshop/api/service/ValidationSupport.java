package dev.sobue.openapi.workshop.api.service;

import dev.sobue.openapi.workshop.api.error.BadRequestException;
import java.math.BigDecimal;

public final class ValidationSupport {
    private ValidationSupport() {
    }

    public static void requireBody(Object body, String name) {
        if (body == null) {
            throw new BadRequestException(name + " is required");
        }
    }

    public static void requireString(String name, String value) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(name + " must not be empty");
        }
    }

    public static void requireNonNegative(String name, BigDecimal value) {
        if (value == null) {
            throw new BadRequestException(name + " must not be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException(name + " must be zero or greater");
        }
    }

    public static void requirePositive(String name, Integer value) {
        if (value == null) {
            throw new BadRequestException(name + " must not be null");
        }
        if (value <= 0) {
            throw new BadRequestException(name + " must be greater than 0");
        }
    }

    public static void requirePatchFields(Object... fields) {
        for (Object field : fields) {
            if (field != null) {
                return;
            }
        }
        throw new BadRequestException("At least one field must be provided");
    }

    public static void requireStringIfPresent(String name, String value) {
        if (value != null && value.isBlank()) {
            throw new BadRequestException(name + " must not be empty");
        }
    }

    public static void requireNonNegativeIfPresent(String name, BigDecimal value) {
        if (value != null && value.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException(name + " must be zero or greater");
        }
    }

    public static void requirePositiveIfPresent(String name, Integer value) {
        if (value != null && value <= 0) {
            throw new BadRequestException(name + " must be greater than 0");
        }
    }
}
