package com.example.spr3.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(length = 1023)
    private String content;
    @Enumerated(EnumType.STRING)
    private ArticleCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
