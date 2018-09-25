package com.w3dai.aias.controller;

import com.w3dai.aias.authorInformation.service.AuthorInfoService;
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
    private AuthorInfoService authorInfoService;

    @Autowired
    public AiasController(AuthorRepository authorRepository, ArticleRepository articleRepository, AuthorService authorService,
                          AuthorInfoService authorInfoService){
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
        this.authorService = authorService;
        this.authorInfoService = authorInfoService;
    }

    @RequestMapping("/")
    public String aiasIndex(){
        return "index";
    }


    @RequestMapping("/search")
    public String searchAction(@RequestParam("searchContent") String searchContent, Model model){
        //List<Article> authorListWithArticleNumber = articleRepository.findByAuthorsNameUsingCustomQuery(searchContent);
        List<Author> searchAuthorResult = null;
        if(searchContent.matches("^[\\u4E00-\\u9FA5]{2,4}"))
            searchAuthorResult = authorInfoService.searchByAuthorName(searchContent);

        if(searchAuthorResult != null){
            model.addAttribute("author", searchAuthorResult);
        }

        authorService.setSearchContent(searchContent);
        List<Article> articleList = authorService.getArticlesBySearchContent(searchContent);

        if(articleList.size() != 0){
            model.addAttribute("articles", articleList);
        }

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

        List<Article> usageList = authorService.getArticlesUsageBySearchContent(searchContent);
        if(usageList.size() != 0){
            model.addAttribute("usageList", usageList);
        }
        model.addAttribute("searchContent", searchContent);
        return "showResult";
    }

    @RequestMapping("/searchArticle")
    public String searchArticleAction(@RequestParam("searchRelatedContent") String authorName, @RequestParam("searchContent") String searchContent,Model model){
        //Page<Article> articleList = articleRepository.findByAuthorsName(searchContent, PageRequest.of(0, 10)););

       // List<Article> articleList = articleRepository.findByAuthorsNameAndArticleText(authorName, authorService.getSearchContent());

        //List<Article> articleList = articleRepository.findByAuthorsNameAndArticleTextUsingCustomQuery(authorName, authorService.getSearchContent());
        List<Article> articleList = authorService.getArticlesByAuthorNameAndSearchContent(authorName);
        if(articleList.size() != 0){
            model.addAttribute("articles", articleList);
        }
        model.addAttribute("searchContent", searchContent);
        return "searchArticleResult";
    }

    @RequestMapping("/searchID")
    public String searchID(@RequestParam("articleID") String articleID, @RequestParam("searchContent") String searchContent, Model model){
        Optional<Article> articleList = articleRepository.findById(articleID);
        if(articleList.isPresent()){
            model.addAttribute("articles", articleList.get());
        }

        model.addAttribute("searchContent", searchContent);

        return "articleResult";
    }

    @RequestMapping("/searchAuthor")
    public String searchAuthorAction(@RequestParam("authorsName") String authorsName, @RequestParam("searchContent") String searchContent,Model model)
    {
        List<Author> authorList = new LinkedList<>();

        String[] authorsNameArray = authorsName.split(";");
        for(int i = 0; i < authorsNameArray.length; i++) {
            List<Author> searchAuthorResult = authorRepository.findByAuthorName(authorsNameArray[i]);
            if(searchAuthorResult.size() > 0)
                authorList.addAll(searchAuthorResult);
        }

        model.addAttribute("authors", authorList);
        model.addAttribute("searchContent", searchContent);
        return "authorInfo";
    }
}
