package dev.sobue.workshop.webapi.controller.impl;

import dev.sobue.workshop.webapi.controller.OrdersApi;
import dev.sobue.workshop.webapi.model.Order;
import dev.sobue.workshop.webapi.model.OrderItem;
import java.util.List;
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
    return OrdersApi.super.getAllOrders();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Order> getOrder(Long orderId) {
    return OrdersApi.super.getOrder(orderId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<OrderItem> addOrderItem(Long orderId, OrderItem orderItem) {
    return OrdersApi.super.addOrderItem(orderId, orderItem);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Order> registerOrder(Order order) {
    return OrdersApi.super.registerOrder(order);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Void> deleteOrder(Long orderId) {
    return OrdersApi.super.deleteOrder(orderId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Order> getOrderTotal(Long orderId, String currency) {
    return OrdersApi.super.getOrderTotal(orderId, currency);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<OrderItem> updateOrderItem(Long orderId, Long itemId, OrderItem orderItem) {
    return OrdersApi.super.updateOrderItem(orderId, itemId, orderItem);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Void> deleteOrderItem(Long orderId, Long itemId) {
    return OrdersApi.super.deleteOrderItem(orderId, itemId);
  }
}
