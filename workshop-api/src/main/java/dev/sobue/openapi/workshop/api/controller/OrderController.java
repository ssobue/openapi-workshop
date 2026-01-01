package dev.sobue.openapi.workshop.api.controller;

import dev.sobue.openapi.workshop.api.model.Order;
import dev.sobue.openapi.workshop.api.model.OrderInput;
import dev.sobue.openapi.workshop.api.model.OrderLine;
import dev.sobue.openapi.workshop.api.model.OrderLineInput;
import dev.sobue.openapi.workshop.api.model.OrderLinePatch;
import dev.sobue.openapi.workshop.api.model.OrderPatch;
import dev.sobue.openapi.workshop.api.service.OrderService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> listOrders() {
        return service.list();
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderInput input) {
        Order order = service.create(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        return service.get(orderId);
    }

    @PutMapping("/{orderId}")
    public Order replaceOrder(@PathVariable String orderId, @RequestBody OrderInput input) {
        return service.replace(orderId, input);
    }

    @PatchMapping("/{orderId}")
    public Order updateOrder(@PathVariable String orderId, @RequestBody OrderPatch patch) {
        return service.update(orderId, patch);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        service.delete(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}/lines")
    public List<OrderLine> listOrderLines(@PathVariable String orderId) {
        return service.listLines(orderId);
    }

    @PostMapping("/{orderId}/lines")
    public ResponseEntity<OrderLine> addOrderLine(
        @PathVariable String orderId,
        @RequestBody OrderLineInput input
    ) {
        OrderLine line = service.addLine(orderId, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(line);
    }

    @GetMapping("/{orderId}/lines/{lineId}")
    public OrderLine getOrderLine(@PathVariable String orderId, @PathVariable String lineId) {
        return service.getLine(orderId, lineId);
    }

    @PutMapping("/{orderId}/lines/{lineId}")
    public OrderLine replaceOrderLine(
        @PathVariable String orderId,
        @PathVariable String lineId,
        @RequestBody OrderLineInput input
    ) {
        return service.replaceLine(orderId, lineId, input);
    }

    @PatchMapping("/{orderId}/lines/{lineId}")
    public OrderLine updateOrderLine(
        @PathVariable String orderId,
        @PathVariable String lineId,
        @RequestBody OrderLinePatch patch
    ) {
        return service.updateLine(orderId, lineId, patch);
    }

    @DeleteMapping("/{orderId}/lines/{lineId}")
    public ResponseEntity<Void> deleteOrderLine(
        @PathVariable String orderId,
        @PathVariable String lineId
    ) {
        service.deleteLine(orderId, lineId);
        return ResponseEntity.noContent().build();
    }
}
