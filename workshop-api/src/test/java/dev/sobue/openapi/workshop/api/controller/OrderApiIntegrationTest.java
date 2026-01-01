package dev.sobue.openapi.workshop.api.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class OrderApiIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createOrderAndAddLine() throws Exception {
        String orderPayload = "{\"status\":\"NEW\"}";

        MvcResult orderResult = mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderPayload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andReturn();

        String orderId = JsonPath.read(orderResult.getResponse().getContentAsString(), "$.id");

        String linePayload = "{\"productId\":\"product-1\",\"quantity\":2,\"unitPrice\":9.99}";
        MvcResult lineResult = mockMvc.perform(post("/orders/{orderId}/lines", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(linePayload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.orderId", is(orderId)))
            .andReturn();

        String lineId = JsonPath.read(lineResult.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/orders/{orderId}/lines/{lineId}", orderId, lineId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(lineId)))
            .andExpect(jsonPath("$.quantity", is(2)));
    }

    @Test
    void missingOrderReturnsProblemDetails() throws Exception {
        mockMvc.perform(get("/orders/{orderId}", "missing"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/problem+json")))
            .andExpect(jsonPath("$.status", is(404)))
            .andExpect(jsonPath("$.title", is("Not found")));
    }
}
