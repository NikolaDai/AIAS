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

    @Query("{\"bool\":{\"must\":[{\"match\":{\"authorsName\":\"?0\"}},{\"match\":{\"articleText\":\"?1\"}}]}}")
    Page<Article> findByAuthorsNameAndArticleText(String name, String searchContent, Pageable pageable);

    Page<Article> findByColumnName(String name, Pageable pageable);

}
