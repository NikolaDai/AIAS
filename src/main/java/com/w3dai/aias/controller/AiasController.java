package com.w3dai.aias.controller;

import com.w3dai.aias.authorInformation.entity.Author;
import com.w3dai.aias.authorInformation.repository.AuthorRepository;
import com.w3dai.aias.domain.AuthorResult;
import com.w3dai.aias.paperInformation.entity.Article;
import com.w3dai.aias.paperInformation.repository.ArticleRepository;
import com.w3dai.aias.paperInformation.service.AuthorService;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class AiasController {
    private AuthorRepository authorRepository;
    private ArticleRepository articleRepository;
    private AuthorService authorService;

    @Autowired
    public AiasController(AuthorRepository authorRepository, ArticleRepository articleRepository, AuthorService authorService){
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
        this.authorService = authorService;
    }

    @RequestMapping("/")
    public String aiasIndex(){
        return "index";
    }


    @RequestMapping("/search")
    public String searchAction(@RequestParam("searchContent") String searchContent, Model model){
        //List<Article> authorListWithArticleNumber = articleRepository.findByAuthorsNameUsingCustomQuery(searchContent);
        authorService.setSearchContent(searchContent);
        Aggregations newAggregation = authorService.shouldReturnAggregatedResponseForGivenSearchQuery();


        Map<String, Aggregation> map=newAggregation.asMap();
        ArrayList<AuthorResult> authorListWithArticleNumber = new ArrayList<>();
        for(String s:map.keySet()){
            StringTerms a=(StringTerms) map.get(s);
            List<StringTerms.Bucket> list=a.getBuckets();

            for(Terms.Bucket b:list){
                AuthorResult tempAuthor = new AuthorResult();
                if(!b.getKeyAsString().equals("等")) {
                    tempAuthor.setAuthorName(b.getKeyAsString());
                    tempAuthor.setArticleNum((int) b.getDocCount());
                    authorListWithArticleNumber.add(tempAuthor);
                }
            }
        }



        if(authorListWithArticleNumber.size() != 0){
            model.addAttribute("authors", authorListWithArticleNumber);
        }
        return "searchResult";
    }

    @RequestMapping("/searchArticle")
    public String searchArticleAction(@RequestParam("searchContent") String searchContent, Model model){
        //Page<Article> articleList = articleRepository.findByAuthorsName(searchContent, PageRequest.of(0, 10)););

        List<Article> articleList = articleRepository.findByAuthorsName(searchContent);

        if(articleList.size() != 0){
            model.addAttribute("articles", articleList);
        }
        return "searchArticleResult";
    }

    @RequestMapping("/searchAuthor")
    public String searchAuthorAction(@RequestParam("authorsName") String authorsName, Model model)
    {
        String[] authorsArray = authorsName.split(";");
        List<Author> authorList = new LinkedList<>();

        for(int i = 0; i < authorsArray.length; i++){
            List<Author> author = authorRepository.findByAuthorName(authorsArray[i]);
            if(author.size() != 0)
                authorList.add(author.get(0));
        }

        model.addAttribute("authors", authorList);
        return "authorInfo";
    }
}
