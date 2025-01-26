package dev.sobue.workshop.webapi.controller.impl;

import dev.sobue.workshop.webapi.controller.ProductsApi;
import dev.sobue.workshop.webapi.model.Product;
import java.util.List;
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
    return ProductsApi.super.getAllProducts();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Product> getProduct(Long productId) {
    return ProductsApi.super.getProduct(productId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Product> registerProduct(Product product) {
    return ProductsApi.super.registerProduct(product);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Product> updateProducts(Long productId) {
    return ProductsApi.super.updateProducts(productId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResponseEntity<Void> deleteProduct(Long productId) {
    return ProductsApi.super.deleteProduct(productId);
  }
}
