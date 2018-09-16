package com.w3dai.aias.paperInformation.service;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@Service
public class AuthorService {
    private ElasticsearchTemplate elasticsearchTemplate;
    private String searchContent;


    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Autowired
    public AuthorService(ElasticsearchTemplate elasticsearchTemplate){
        this.elasticsearchTemplate = elasticsearchTemplate;
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


}
