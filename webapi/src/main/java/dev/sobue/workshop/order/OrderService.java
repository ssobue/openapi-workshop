package dev.sobue.workshop.order;

import dev.sobue.workshop.common.Currency;
import dev.sobue.workshop.common.ExchangeRate;
import dev.sobue.workshop.common.mybatis.mapper.ExchangeRateMapper;
import dev.sobue.workshop.order.mybatis.mapper.OrderItemMapper;
import dev.sobue.workshop.order.mybatis.mapper.OrderMapper;
import dev.sobue.workshop.product.Product;
import dev.sobue.workshop.product.mybatis.mapper.ProductMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class OrderService {

  private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;

  private final OrderMapper orderMapper;
  private final OrderItemMapper orderItemMapper;
  private final ProductMapper productMapper;
  private final ExchangeRateMapper exchangeRateMapper;

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Spring injects mapper singletons managed by the container")
  public OrderService(OrderMapper orderMapper,
      OrderItemMapper orderItemMapper,
      ProductMapper productMapper,
      ExchangeRateMapper exchangeRateMapper) {
    this.orderMapper = orderMapper;
    this.orderItemMapper = orderItemMapper;
    this.productMapper = productMapper;
    this.exchangeRateMapper = exchangeRateMapper;
  }

  @Transactional(readOnly = true)
  public List<Order> findAll() {
    List<Order> orders = orderMapper.selectAll();
    return initialiseOrderCollections(orders);
  }

  @Transactional(readOnly = true)
  public Order findById(Long orderId) {
    Order order = orderMapper.selectById(orderId);
    if (order == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Order %d not found".formatted(orderId));
    }
    ensureItems(order);
    return order;
  }

  public Order create(Order order) {
    if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer name is required");
    }
    if (order.getItems().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order items are required");
    }
    order.setOrderDate(LocalDateTime.now());
    if (order.getStatus() == null) {
      order.setStatus(OrderStatus.PENDING_PAYMENT);
    }
    orderMapper.insert(order);
    for (OrderItem item : order.getItems()) {
      persistOrderItem(order.getId(), item);
    }
    return findById(order.getId());
  }

  public void delete(Long orderId) {
    int deleted = orderMapper.delete(orderId);
    if (deleted == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Order %d not found".formatted(orderId));
    }
  }

  public OrderItem addItem(Long orderId, OrderItem item) {
    Order order = findById(orderId);
    persistOrderItem(order.getId(), item);
    return orderItemMapper.selectById(orderId, item.getId());
  }

  public OrderItem updateItem(Long orderId, Long itemId, OrderItem item) {
    findById(orderId);
    OrderItem existing = orderItemMapper.selectById(orderId, itemId);
    if (existing == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Order item %d for order %d not found".formatted(itemId, orderId));
    }
    item.setId(itemId);
    item.setOrderId(orderId);
    Product product = resolveProduct(item.getProductId());
    item.setCurrency(product.getCurrency());
    item.setPrice(calculateLinePrice(product.getPrice(), item.getQuantity()));
    validateQuantity(item.getQuantity());
    orderItemMapper.update(item);
    return orderItemMapper.selectById(orderId, itemId);
  }

  public void deleteItem(Long orderId, Long itemId) {
    findById(orderId);
    int deleted = orderItemMapper.delete(orderId, itemId);
    if (deleted == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Order item %d for order %d not found".formatted(itemId, orderId));
    }
  }

  @Transactional(readOnly = true)
  public Order findWithTotal(Long orderId, Currency targetCurrency) {
    Order order = findById(orderId);
    if (targetCurrency == null) {
      return order;
    }
    Order converted = cloneWithoutItems(order);
    List<OrderItem> convertedItems = new ArrayList<>();
    for (OrderItem item : order.getItems()) {
      OrderItem copy = cloneOrderItem(item);
      BigDecimal convertedPrice = convertCurrency(copy.getPrice(), copy.getCurrency(),
          targetCurrency);
      copy.setPrice(convertedPrice);
      copy.setCurrency(targetCurrency);
      convertedItems.add(copy);
    }
    converted.setItems(convertedItems);
    return converted;
  }

  private void persistOrderItem(Long orderId, OrderItem item) {
    validateQuantity(item.getQuantity());
    Product product = resolveProduct(item.getProductId());
    item.setOrderId(orderId);
    item.setCurrency(product.getCurrency());
    item.setPrice(calculateLinePrice(product.getPrice(), item.getQuantity()));
    orderItemMapper.insert(item);
  }

  private Product resolveProduct(Long productId) {
    Product product = productMapper.selectById(productId);
    if (product == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Product %d not found".formatted(productId));
    }
    return product;
  }

  private BigDecimal calculateLinePrice(BigDecimal unitPrice, Integer quantity) {
    if (unitPrice == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unit price is required");
    }
    validateQuantity(quantity);
    return unitPrice.multiply(BigDecimal.valueOf(quantity.longValue()));
  }

  private void validateQuantity(Integer quantity) {
    if (quantity == null || quantity <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Quantity must be greater than zero");
    }
  }

  private List<Order> initialiseOrderCollections(List<Order> orders) {
    if (orders == null) {
      return List.of();
    }
    for (Order order : orders) {
      ensureItems(order);
    }
    return orders;
  }

  private void ensureItems(Order order) {
    order.setItems(new ArrayList<>(orderItemMapper.selectByOrderId(order.getId())));
  }

  private Order cloneWithoutItems(Order order) {
    Order copy = new Order();
    copy.setId(order.getId());
    copy.setCustomerName(order.getCustomerName());
    copy.setStatus(order.getStatus());
    copy.setOrderDate(order.getOrderDate());
    copy.setUpdatedOn(order.getUpdatedOn());
    copy.setItems(new ArrayList<>());
    return copy;
  }

  private OrderItem cloneOrderItem(OrderItem item) {
    OrderItem copy = new OrderItem();
    copy.setId(item.getId());
    copy.setOrderId(item.getOrderId());
    copy.setProductId(item.getProductId());
    copy.setQuantity(item.getQuantity());
    copy.setPrice(item.getPrice());
    copy.setCurrency(item.getCurrency());
    return copy;
  }

  private BigDecimal convertCurrency(BigDecimal amount, Currency source, Currency target) {
    if (amount == null || source == null || target == null) {
      return amount;
    }
    if (Objects.equals(source, target)) {
      return amount;
    }
    ExchangeRate sourceRate = exchangeRateMapper.selectByCurrency(source);
    ExchangeRate targetRate = exchangeRateMapper.selectByCurrency(target);
    if (sourceRate == null || sourceRate.getRate() == null || targetRate == null
        || targetRate.getRate() == null) {
      return amount;
    }
    if (targetRate.getRate().compareTo(BigDecimal.ZERO) == 0) {
      return amount;
    }
    BigDecimal amountInBase = amount.multiply(sourceRate.getRate());
    return amountInBase.divide(targetRate.getRate(), 2, DEFAULT_ROUNDING);
  }
}
