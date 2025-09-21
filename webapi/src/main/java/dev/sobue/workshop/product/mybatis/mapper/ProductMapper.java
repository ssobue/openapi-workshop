package dev.sobue.workshop.product.mybatis.mapper;

import dev.sobue.workshop.product.Product;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {

  List<Product> selectAll();

  Product selectById(@Param("id") Long id);

  int insert(Product product);

  int update(Product product);

  int delete(@Param("id") Long id);
}
