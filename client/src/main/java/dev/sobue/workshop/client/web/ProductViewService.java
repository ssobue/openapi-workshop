package dev.sobue.workshop.client.web;

import dev.sobue.workshop.client.api.ProductsApi;
import dev.sobue.workshop.client.model.Product;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductViewService {

    private static final Logger log = LoggerFactory.getLogger(ProductViewService.class);

    private final ProductsApi productsApi;

    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Spring injects a shared ProductsApi bean that this service must reuse")
    public ProductViewService(ProductsApi productsApi) {
        this.productsApi = productsApi;
    }

    public List<Product> fetchProducts() {
        try {
            return productsApi.getAllProducts();
        } catch (Exception ex) {
            log.warn("Failed to load products from API", ex);
            return Collections.emptyList();
        }
    }
}
