package com.example.spr3;

import com.example.spr3.models.Article;
import com.example.spr3.models.ArticleCategory;
import com.example.spr3.services.articles.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService service;

//    public ArticleController(ArticleService service) {
//        this.service = service;
//    }

    @GetMapping("blog")
    public String blog(@RequestParam(required = false) Integer m,
                       @RequestParam(required = false) Integer y,
                       @RequestParam(required = false) ArticleCategory c,
                       Model model) {
        var filtered = service.getArticlesByParams(m, y, c);
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
        service.addArticle(article);
        return "redirect:/blog";
    }

    @GetMapping("remove-article/{id}")
    public String removeArticle(@PathVariable Long id) {
        service.removeArticle(id);
        return "redirect:/blog";
    }

    @GetMapping("edit-article/{id}")
    public String showEditArticleForm(@PathVariable Long id, Model model) {
        var optionalArticle = service.findArticle(id);

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
        service.updateArticle(id, formArticle);
        return "redirect:/blog";
    }

    @GetMapping("show-article/{id}")
    public String showArticleDetails(@PathVariable Long id, Model model) {
        var optionalArticle = service.findArticle(id);
        if (optionalArticle.isPresent()) {
            var dbArticle = optionalArticle.get();
            model.addAttribute("article",  dbArticle);
            return "blog/article";
        } else {
            return "redirect:/blog";
        }

    }


}
