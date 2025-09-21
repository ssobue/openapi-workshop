package dev.sobue.workshop.common.mybatis.typehandler;

import dev.sobue.workshop.common.Currency;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(Currency.class)
public class CurrencyTypeHandler extends BaseTypeHandler<Currency> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Currency parameter,
      JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, parameter.getCode());
  }

  @Override
  public Currency getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String code = rs.getString(columnName);
    return code == null ? null : Currency.fromCode(code);
  }

  @Override
  public Currency getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String code = rs.getString(columnIndex);
    return code == null ? null : Currency.fromCode(code);
  }

  @Override
  public Currency getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String code = cs.getString(columnIndex);
    return code == null ? null : Currency.fromCode(code);
  }
}
