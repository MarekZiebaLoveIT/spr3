package com.example.spr3.services.articles;

import com.example.spr3.models.Article;
import com.example.spr3.models.ArticleCategory;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    List<Article> getArticles();

    void addArticle(Article article);

    void updateArticle(Long id, Article formArticle);

    void removeArticle(Long id);

    Optional<Article> findArticle(Long id);

    List<Article> getArticlesByParams(Integer m, Integer y, ArticleCategory c);
}
