package dev.sobue.openapi.workshop.api.repository;

import dev.sobue.openapi.workshop.api.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final Map<String, Product> store = new ConcurrentHashMap<>();

    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Product> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Product save(Product product) {
        store.put(product.id(), product);
        return product;
    }

    public void delete(String id) {
        store.remove(id);
    }
}
