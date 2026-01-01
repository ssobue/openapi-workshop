package dev.sobue.openapi.workshop.api.controller;

import dev.sobue.openapi.workshop.api.model.Product;
import dev.sobue.openapi.workshop.api.model.ProductInput;
import dev.sobue.openapi.workshop.api.model.ProductPatch;
import dev.sobue.openapi.workshop.api.service.ProductService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> listProducts() {
        return service.list();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductInput input) {
        Product product = service.create(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable String productId) {
        return service.get(productId);
    }

    @PutMapping("/{productId}")
    public Product replaceProduct(@PathVariable String productId, @RequestBody ProductInput input) {
        return service.replace(productId, input);
    }

    @PatchMapping("/{productId}")
    public Product updateProduct(@PathVariable String productId, @RequestBody ProductPatch patch) {
        return service.update(productId, patch);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        service.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
