package dev.sobue.openapi.workshop.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.sobue.openapi.workshop.api.error.BadRequestException;
import dev.sobue.openapi.workshop.api.error.NotFoundException;
import dev.sobue.openapi.workshop.api.model.Order;
import dev.sobue.openapi.workshop.api.model.OrderInput;
import dev.sobue.openapi.workshop.api.model.OrderLine;
import dev.sobue.openapi.workshop.api.model.OrderLineInput;
import dev.sobue.openapi.workshop.api.model.OrderLinePatch;
import dev.sobue.openapi.workshop.api.model.OrderPatch;
import dev.sobue.openapi.workshop.api.repository.OrderLineRepository;
import dev.sobue.openapi.workshop.api.repository.OrderRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceTest {
    private OrderService service;

    @BeforeEach
    void setUp() {
        service = new OrderService(new OrderRepository(), new OrderLineRepository());
    }

    @Test
    void createAndUpdateOrder() {
        OrderInput input = new OrderInput("NEW");
        Order created = service.create(input);

        assertNotNull(created.id());
        assertEquals("NEW", created.status());

        OrderPatch patch = new OrderPatch("SHIPPED");
        Order updated = service.update(created.id(), patch);
        assertEquals("SHIPPED", updated.status());
    }

    @Test
    void addLineRequiresOrder() {
        OrderLineInput input = new OrderLineInput("product-1", 2, new BigDecimal("9.99"));
        assertThrows(NotFoundException.class, () -> service.addLine("missing", input));
    }

    @Test
    void addLineAndUpdateLine() {
        Order order = service.create(new OrderInput("NEW"));
        OrderLineInput input = new OrderLineInput("product-1", 2, new BigDecimal("9.99"));
        OrderLine line = service.addLine(order.id(), input);

        assertEquals(order.id(), line.orderId());

        OrderLinePatch patch = new OrderLinePatch("product-2", 3, new BigDecimal("8.50"));
        OrderLine updated = service.updateLine(order.id(), line.id(), patch);
        assertEquals("product-2", updated.productId());
        assertEquals(3, updated.quantity());
    }

    @Test
    void updateRequiresPatchFields() {
        Order order = service.create(new OrderInput("NEW"));
        assertThrows(BadRequestException.class, () -> service.update(order.id(), new OrderPatch(null)));
    }
}
