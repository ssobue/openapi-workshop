package dev.sobue.workshop.order.mybatis.mapper;

import dev.sobue.workshop.order.Order;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

  List<Order> selectAll();

  Order selectById(@Param("id") Long id);

  int insert(Order order);

  int delete(@Param("id") Long id);
}
