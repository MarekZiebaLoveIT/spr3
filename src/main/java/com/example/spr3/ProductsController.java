package com.example.spr3;

import com.example.spr3.models.Product;
import com.example.spr3.models.ProductCategory;
import com.example.spr3.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ProductsController {

    private final ProductService service;

    @GetMapping("products")
    public String products(@RequestParam(required = false) Integer p,
                           @RequestParam(required = false) Integer pf,
                           @RequestParam(required = false) Integer pt,
                           @RequestParam(required = false) String n,
                           @RequestParam(required = false) ProductCategory c,
                           Model model) {
        var filtered = service.getProductsByParam(p, pf, pt, n, c);
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
        service.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("remove-product/{id}")
    public String removeProduct(@PathVariable Long id) {
        service.removeProduct(id);
        return "redirect:/products";
    }

    @GetMapping("edit-product/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        var optionalProduct = service.findProduct(id);

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
    public String editProduct(@PathVariable Long id, Product formProduct) {
        service.updateProduct(id, formProduct);
        return "redirect:/products";
    }

    @GetMapping("show-product/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        var optionalProduct = service.findProduct(id);

        if (optionalProduct.isPresent()) {
            var dbProduct = optionalProduct.get();
            model.addAttribute("product",  dbProduct);
            return "products/product";
        } else {
            return "redirect:/products";
        }
    }

}
