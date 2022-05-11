package com.example.spr3.services.products;

import com.example.spr3.models.Product;
import com.example.spr3.models.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();

    void addProduct(Product product);

    void updateProduct(Long id, Product formProduct);

    void removeProduct(Long id);

    Optional<Product> findProduct(Long id);

    List<Product> getProductsByParam(Integer p, Integer pf, Integer pt, String n, ProductCategory c);
}
