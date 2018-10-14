package com.w3dai.aias.paperInformation.service;

import com.alibaba.fastjson.JSON;
import com.w3dai.aias.paperInformation.entity.Article;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import static org.elasticsearch.index.query.QueryBuilders.*;
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

import java.util.*;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@Service
public class AuthorService {
    private ElasticsearchTemplate elasticsearchTemplate;
    private Client client;
    private String searchContent;

    @Autowired
    public AuthorService(ElasticsearchTemplate elasticsearchTemplate, Client client) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.client = client;
    }

    //Key function: based on the querying results, show the most related authors.
    public Aggregations shouldReturnAggregatedResponseForGivenSearchQuery() {
        // given
        //QueryBuilder queryBuilder = matchQuery("articleText", this.getSearchContent()).operator(Operator.AND);
        QueryBuilder queryBuilder = commonTermsQuery("articleText", this.getSearchContent());
        //QueryBuilder qb = matchAllQuery();
        //note that you can easily print(aka debug) json generated queries using toString() method on QueryBuilder object.
        System.out.println(queryBuilder.toString());

        //QueryBuilder queryBuilder = QueryBuilders.matchQuery("articleText", this.getSearchContent());
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

    //return the author's article list
    public List<Article> getArticlesByAuthorNameAndSearchContent(String authorName) {
        //usage of QueryBuilders
        QueryBuilder multiMatchQuery = QueryBuilders.boolQuery()
                .must(matchQuery("articleText", this.getSearchContent()))
                .must(matchQuery("authorsName", authorName));
        /***
        *    "highlight" : {
        *         "fields" : {
        *             "articleText" : {}
        *         },
        * "boundary_scanner_locale": "zh-cn",
        * "boundary_scanner":"sentence",
        * "type":"unified"
        *     }
        *  */
        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<strong style=\"color:red\">");
        hiBuilder.postTags("</strong>");
        hiBuilder.field("articleText");
        hiBuilder.boundaryScannerLocale("zh-cn");
        hiBuilder.boundaryScannerType("sentence");
        hiBuilder.highlighterType("unified");

        // 搜索数据
        SearchResponse response = client.prepareSearch("papers")
                .setQuery(multiMatchQuery)
                .highlighter(hiBuilder)
                .execute().actionGet();
        //获取查询结果集
        SearchHits searchHits = response.getHits();

        System.out.println("共搜到:" + searchHits.getTotalHits() + "条结果!");

        return searchResultProcess(searchHits);
    }



    public List<Article> getArticlesUsageBySearchContent(String SearchContent) {
        //usage of QueryBuilders
        QueryBuilder multiMatchQuery = QueryBuilders.boolQuery()
                .must(matchQuery("articleText", this.getSearchContent()));
        /***
         *    "highlight" : {
         *         "fields" : {
         *             "articleText" : {}
         *         },
         * "boundary_scanner_locale": "zh-cn",
         * "boundary_scanner":"sentence",
         * "type":"unified"
         *     }
         *  */
        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<strong style=\"color:red\">");
        hiBuilder.postTags("</strong>");
        hiBuilder.field("articleText");
        hiBuilder.boundaryScannerLocale("zh-cn");
        hiBuilder.boundaryScannerType("sentence");
        hiBuilder.highlighterType("unified");

        // 搜索数据
        SearchResponse response = client.prepareSearch("papers")
                .setQuery(multiMatchQuery)
                .highlighter(hiBuilder)
                .execute().actionGet();
        //获取查询结果集
        SearchHits searchHits = response.getHits();

        System.out.println("共搜到:" + searchHits.getTotalHits() + "条结果!");

        return searchResultProcess(searchHits);
    }


/*
    public List<Article> getArticlesBySearchContent(String SearchContent) {
        //usage of QueryBuilders
        QueryBuilder MatchQuery = QueryBuilders.matchQuery("articleText", this.getSearchContent());

        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<strong style=\"color:red\">");
        hiBuilder.postTags("</strong>");
        hiBuilder.field("articleText");
        hiBuilder.fragmentSize(30000);

        SearchResponse response = client.prepareSearch("papers")
                .setQuery(MatchQuery)
                .setFrom(0)
                .setSize(20)
                .highlighter(hiBuilder)
                .execute().actionGet();

        //获取查询结果集
        SearchHits searchHits = response.getHits();

        System.out.println("共搜到:" + searchHits.getTotalHits() + "条结果!");

        return searchResultProcess(searchHits);
    }
*/

    public Map<String, Object> getArticlesBySearchContent(String SearchContent) {
        //usage of QueryBuilders
        QueryBuilder MatchQuery = QueryBuilders.matchQuery("articleText", this.getSearchContent());

        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<strong style=\"color:red\">");
        hiBuilder.postTags("</strong>");
        hiBuilder.field("articleText");
        hiBuilder.fragmentSize(30000);

        SearchResponse response = client.prepareSearch("papers")
                .setQuery(MatchQuery)
                .setFrom(0)
                .setSize(20)
                .highlighter(hiBuilder)
                .execute().actionGet();

        //获取查询结果集
        SearchHits searchHits = response.getHits();

        Map<String, Object> resultWanted = new HashMap<String, Object>();
        resultWanted.put("totalNumOfResults", response.getHits().getTotalHits());
        resultWanted.put("resultsOfWanted", searchResultProcess(searchHits));
        System.out.println("共搜到:" + searchHits.getTotalHits() + "条结果!");

        return resultWanted;
    }

    public List<Article> searchResultProcess(SearchHits searchHits){
        List<Article> articlesWithHighlight = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            Article article = JSON.parseObject(hit.getSourceAsString(), Article.class);
            Text[] text = hit.getHighlightFields().get("articleText").getFragments();
            String articleString = "";
            for(Text str : text){
                articleString += (str+"</br>");
            }

            article.setId(hit.getId());
            article.setArticleText(articleString);
            articlesWithHighlight.add(article);
        }

        return articlesWithHighlight;
    }


    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

}