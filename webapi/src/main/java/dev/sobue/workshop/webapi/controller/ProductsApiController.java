package dev.sobue.workshop.webapi.controller;

import dev.sobue.workshop.webapi.model.Product;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Products API Controller.
 *
 * @author Sho SOBUE
 */
@RestController
public class ProductsApiController implements ProductsApi {

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<List<Product>> getAllProducts() {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Product> getProduct(Long productId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Product> registerProduct(Product product) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Product> updateProducts(Long productId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Void> deleteProduct(Long productId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
