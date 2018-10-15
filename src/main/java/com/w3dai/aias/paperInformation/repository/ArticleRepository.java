package com.w3dai.aias.paperInformation.repository;

import com.w3dai.aias.paperInformation.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

    Page<Article> findByAuthorsName(String name, Pageable pageable);

    Page<Article> findByArticleText(String name, Pageable pageable);

    @Query("{\"bool\":{\n" +
            "\"must\":[\n" +
            "{\"match\":{\"articleText\":\"?1\"}},\n" +
            "{\"match\":{\"authorsName\":\"?0\"}}\n" +
            "]\n" +
            "}\n" +
            "}}")
    List<Article> findByAuthorsNameAndArticleTextUsingCustomQuery(String name, String searchContent);
    //Page<Article> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);
}
