package com.example.spr3.repositories;

import com.example.spr3.models.Product;
import com.example.spr3.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product, Long> {

    @Query("""
            select p from Product p where
            (:name is null or p.name like %:name%) and
            (:category is null or p.category = :category) and
            (:priceFrom is null or p.price >= :priceFrom) and
            (:priceTo is null or p.price <= :priceTo)
            """)
    List<Product> findByParams(
            @Param("priceFrom") Integer pf,
            @Param("priceTo") Integer pt,
            @Param("name") String n,
            @Param("category")ProductCategory c
            );

}
