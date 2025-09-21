package dev.sobue.workshop.webapi.mapper;

import dev.sobue.workshop.product.Product;
import java.math.BigDecimal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductApiMapper {

  dev.sobue.workshop.webapi.model.Product toApi(Product product);

  List<dev.sobue.workshop.webapi.model.Product> toApiList(List<Product> products);

  Product toDomain(dev.sobue.workshop.webapi.model.Product product);

  default Double toDouble(BigDecimal value) {
    return value == null ? null : value.doubleValue();
  }

  default BigDecimal toBigDecimal(Double value) {
    return value == null ? null : BigDecimal.valueOf(value);
  }
}
