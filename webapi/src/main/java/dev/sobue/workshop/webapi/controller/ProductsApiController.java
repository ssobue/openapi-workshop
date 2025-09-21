package dev.sobue.workshop.webapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sobue.workshop.product.Product;
import dev.sobue.workshop.product.ProductService;
import dev.sobue.workshop.webapi.mapper.ProductApiMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProductsApiController implements ProductsApi {

  private final ProductService productService;
  private final ProductApiMapper productApiMapper;
  private final ObjectMapper objectMapper;

  public ProductsApiController(ProductService productService,
      ProductApiMapper productApiMapper,
      ObjectMapper objectMapper) {
    this.productService = productService;
    this.productApiMapper = productApiMapper;
    this.objectMapper = objectMapper;
  }

  @Override
  public ResponseEntity<List<dev.sobue.workshop.webapi.model.Product>> getAllProducts() {
    List<Product> products = productService.findAll();
    return ResponseEntity.ok(productApiMapper.toApiList(products));
  }

  @Override
  public ResponseEntity<dev.sobue.workshop.webapi.model.Product> getProduct(Long productId) {
    Product product = productService.findById(productId);
    return ResponseEntity.ok(productApiMapper.toApi(product));
  }

  @Override
  public ResponseEntity<dev.sobue.workshop.webapi.model.Product> registerProduct(
      dev.sobue.workshop.webapi.model.Product product) {
    if (product == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product payload is required");
    }
    Product created = productService.create(productApiMapper.toDomain(product));
    return ResponseEntity.status(HttpStatus.CREATED).body(productApiMapper.toApi(created));
  }

  @Override
  public ResponseEntity<dev.sobue.workshop.webapi.model.Product> updateProducts(Long productId) {
    dev.sobue.workshop.webapi.model.Product body = readBody(
        dev.sobue.workshop.webapi.model.Product.class);
    if (body == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product payload is required");
    }
    Product updated = productService.update(productId, productApiMapper.toDomain(body));
    return ResponseEntity.ok(productApiMapper.toApi(updated));
  }

  @Override
  public ResponseEntity<Void> deleteProduct(Long productId) {
    productService.delete(productId);
    return ResponseEntity.noContent().build();
  }

  private <T> T readBody(Class<T> clazz) {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request context not available");
    }
    HttpServletRequest request = attributes.getRequest();
    if (request.getContentLengthLong() == 0) {
      return null;
    }
    try (InputStream stream = request.getInputStream()) {
      if (stream == null) {
        return null;
      }
      return objectMapper.readValue(stream, clazz);
    } catch (IOException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request body", ex);
    }
  }
}
