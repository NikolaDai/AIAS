package com.w3dai.aias.paperInformation.service;

import com.alibaba.fastjson.JSON;
import com.w3dai.aias.paperInformation.entity.Article;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import static org.elasticsearch.index.query.QueryBuilders.*;

import org.elasticsearch.index.query.QueryShardContext;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortFieldAndFormat;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Method;
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
        //QueryBuilder queryBuilder = commonTermsQuery("articleText", this.getSearchContent());
        QueryBuilder queryBuilder = matchQuery("articleText", this.getSearchContent());
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

        //System.out.println("共搜到:" + searchHits.getTotalHits() + "条结果!");

        return searchResultProcess(searchHits);
    }



    public List<Article> getArticlesUsageBySearchContent(String SearchContent) {
        //usage of QueryBuilders
        QueryBuilder multiMatchQuery = QueryBuilders.boolQuery()
                .must(matchQuery("articleText", SearchContent == null ? this.getSearchContent():SearchContent));

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

       // System.out.println("共搜到:" + searchHits.getTotalHits() + "条结果!");

        return searchResultProcess(searchHits);
    }

    public Map<String, Object> getArticlesBySearchContent(String SearchContent, int fromValue, int sizeValue) {
        //usage of QueryBuilders
        if(SearchContent == null)
            SearchContent = this.getSearchContent();

        QueryBuilder MatchQuery  = QueryBuilders.matchQuery("articleText", SearchContent);

        HighlightBuilder hiBuilder = new HighlightBuilder();
        hiBuilder.preTags("<strong style=\"color:red\">");
        hiBuilder.postTags("</strong>");
        hiBuilder.field("articleText");
        hiBuilder.fragmentSize(30000);

        /*按照时间排序查询
        SortBuilder hiSort = SortBuilders.fieldSort("publishDate")
        .order(SortOrder.DESC);
        SearchResponse response = client.prepareSearch("papers")
                .setQuery(MatchQuery).addSort(hiSort)
                .setFrom(fromValue)
                .setSize(sizeValue)
                .highlighter(hiBuilder)
                .execute().actionGet();
        */
        SearchResponse response = client.prepareSearch("papers")
                .setQuery(MatchQuery)
                .setFrom(fromValue)
                .setSize(sizeValue)
                .highlighter(hiBuilder)
                .execute().actionGet();

        //获取查询结果集
        SearchHits searchHits = response.getHits();

        Map<String, Object> resultWanted = new HashMap<String, Object>();
        resultWanted.put("totalNumOfResults", response.getHits().getTotalHits());
        resultWanted.put("resultsOfWanted", searchResultProcess(searchHits));
        //System.out.println("共搜到:" + searchHits.getTotalHits() + "条结果!");

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
           // System.out.println(articleString);
            article.setId(hit.getId());
            article.setArticleText(articleString);
            articlesWithHighlight.add(article);
        }

        return articlesWithHighlight;
    }

    //proved to be a failure! hope to take some time fix in future.
    //make it reborn as a success on 10/18
    //refer to https://stackoverflow.com/questions/37049764/how-to-provide-highlighting-with-spring-data-elasticsearch/37163711
    public Page<Article>  resultsBySearchArticleContent(String searchContent, Pageable pageable) {
        QueryBuilder MatchQuery = QueryBuilders.matchQuery("articleText", searchContent);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(MatchQuery).withPageable(pageable)
                .withHighlightFields(new HighlightBuilder.Field("articleText").preTags("<strong style=\"color:red\">").postTags("</strong>").fragmentSize(30000)).build();

        Page<Article> page = elasticsearchTemplate.queryForPage(searchQuery, Article.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable){
                ArrayList<Article> articles = new ArrayList<>();
                SearchHits hits = response.getHits();
                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    Article article = JSON.parseObject(searchHit.getSourceAsString(), Article.class);
                    String highLightMessage = searchHit.getHighlightFields().get("articleText").fragments()[0].toString();
                    article.setId(searchHit.getId());
                    article.setArticleText(highLightMessage);

                    /******unknown code which needs to be checked
                    // 反射调用set方法将高亮内容设置进去
                    try {
                        String setMethodName = parSetName("articleText");
                        Class<? extends Article> articleClazz = article.getClass();
                        Method setMethod = articleClazz.getMethod(setMethodName, String.class);
                        setMethod.invoke(article, highLightMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                     ******/
                    articles.add(article);
                }
                if (articles.size() > 0) {
                    return new AggregatedPageImpl<>((List<T>) articles, pageable, response.getHits().getTotalHits());

                }
                return null;
            }
        });

        return page;
    }

    private String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);

    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

}