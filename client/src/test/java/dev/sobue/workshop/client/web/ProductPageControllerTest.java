package dev.sobue.workshop.client.web;

import dev.sobue.workshop.client.model.Currency;
import dev.sobue.workshop.client.model.Product;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProductPageController.class)
class ProductPageControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ProductViewService productViewService;

  @Test
  void redirectsRootToProductsPage() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/products"));
  }

  @Test
  void rendersProductsWhenAvailable() throws Exception {
    Product sample = Product.builder()
        .id(1L)
        .name("Sample Widget")
        .currency(Currency.JPY)
        .price(1200.0d)
        .stock(8)
        .build();

    given(productViewService.fetchProducts()).willReturn(List.of(sample));

    mockMvc.perform(get("/products"))
        .andExpect(status().isOk())
        .andExpect(view().name("products"))
        .andExpect(model().attribute("hasData", true))
        .andExpect(content().string(containsString("Sample Widget")))
        .andExpect(content().string(containsString("1200.0")));
  }

  @Test
  void rendersEmptyStateWhenApiFails() throws Exception {
    given(productViewService.fetchProducts()).willReturn(Collections.emptyList());

    mockMvc.perform(get("/products"))
        .andExpect(status().isOk())
        .andExpect(view().name("products"))
        .andExpect(model().attribute("hasData", false))
        .andExpect(content().string(containsString("No products could be loaded")));
  }
}
