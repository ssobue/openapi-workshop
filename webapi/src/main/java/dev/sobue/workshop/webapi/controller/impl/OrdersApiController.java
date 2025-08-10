package dev.sobue.workshop.webapi.controller.impl;

import dev.sobue.workshop.webapi.controller.OrdersApi;
import dev.sobue.workshop.webapi.model.Currency;
import dev.sobue.workshop.webapi.model.Order;
import dev.sobue.workshop.webapi.model.OrderItem;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Orders API Controller.
 *
 * @author Sho SOBUE
 */
@RestController
public class OrdersApiController implements OrdersApi {

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<List<Order>> getAllOrders() {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Order> getOrder(Long orderId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<OrderItem> addOrderItem(Long orderId, OrderItem orderItem) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Order> registerOrder(Order order) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Void> deleteOrder(Long orderId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Order> getOrderTotal(Long orderId, Currency currency) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<OrderItem> updateOrderItem(Long orderId, Long itemId, OrderItem orderItem) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Void> deleteOrderItem(Long orderId, Long itemId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
