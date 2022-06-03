package com.example.spr3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(length = 1023)
    private String description;
    private int price;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
