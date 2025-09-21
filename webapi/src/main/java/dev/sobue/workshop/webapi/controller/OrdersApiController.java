package dev.sobue.workshop.webapi.controller;

import dev.sobue.workshop.common.Currency;
import dev.sobue.workshop.order.Order;
import dev.sobue.workshop.order.OrderItem;
import dev.sobue.workshop.order.OrderService;
import dev.sobue.workshop.webapi.mapper.OrderApiMapper;
import dev.sobue.workshop.webapi.mapper.OrderItemApiMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersApiController implements OrdersApi {

  private final OrderService orderService;
  private final OrderApiMapper orderApiMapper;
  private final OrderItemApiMapper orderItemApiMapper;

  public OrdersApiController(OrderService orderService,
      OrderApiMapper orderApiMapper,
      OrderItemApiMapper orderItemApiMapper) {
    this.orderService = orderService;
    this.orderApiMapper = orderApiMapper;
    this.orderItemApiMapper = orderItemApiMapper;
  }

  @Override
  public ResponseEntity<List<dev.sobue.workshop.webapi.model.Order>> getAllOrders() {
    List<Order> orders = orderService.findAll();
    return ResponseEntity.ok(orderApiMapper.toApiList(orders));
  }

  @Override
  public ResponseEntity<dev.sobue.workshop.webapi.model.Order> getOrder(Long orderId) {
    Order order = orderService.findById(orderId);
    return ResponseEntity.ok(orderApiMapper.toApi(order));
  }

  @Override
  public ResponseEntity<dev.sobue.workshop.webapi.model.OrderItem> addOrderItem(Long orderId,
      dev.sobue.workshop.webapi.model.OrderItem orderItem) {
    if (orderItem == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    OrderItem created = orderService.addItem(orderId, orderItemApiMapper.toDomain(orderItem));
    return ResponseEntity.ok(orderItemApiMapper.toApi(created));
  }

  @Override
  public ResponseEntity<dev.sobue.workshop.webapi.model.Order> registerOrder(
      dev.sobue.workshop.webapi.model.Order order) {
    if (order == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    Order domain = orderApiMapper.toDomain(order);
    if (domain.getItems() == null) {
      domain.setItems(new ArrayList<>());
    }
    Order created = orderService.create(domain);
    return ResponseEntity.status(HttpStatus.CREATED).body(orderApiMapper.toApi(created));
  }

  @Override
  public ResponseEntity<Void> deleteOrder(Long orderId) {
    orderService.delete(orderId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<dev.sobue.workshop.webapi.model.Order> getOrderTotal(Long orderId,
      dev.sobue.workshop.webapi.model.Currency currency) {
    Currency domainCurrency = currency == null ? null : Currency.valueOf(currency.getValue());
    Order order = orderService.findWithTotal(orderId, domainCurrency);
    return ResponseEntity.ok(orderApiMapper.toApi(order));
  }

  @Override
  public ResponseEntity<dev.sobue.workshop.webapi.model.OrderItem> updateOrderItem(Long orderId,
      Long itemId, dev.sobue.workshop.webapi.model.OrderItem orderItem) {
    if (orderItem == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    OrderItem updated = orderService.updateItem(orderId, itemId,
        orderItemApiMapper.toDomain(orderItem));
    return ResponseEntity.ok(orderItemApiMapper.toApi(updated));
  }

  @Override
  public ResponseEntity<Void> deleteOrderItem(Long orderId, Long itemId) {
    orderService.deleteItem(orderId, itemId);
    return ResponseEntity.noContent().build();
  }
}
