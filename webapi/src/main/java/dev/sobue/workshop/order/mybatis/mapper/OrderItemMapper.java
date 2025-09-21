package dev.sobue.workshop.order.mybatis.mapper;

import dev.sobue.workshop.order.OrderItem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderItemMapper {

  List<OrderItem> selectByOrderId(Long orderId);

  OrderItem selectById(@Param("orderId") Long orderId, @Param("itemId") Long itemId);

  int insert(OrderItem item);

  int update(OrderItem item);

  int delete(@Param("orderId") Long orderId, @Param("itemId") Long itemId);

  int deleteByOrderId(@Param("orderId") Long orderId);
}
