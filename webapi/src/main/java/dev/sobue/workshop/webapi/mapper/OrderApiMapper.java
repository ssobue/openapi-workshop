package dev.sobue.workshop.webapi.mapper;

import dev.sobue.workshop.order.Order;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = OrderItemApiMapper.class)
public interface OrderApiMapper {

  dev.sobue.workshop.webapi.model.Order toApi(Order order);

  List<dev.sobue.workshop.webapi.model.Order> toApiList(List<Order> orders);

  @Mapping(target = "orderDate", ignore = true)
  @Mapping(target = "updatedOn", ignore = true)
  Order toDomain(dev.sobue.workshop.webapi.model.Order order);
}
