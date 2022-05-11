package com.example.spr3.services.articles;

import com.example.spr3.models.Article;
import com.example.spr3.models.ArticleCategory;
import com.example.spr3.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Primary
@Service
public class ArticleDatabaseService implements ArticleService {

    @Autowired
    private ArticleRepository repository;

    @Override
    public List<Article> getArticles() {
        return null;
    }

    @Override
    public void addArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        repository.save(article);
    }

    @Override
    public void updateArticle(Long id, Article formArticle) {
        var optionalArticle = findArticle(id);
        if (optionalArticle.isPresent()) {
            var dbArticle = optionalArticle.get();
            dbArticle.setTitle(formArticle.getTitle());
            dbArticle.setContent(formArticle.getContent());
            dbArticle.setCategory(formArticle.getCategory());
            dbArticle.setUpdatedAt(LocalDateTime.now());
            repository.save(dbArticle);
        }
    }

    @Override
    public void removeArticle(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Article> findArticle(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Article> getArticlesByParams(Integer m, Integer y, ArticleCategory c) {
        return repository.findAll();
    }
}
