package dev.sobue.workshop.client.web;

import dev.sobue.workshop.client.model.Product;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductPageController {

    private final ProductViewService productViewService;

    public ProductPageController(ProductViewService productViewService) {
        this.productViewService = productViewService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productViewService.fetchProducts();
        model.addAttribute("products", products);
        model.addAttribute("hasData", !products.isEmpty());
        return "products";
    }
}
