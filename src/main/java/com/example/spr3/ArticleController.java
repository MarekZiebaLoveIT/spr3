package com.example.spr3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.spr3.models.Article;
import com.example.spr3.models.ArticleCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class ArticleController {

    private Long nextId;
    private List<Article> articles = new ArrayList<>();

    public ArticleController() {
        var now = LocalDateTime.now();
        articles.add(new Article(1L, "Tytuł 1", "Treść 1", ArticleCategory.GUIDE, now, null));
        articles.add(new Article(2L, "Tytuł 2", "Treść 2", ArticleCategory.TECHNICAL, now.minusMonths(2), null));
        articles.add(new Article(3L, "Tytuł 3", "Treść 3", ArticleCategory.GUIDE, now.minusMonths(2), null));
        articles.add(new Article(4L, "Tytuł 4", "Treść 4", ArticleCategory.OTHER, now.minusMonths(3), null));
        articles.add(new Article(5L, "Tytuł 5", "Treść 5", ArticleCategory.TECHNICAL, now.minusMonths(3).minusYears(1), null));
        nextId = 6L;
    }

    @GetMapping("blog")
    public String blog(@RequestParam(required = false) Integer m,
                       @RequestParam(required = false) Integer y,
                       @RequestParam(required = false) ArticleCategory c,
                       Model model) {
        var filtered = articles.stream()
                .filter(article -> m == null || article.getCreatedAt().getMonthValue() == m)
                .filter(article -> y == null || article.getCreatedAt().getYear() == y)
                .filter(product -> c == null || product.getCategory() == c)
                .toList();
        model.addAttribute("categories", ArticleCategory.values());
        model.addAttribute("items", filtered);
        model.addAttribute("month", m);
        model.addAttribute("year", y);
        model.addAttribute("category", c);
        return "blog/blog";
    }

    @GetMapping("add-article")
    public String showAddArticleForm(Model model) {
        model.addAttribute("categories", ArticleCategory.values());
        model.addAttribute("header", "Add article");
        return "admin/addArticle";
    }

    @PostMapping("add-article")
    public String addArticle(Article article) {

        article.setId(nextId);
        article.setCreatedAt(LocalDateTime.now());
        articles.add(article);
        nextId++;

        return "redirect:/blog";
    }

    @GetMapping("remove-article/{id}")
    public String removeArticle(@PathVariable Long id) {
        articles.removeIf(article -> id.equals(article.getId()));
        return "redirect:/blog";
    }

    @GetMapping("edit-article/{id}")
    public String showEditArticleForm(@PathVariable Long id, Model model) {
        var optionalArticle = articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst();

        if (optionalArticle.isPresent()) {
            var article = optionalArticle.get();
            model.addAttribute("article", article);
            model.addAttribute("categories", ArticleCategory.values());
            model.addAttribute("header", "Edit article");
            return "admin/addArticle";
        } else {
            return "redirect:/blog";
        }
    }

    @PostMapping("edit-article/{id}")
    public String editArticle(@PathVariable Long id, Article formArticle) {
        var optionalArticle = articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst();
        if (optionalArticle.isPresent()) {
            var dbArticle = optionalArticle.get();
            dbArticle.setTitle(formArticle.getTitle());
            dbArticle.setContent(formArticle.getContent());
            dbArticle.setCategory(formArticle.getCategory());
            dbArticle.setUpdatedAt(LocalDateTime.now());
        }
        return "redirect:/blog";
    }

    @GetMapping("show-article/{id}")
    public String showArticleDetails(@PathVariable Long id, Model model) {
        var optionalArticle = articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst();
        if (optionalArticle.isPresent()) {
            var dbArticle = optionalArticle.get();
            model.addAttribute("article",  dbArticle);
            return "blog/article";
        } else {
            return "redirect:/blog";
        }

    }


}
