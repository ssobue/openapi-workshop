package dev.sobue.openapi.workshop.api.repository;

import dev.sobue.openapi.workshop.api.model.OrderLine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class OrderLineRepository {
    private final Map<String, Map<String, OrderLine>> store = new ConcurrentHashMap<>();

    public List<OrderLine> findAll(String orderId) {
        return new ArrayList<>(store.getOrDefault(orderId, Collections.emptyMap()).values());
    }

    public Optional<OrderLine> findById(String orderId, String lineId) {
        return Optional.ofNullable(store.getOrDefault(orderId, Collections.emptyMap()).get(lineId));
    }

    public OrderLine save(String orderId, OrderLine orderLine) {
        store.computeIfAbsent(orderId, ignored -> new ConcurrentHashMap<>())
            .put(orderLine.id(), orderLine);
        return orderLine;
    }

    public void delete(String orderId, String lineId) {
        Map<String, OrderLine> lines = store.get(orderId);
        if (lines != null) {
            lines.remove(lineId);
        }
    }

    public void deleteAll(String orderId) {
        store.remove(orderId);
    }
}
