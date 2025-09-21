package dev.sobue.workshop.product;

import dev.sobue.workshop.product.mybatis.mapper.ProductMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ProductService {

  private final ProductMapper productMapper;

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Mappers are Spring-managed singletons")
  public ProductService(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  @Transactional(readOnly = true)
  public List<Product> findAll() {
    return productMapper.selectAll();
  }

  @Transactional(readOnly = true)
  public Product findById(Long id) {
    Product product = productMapper.selectById(id);
    if (product == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product %d not found".formatted(id));
    }
    return product;
  }

  public Product create(Product product) {
    validateProduct(product);
    productMapper.insert(product);
    return findById(product.getId());
  }

  public Product update(Long id, Product product) {
    // Ensure entity exists before attempting update
    findById(id);
    product.setId(id);
    validateProduct(product);
    int updated = productMapper.update(product);
    if (updated == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product %d not found".formatted(id));
    }
    return findById(id);
  }

  public void delete(Long id) {
    int deleted = productMapper.delete(id);
    if (deleted == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product %d not found".formatted(id));
    }
  }

  private void validateProduct(Product product) {
    if (product.getName() == null || product.getName().isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name is required");
    }
    if (product.getCurrency() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product currency is required");
    }
    BigDecimal price = product.getPrice();
    if (price == null || price.signum() < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product price must be positive");
    }
    Integer stock = product.getStock();
    if (stock == null || stock < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Product stock must be non-negative");
    }
  }
}
