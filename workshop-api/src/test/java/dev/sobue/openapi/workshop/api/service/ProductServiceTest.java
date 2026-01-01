package dev.sobue.openapi.workshop.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.sobue.openapi.workshop.api.error.BadRequestException;
import dev.sobue.openapi.workshop.api.error.NotFoundException;
import dev.sobue.openapi.workshop.api.model.Product;
import dev.sobue.openapi.workshop.api.model.ProductInput;
import dev.sobue.openapi.workshop.api.model.ProductPatch;
import dev.sobue.openapi.workshop.api.repository.ProductRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceTest {
    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(new ProductRepository());
    }

    @Test
    void createAndGetProduct() {
        ProductInput input = new ProductInput("Widget", new BigDecimal("12.50"));
        Product created = service.create(input);

        assertNotNull(created.id());
        assertEquals("Widget", created.name());

        Product fetched = service.get(created.id());
        assertEquals(created.id(), fetched.id());
        assertEquals(created.price(), fetched.price());
    }

    @Test
    void createRequiresName() {
        ProductInput input = new ProductInput(" ", new BigDecimal("1.00"));
        assertThrows(BadRequestException.class, () -> service.create(input));
    }

    @Test
    void createRejectsNegativePrice() {
        ProductInput input = new ProductInput("Widget", new BigDecimal("-1.00"));
        assertThrows(BadRequestException.class, () -> service.create(input));
    }

    @Test
    void updateRequiresPatchFields() {
        ProductInput input = new ProductInput("Widget", new BigDecimal("2.00"));
        Product created = service.create(input);

        ProductPatch patch = new ProductPatch(null, null);
        assertThrows(BadRequestException.class, () -> service.update(created.id(), patch));
    }

    @Test
    void getMissingProductThrowsNotFound() {
        assertThrows(NotFoundException.class, () -> service.get("missing"));
    }
}
