package dev.sobue.openapi.workshop.api.repository;

import dev.sobue.openapi.workshop.api.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    private final Map<String, Order> store = new ConcurrentHashMap<>();

    public List<Order> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Order save(Order order) {
        store.put(order.id(), order);
        return order;
    }

    public void delete(String id) {
        store.remove(id);
    }
}
