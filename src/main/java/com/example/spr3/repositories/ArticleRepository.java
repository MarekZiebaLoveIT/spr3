package com.example.spr3.repositories;

import com.example.spr3.models.Article;
import com.example.spr3.models.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("""
        select a from Article a
        where a.title like %:title%
        """)
    List<Article> findByTitle(@Param("title") String title);

    @Query("""
        select a from Article a
        where (:cat is null or a.category = :cat) and
        ((:fromDate is null and :toDate is null) or a.createdAt between :fromDate and :toDate)
        """)
    List<Article> findByParams(
            @Param("cat") ArticleCategory category,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );


}
