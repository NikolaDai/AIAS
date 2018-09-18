package com.w3dai.aias.paperInformation.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.w3dai.aias.paperInformation.entity.Article;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@Service
public class AuthorService {
    private ElasticsearchTemplate elasticsearchTemplate;
    private Client client;
    private String searchContent;


    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Autowired
    public AuthorService(ElasticsearchTemplate elasticsearchTemplate, Client client) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.client = client;
    }

    public Aggregations shouldReturnAggregatedResponseForGivenSearchQuery() {
        // given
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("articleText", this.getSearchContent());
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withSearchType(SearchType.DEFAULT)
                .withIndices("papers").withTypes("article")
                .addAggregation(terms("authorsName").field("authorsName").size(10000))
                .build();

        // when
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        return aggregations;
    }

    public List<Article> getArticlesByAuthorNameAndSearchContent(String authorName) {
        //usage of QueryBuilders
        QueryBuilder multiMatchQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("articleText", this.getSearchContent()))
                .must(QueryBuilders.matchQuery("authorsName", authorName));
        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("\"<strong style=\"color:red\">");
        hiBuilder.postTags("</strong>\"");
        hiBuilder.field("articleText");
        // 搜索数据
        SearchResponse response = client.prepareSearch("papers")
                .setQuery(multiMatchQuery)
                .highlighter(hiBuilder)
                .execute().actionGet();
        //获取查询结果集
        SearchHits searchHits = response.getHits();

        System.out.println("共搜到:" + searchHits.getTotalHits() + "条结果!");

        List<Article> articlesWithHighlight = new ArrayList<>();
        //遍历结果
        for (SearchHit hit : searchHits) {

            Article article = JSON.parseObject(hit.getSourceAsString(), Article.class);
            Text[] text = hit.getHighlightFields().get("articleText").getFragments();
            String articleString = "";
            for(Text str : text){
                articleString += (str+"</br>");
            }

            article.setArticleText(articleString);
            articlesWithHighlight.add(article);
        }
        return articlesWithHighlight;
    }

}