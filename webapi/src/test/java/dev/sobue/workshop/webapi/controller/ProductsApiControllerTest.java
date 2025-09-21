package dev.sobue.workshop.webapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class ProductsApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldRegisterUpdateAndFetchProduct() throws Exception {
    String payload = objectMapper.writeValueAsString(objectMapper.createObjectNode()
        .put("name", "Mechanical Keyboard")
        .put("currency", "JPY")
        .put("price", 15800.0)
        .put("stock", 15));

    MvcResult result = mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
        .andExpect(status().isCreated())
        .andReturn();

    JsonNode created = objectMapper.readTree(
        result.getResponse().getContentAsString(StandardCharsets.UTF_8));
    long productId = created.get("id").asLong();
    assertThat(created.get("name").asText()).isEqualTo("Mechanical Keyboard");

    String updatePayload = objectMapper.writeValueAsString(objectMapper.createObjectNode()
        .put("name", "Wireless Mechanical Keyboard")
        .put("currency", "JPY")
        .put("price", 16800.0)
        .put("stock", 20));

    mockMvc.perform(put("/api/v1/products/{id}", productId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatePayload))
        .andExpect(status().isOk());

    MvcResult getResult = mockMvc.perform(get("/api/v1/products/{id}", productId))
        .andExpect(status().isOk())
        .andReturn();

    JsonNode fetched = objectMapper.readTree(
        getResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    assertThat(fetched.get("name").asText()).isEqualTo("Wireless Mechanical Keyboard");
    assertThat(fetched.get("price").asDouble()).isEqualTo(16800.0);
    assertThat(fetched.get("stock").asInt()).isEqualTo(20);
  }
}
