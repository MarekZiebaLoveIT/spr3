package com.example.spr3;

import com.example.spr3.models.Product;
import com.example.spr3.models.ProductCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ProductsController {

    private Long nextId;
    private List<Product> products = new ArrayList<>();

    public ProductsController() {
        var now = LocalDateTime.now();
        products.add(new Product(1L, "Samsung", "Desc0", 1500, ProductCategory.PHONES, now, null));
        products.add(new Product(2L, "Nokia", "Desc1", 1200, ProductCategory.PHONES, now.minusMonths(1), null));
        products.add(new Product(3L, "LG", "Desc3", 1100, ProductCategory.PHONES, now.minusMonths(1), null));
        products.add(new Product(4L, "Dell", "Desc4", 2200, ProductCategory.MONITORS, now.minusMonths(2), null));
        products.add(new Product(5L, "AKG", "Desc5", 900, ProductCategory.SPEAKERS, now.minusMonths(3), null));
        products.add(new Product(6L, "Xiaomi", "Desc5", 600, ProductCategory.PHONES, now.minusMonths(3), null));
        nextId = 7L;
    }



    @GetMapping("products")
    public String products(@RequestParam(required = false) Integer p,
                           @RequestParam(required = false) Integer pf,
                           @RequestParam(required = false) Integer pt,
                           @RequestParam(required = false) String n,
                           @RequestParam(required = false) ProductCategory c,
                           Model model) {
        var filtered = products.stream()
                .filter(product -> p == null || product.getPrice() <= 1.2 * p && product.getPrice() >= 0.8 * p)
                .filter(product -> pf == null || product.getPrice() >= pf)
                .filter(product -> pt == null || product.getPrice() <= pt)
                .filter(product -> n == null || product.getName().contains(n))
                .filter(product -> c == null || product.getCategory() == c)
                .toList();
        model.addAttribute("categories", ProductCategory.values());
        model.addAttribute("items", filtered);
        model.addAttribute("priceFrom", pf);
        model.addAttribute("priceTo", pt);
        model.addAttribute("name", n);
        model.addAttribute("category", c);
        return "products/products";
    }

    @GetMapping("add-product")
    public String addProductForm(Model model) {
        model.addAttribute("categories", ProductCategory.values());
        return "admin/addProduct";
    }

    @PostMapping("add-product")
    public String addProduct(Product product) {

        product.setId(nextId);
        product.setCreatedAt(LocalDateTime.now());
        products.add(product);
        nextId++;

        return "redirect:/products";
    }

    @GetMapping("remove-product/{id}")
    public String removeArticle(@PathVariable Long id) {
        products.removeIf(article -> id.equals(article.getId()));
        return "redirect:/products";
    }

    @GetMapping("edit-product/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        var optionalProduct = products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();

        if (optionalProduct.isPresent()) {
            var product = optionalProduct.get();
            model.addAttribute("product", product);
            model.addAttribute("categories", ProductCategory.values());
            model.addAttribute("header", "Edit product");
            return "admin/addProduct";
        } else {
            return "redirect:/products";
        }
    }

    @PostMapping("edit-product/{id}")
    public String editArticle(@PathVariable Long id, Product formProduct) {
        var optionalProduct = products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
        if (optionalProduct.isPresent()) {
            var dbProduct = optionalProduct.get();
            dbProduct.setName(formProduct.getName());
            dbProduct.setPrice(formProduct.getPrice());
            dbProduct.setDescription(formProduct.getDescription());
            dbProduct.setCategory(formProduct.getCategory());
            dbProduct.setUpdatedAt(LocalDateTime.now());
        }
        return "redirect:/products";
    }

}
