package dev.sobue.openapi.workshop.api.service;

import dev.sobue.openapi.workshop.api.error.NotFoundException;
import dev.sobue.openapi.workshop.api.model.Product;
import dev.sobue.openapi.workshop.api.model.ProductInput;
import dev.sobue.openapi.workshop.api.model.ProductPatch;
import dev.sobue.openapi.workshop.api.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> list() {
        return repository.findAll();
    }

    public Product create(ProductInput input) {
        ValidationSupport.requireBody(input, "ProductInput");
        ValidationSupport.requireString("name", input.name());
        ValidationSupport.requireNonNegative("price", input.price());
        Product product = new Product(UUID.randomUUID().toString(), input.name(), input.price());
        return repository.save(product);
    }

    public Product get(String productId) {
        return repository.findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    public Product replace(String productId, ProductInput input) {
        ValidationSupport.requireBody(input, "ProductInput");
        ValidationSupport.requireString("name", input.name());
        ValidationSupport.requireNonNegative("price", input.price());
        Product existing = get(productId);
        Product product = new Product(existing.id(), input.name(), input.price());
        return repository.save(product);
    }

    public Product update(String productId, ProductPatch patch) {
        ValidationSupport.requireBody(patch, "ProductPatch");
        ValidationSupport.requirePatchFields(patch.name(), patch.price());
        ValidationSupport.requireStringIfPresent("name", patch.name());
        ValidationSupport.requireNonNegativeIfPresent("price", patch.price());
        Product existing = get(productId);
        String name = patch.name() != null ? patch.name() : existing.name();
        var price = patch.price() != null ? patch.price() : existing.price();
        Product product = new Product(existing.id(), name, price);
        return repository.save(product);
    }

    public void delete(String productId) {
        Product existing = get(productId);
        repository.delete(existing.id());
    }
}
