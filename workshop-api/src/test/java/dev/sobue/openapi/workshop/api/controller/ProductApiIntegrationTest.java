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
class ProductApiIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createProductThenFetch() throws Exception {
        String payload = "{\"name\":\"Notebook\",\"price\":12.5}";

        MvcResult result = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.name", is("Notebook")))
            .andReturn();

        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mockMvc.perform(get("/products/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(id)))
            .andExpect(jsonPath("$.price", is(12.5)));
    }

    @Test
    void invalidProductReturnsProblemDetails() throws Exception {
        String payload = "{}";

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/problem+json")))
            .andExpect(jsonPath("$.status", is(400)))
            .andExpect(jsonPath("$.title", is("Invalid request")));
    }

    @Test
    void missingProductReturnsProblemDetails() throws Exception {
        mockMvc.perform(get("/products/{id}", "missing"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/problem+json")))
            .andExpect(jsonPath("$.status", is(404)))
            .andExpect(jsonPath("$.title", is("Not found")));
    }
}
