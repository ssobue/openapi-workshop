package dev.sobue.workshop.webapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class OrdersApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldCreateOrderAndReturnConvertedTotal() throws Exception {
    ObjectNode orderPayload = objectMapper.createObjectNode();
    orderPayload.put("customerName", "Alice");
    orderPayload.put("status", "PROCESSING");
    ArrayNode items = orderPayload.putArray("items");
    items.addObject()
        .put("productId", 1L)
        .put("quantity", 2);

    MvcResult createResult = mockMvc.perform(post("/api/v1/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(orderPayload.toString()))
        .andExpect(status().isCreated())
        .andReturn();

    JsonNode createdOrder = objectMapper.readTree(
        createResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    long orderId = createdOrder.get("id").asLong();
    assertThat(orderId).isPositive();
    assertThat(createdOrder.get("items").get(0).get("price").asDouble()).isGreaterThan(0.0);

    MvcResult totalResult = mockMvc.perform(get("/api/v1/orders/{orderId}/total", orderId)
            .param("currency", "USD"))
        .andExpect(status().isOk())
        .andReturn();

    JsonNode converted = objectMapper.readTree(
        totalResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    JsonNode convertedItem = converted.get("items").get(0);
    assertThat(convertedItem.get("currency").asText()).isEqualTo("USD");
    assertThat(convertedItem.get("price").asDouble()).isGreaterThan(0.0);
  }
}
