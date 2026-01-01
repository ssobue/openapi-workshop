package dev.sobue.openapi.workshop.api.service;

import dev.sobue.openapi.workshop.api.error.NotFoundException;
import dev.sobue.openapi.workshop.api.model.Order;
import dev.sobue.openapi.workshop.api.model.OrderInput;
import dev.sobue.openapi.workshop.api.model.OrderLine;
import dev.sobue.openapi.workshop.api.model.OrderLineInput;
import dev.sobue.openapi.workshop.api.model.OrderLinePatch;
import dev.sobue.openapi.workshop.api.model.OrderPatch;
import dev.sobue.openapi.workshop.api.repository.OrderLineRepository;
import dev.sobue.openapi.workshop.api.repository.OrderRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineRepository lineRepository;

    public OrderService(OrderRepository orderRepository, OrderLineRepository lineRepository) {
        this.orderRepository = orderRepository;
        this.lineRepository = lineRepository;
    }

    public List<Order> list() {
        return orderRepository.findAll();
    }

    public Order create(OrderInput input) {
        ValidationSupport.requireBody(input, "OrderInput");
        ValidationSupport.requireString("status", input.status());
        Order order = new Order(UUID.randomUUID().toString(), input.status(), Instant.now());
        return orderRepository.save(order);
    }

    public Order get(String orderId) {
        return findOrder(orderId);
    }

    public Order replace(String orderId, OrderInput input) {
        ValidationSupport.requireBody(input, "OrderInput");
        ValidationSupport.requireString("status", input.status());
        Order existing = findOrder(orderId);
        Order order = new Order(existing.id(), input.status(), existing.createdAt());
        return orderRepository.save(order);
    }

    public Order update(String orderId, OrderPatch patch) {
        ValidationSupport.requireBody(patch, "OrderPatch");
        ValidationSupport.requirePatchFields(patch.status());
        ValidationSupport.requireStringIfPresent("status", patch.status());
        Order existing = findOrder(orderId);
        String status = patch.status() != null ? patch.status() : existing.status();
        Order order = new Order(existing.id(), status, existing.createdAt());
        return orderRepository.save(order);
    }

    public void delete(String orderId) {
        Order existing = findOrder(orderId);
        orderRepository.delete(existing.id());
        lineRepository.deleteAll(existing.id());
    }

    public List<OrderLine> listLines(String orderId) {
        findOrder(orderId);
        return lineRepository.findAll(orderId);
    }

    public OrderLine addLine(String orderId, OrderLineInput input) {
        Order order = findOrder(orderId);
        ValidationSupport.requireBody(input, "OrderLineInput");
        ValidationSupport.requireString("productId", input.productId());
        ValidationSupport.requirePositive("quantity", input.quantity());
        ValidationSupport.requireNonNegative("unitPrice", input.unitPrice());
        OrderLine line = new OrderLine(
            UUID.randomUUID().toString(),
            order.id(),
            input.productId(),
            input.quantity(),
            input.unitPrice()
        );
        return lineRepository.save(order.id(), line);
    }

    public OrderLine getLine(String orderId, String lineId) {
        findOrder(orderId);
        return lineRepository.findById(orderId, lineId)
            .orElseThrow(() -> new NotFoundException("Order line not found"));
    }

    public OrderLine replaceLine(String orderId, String lineId, OrderLineInput input) {
        ValidationSupport.requireBody(input, "OrderLineInput");
        ValidationSupport.requireString("productId", input.productId());
        ValidationSupport.requirePositive("quantity", input.quantity());
        ValidationSupport.requireNonNegative("unitPrice", input.unitPrice());
        OrderLine existing = getLine(orderId, lineId);
        OrderLine line = new OrderLine(
            existing.id(),
            existing.orderId(),
            input.productId(),
            input.quantity(),
            input.unitPrice()
        );
        return lineRepository.save(orderId, line);
    }

    public OrderLine updateLine(String orderId, String lineId, OrderLinePatch patch) {
        ValidationSupport.requireBody(patch, "OrderLinePatch");
        ValidationSupport.requirePatchFields(patch.productId(), patch.quantity(), patch.unitPrice());
        ValidationSupport.requireStringIfPresent("productId", patch.productId());
        ValidationSupport.requirePositiveIfPresent("quantity", patch.quantity());
        ValidationSupport.requireNonNegativeIfPresent("unitPrice", patch.unitPrice());
        OrderLine existing = getLine(orderId, lineId);
        String productId = patch.productId() != null ? patch.productId() : existing.productId();
        Integer quantity = patch.quantity() != null ? patch.quantity() : existing.quantity();
        var unitPrice = patch.unitPrice() != null ? patch.unitPrice() : existing.unitPrice();
        OrderLine line = new OrderLine(
            existing.id(),
            existing.orderId(),
            productId,
            quantity,
            unitPrice
        );
        return lineRepository.save(orderId, line);
    }

    public void deleteLine(String orderId, String lineId) {
        OrderLine existing = getLine(orderId, lineId);
        lineRepository.delete(existing.orderId(), existing.id());
    }

    private Order findOrder(String orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException("Order not found"));
    }
}
