package com.example.spr3.services.products;

import com.example.spr3.models.Product;
import com.example.spr3.models.ProductCategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductListService implements ProductService {

    private Long nextId;
    private List<Product> products = new ArrayList<>();

    public ProductListService() {
        var now = LocalDateTime.now();
        products.add(new Product(1L, "Samsung", "Desc0", 1500, ProductCategory.PHONES, now, null));
        products.add(new Product(2L, "Nokia", "Desc1", 1200, ProductCategory.PHONES, now.minusMonths(1), null));
        products.add(new Product(3L, "LG", "Desc3", 1100, ProductCategory.PHONES, now.minusMonths(1), null));
        products.add(new Product(4L, "Dell", "Desc4", 2200, ProductCategory.MONITORS, now.minusMonths(2), null));
        products.add(new Product(5L, "AKG", "Desc5", 900, ProductCategory.SPEAKERS, now.minusMonths(3), null));
        products.add(new Product(6L, "Xiaomi", "Desc5", 600, ProductCategory.PHONES, now.minusMonths(3), null));
        nextId = 7L;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void addProduct(Product product) {
        product.setId(nextId);
        product.setCreatedAt(LocalDateTime.now());
        products.add(product);
        nextId++;
    }

    @Override
    public void removeProduct(Long id) {
        products.removeIf(article -> id.equals(article.getId()));
    }

    @Override
    public Optional<Product> findProduct(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public void updateProduct(Long id, Product formProduct) {
        var optionalProduct = findProduct(id);
        if (optionalProduct.isPresent()) {
            var dbProduct = optionalProduct.get();
            dbProduct.setName(formProduct.getName());
            dbProduct.setPrice(formProduct.getPrice());
            dbProduct.setDescription(formProduct.getDescription());
            dbProduct.setCategory(formProduct.getCategory());
            dbProduct.setUpdatedAt(LocalDateTime.now());
        }
    }

    @Override
    public List<Product> getProductsByParam(Integer p, Integer pf, Integer pt, String n, ProductCategory c) {
        return products.stream()
        .filter(product -> p == null || product.getPrice() <= 1.2 * p && product.getPrice() >= 0.8 * p)
                .filter(product -> pf == null || product.getPrice() >= pf)
                .filter(product -> pt == null || product.getPrice() <= pt)
                .filter(product -> n == null || product.getName().contains(n))
                .filter(product -> c == null || product.getCategory() == c)
                .toList();
    }
}
