package com.w3dai.aias.paperInformation.repository;

import com.w3dai.aias.paperInformation.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

    Page<Article> findByAuthorsName(String name, Pageable pageable);
    List<Article> findByAuthorsName(String name);


    @Query("{\"match\": {\"articleText\":\"?0\"}},\"size\":0,\"aggs\":{\"group_by_state\":{\"terms\":{\"field\":\"authorsName\",\"size\":100}}}")
    List<Article> findByAuthorsNameUsingCustomQuery(String name);
    //Page<Article> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);
}
