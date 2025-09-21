package dev.sobue.workshop.webapi.mapper;

import dev.sobue.workshop.order.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemApiMapper {

  dev.sobue.workshop.webapi.model.OrderItem toApi(OrderItem item);

  List<dev.sobue.workshop.webapi.model.OrderItem> toApiList(List<OrderItem> items);

  OrderItem toDomain(dev.sobue.workshop.webapi.model.OrderItem item);

  default Double toDouble(BigDecimal value) {
    return value == null ? null : value.doubleValue();
  }

  default BigDecimal toBigDecimal(Double value) {
    return value == null ? null : BigDecimal.valueOf(value);
  }
}
