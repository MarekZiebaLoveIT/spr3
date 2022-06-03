package com.example.spr3.services.articles;

import com.example.spr3.models.Article;
import com.example.spr3.models.ArticleCategory;
import com.example.spr3.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Primary
@Service
public class ArticleDatabaseService implements ArticleService {

    private final ArticleRepository repository;

    @Override
    public List<Article> getArticles() {
        return null;
    }

    @Override
    public void addArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        log.info(article.toString());  // loger do wyświetlania info -> używa się zamiast System.out.println
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
        LocalDateTime from = null;
        LocalDateTime to = null;

        if (y != null && m != null) {
            from = LocalDate.of(y, m, 1).atStartOfDay();
            to = LocalDate.of(y, m + 1, 1).atStartOfDay();
        } else if (y == null && m != null) {
            from = LocalDate.of(LocalDate.now().getYear(), m, 1).atStartOfDay();
            to = LocalDate.of(LocalDate.now().getYear(), m + 1, 1).atStartOfDay();
        } else if (y != null) {
            from = LocalDate.of(y, 1, 1).atStartOfDay();
            to = LocalDate.of(y + 1, 1, 1).atStartOfDay();
        }
        return repository.findByParams(c, from, to);
    }
}
