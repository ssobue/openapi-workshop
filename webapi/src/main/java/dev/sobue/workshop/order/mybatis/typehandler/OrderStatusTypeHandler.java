package dev.sobue.workshop.order.mybatis.typehandler;

import dev.sobue.workshop.order.OrderStatus;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(OrderStatus.class)
public class OrderStatusTypeHandler extends BaseTypeHandler<OrderStatus> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, OrderStatus parameter,
      JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, parameter.getCode());
  }

  @Override
  public OrderStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String code = rs.getString(columnName);
    return code == null ? null : OrderStatus.fromCode(code);
  }

  @Override
  public OrderStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String code = rs.getString(columnIndex);
    return code == null ? null : OrderStatus.fromCode(code);
  }

  @Override
  public OrderStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String code = cs.getString(columnIndex);
    return code == null ? null : OrderStatus.fromCode(code);
  }
}
