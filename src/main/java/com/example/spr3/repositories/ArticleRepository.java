package com.example.spr3.repositories;

import com.example.spr3.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ArticleRepository extends JpaRepository<Article, Long> {
}
