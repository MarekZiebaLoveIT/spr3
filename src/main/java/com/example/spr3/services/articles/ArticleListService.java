package com.example.spr3.services.articles;

import com.example.spr3.models.Article;
import com.example.spr3.models.ArticleCategory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ArticleListService implements ArticleService {

    private Long nextId;
    private List<Article> articles = new ArrayList<>();

    public ArticleListService() {
        var now = LocalDateTime.now();
        articles.add(Article.builder().id(1L).title("Tytuł1").content("Treść 1").category(ArticleCategory.GUIDE).createdAt(now).build());  // builder
        articles.add(new Article(2L, "Tytuł 2", "Treść 2", ArticleCategory.TECHNICAL, now.minusMonths(2), null));
        articles.add(new Article(3L, "Tytuł 3", "Treść 3", ArticleCategory.GUIDE, now.minusMonths(2), null));
        articles.add(new Article(4L, "Tytuł 4", "Treść 4", ArticleCategory.OTHER, now.minusMonths(3), null));
        articles.add(new Article(5L, "Tytuł 5", "Treść 5", ArticleCategory.TECHNICAL, now.minusMonths(3).minusYears(1), null));
        nextId = 6L;
    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }

    @Override
    public void addArticle(Article article) {
        article.setId(nextId);
        article.setCreatedAt(LocalDateTime.now());
        articles.add(article);
        nextId++;
    }

    @Override
    public void removeArticle(Long id) {
        articles.removeIf(article -> id.equals(article.getId()));
    }

    @Override
    public Optional<Article> findArticle(Long id) {
        return articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst();
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
        }
    }

    @Override
    public List<Article> getArticlesByParams(Integer m, Integer y, ArticleCategory c) {
        return articles.stream()
        .filter(article -> m == null || article.getCreatedAt().getMonthValue() == m)
                .filter(article -> y == null || article.getCreatedAt().getYear() == y)
                .filter(product -> c == null || product.getCategory() == c)
                .toList();
    }
}
