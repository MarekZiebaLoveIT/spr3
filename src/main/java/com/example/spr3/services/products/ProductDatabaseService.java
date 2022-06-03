package com.example.spr3.services.products;

import com.example.spr3.models.Product;
import com.example.spr3.models.ProductCategory;
import com.example.spr3.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Primary
@Service
public class ProductDatabaseService implements ProductService {


    private final ProductRepository repository;

    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public void addProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        repository.save(product);
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
            repository.save(dbProduct);
        }
    }

    @Override
    public void removeProduct(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Product> findProduct(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Product> getProductsByParam(Integer p, Integer pf, Integer pt, String n, ProductCategory c) {
        return repository.findByParams(pf, pt, n, c);
    }
}
