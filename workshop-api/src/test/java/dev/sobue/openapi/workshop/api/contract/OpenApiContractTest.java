package dev.sobue.openapi.workshop.api.contract;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpenApiContractTest {
    @Test
    void openApiSpecIncludesImplementedEndpoints() throws IOException {
        Path specPath = Path.of("..", "openapi", "openapi.yaml");
        assertTrue(Files.exists(specPath), "openapi/openapi.yaml must exist");

        String spec = Files.readString(specPath, StandardCharsets.UTF_8);

        List<String> expectedPaths = List.of(
            "/products:",
            "/products/{productId}:",
            "/orders:",
            "/orders/{orderId}:",
            "/orders/{orderId}/lines:",
            "/orders/{orderId}/lines/{lineId}:"
        );

        for (String path : expectedPaths) {
            assertTrue(spec.contains(path), "Missing path: " + path);
        }

        List<String> expectedOperations = List.of(
            "operationId: listProducts",
            "operationId: createProduct",
            "operationId: getProduct",
            "operationId: replaceProduct",
            "operationId: updateProduct",
            "operationId: deleteProduct",
            "operationId: listOrders",
            "operationId: createOrder",
            "operationId: getOrder",
            "operationId: replaceOrder",
            "operationId: updateOrder",
            "operationId: deleteOrder",
            "operationId: listOrderLines",
            "operationId: addOrderLine",
            "operationId: getOrderLine",
            "operationId: replaceOrderLine",
            "operationId: updateOrderLine",
            "operationId: deleteOrderLine"
        );

        for (String operation : expectedOperations) {
            assertTrue(spec.contains(operation), "Missing operationId: " + operation);
        }

        assertTrue(spec.contains("application/problem+json"), "Problem Details media type must be defined");
    }
}
