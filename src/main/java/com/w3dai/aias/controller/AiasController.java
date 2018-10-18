package com.w3dai.aias.controller;

import com.w3dai.aias.authorInformation.service.AuthorInfoService;
import com.w3dai.aias.authorInformation.entity.Author;
import com.w3dai.aias.authorInformation.repository.AuthorRepository;
import com.w3dai.aias.domain.AuthorResult;
import com.w3dai.aias.domain.PagedList;
import com.w3dai.aias.paperInformation.entity.Article;
import com.w3dai.aias.paperInformation.repository.ArticleRepository;
import com.w3dai.aias.paperInformation.service.AuthorService;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class AiasController {
    private AuthorRepository authorRepository;
    private ArticleRepository articleRepository;
    private AuthorService authorService;
    private AuthorInfoService authorInfoService;

    //support the pagination function
    private static final int INIT_PAGE_SIZE = 20;
    private static final int INIT_PAGE_INDEX = 0;
    private static final int[] PAGE_SIZES = {5, 10, 20};


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

/*
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
        Map<String, Object> searchDataResults = authorService.getArticlesBySearchContent(searchContent, 20, 0);

        List<Article> articleList = (List<Article>)searchDataResults.get("resultsOfWanted");

        if(articleList.size() != 0){
            model.addAttribute("searchedArticlesNum", searchDataResults.get("totalNumOfResults"));
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
*/
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

    @RequestMapping(value = {"/searchAuthor"}, method = {RequestMethod.GET})
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

    @RequestMapping(value = {"/searchAuthor"}, method = {RequestMethod.POST})
    public String updateAuthorInfo(@RequestParam("authorName") String authorName,
                                   @RequestParam("cellNumber") String cellNumber,
                                   @RequestParam("phoneNumber") String phoneNumber,
                                   @RequestParam("QQ") String QQ,
                                   @RequestParam("weChat") String weChat,
                                   @RequestParam("emailAddress") String emailAddress,
                                   @RequestParam("organizationName") String organizationName,
                                   @RequestParam("mailAddress") String mailAddress,
                                   @RequestParam("zipCode") String zipCode,
                                   @RequestParam("workCityName") String workCityName,
                                   @RequestParam("workProvinceName") String workProvinceName,
                                   Model model)
    {
        Author newAuthorInfo = new Author();
        List<Author> searchAuthorResult = authorRepository.findByAuthorName(authorName);
        newAuthorInfo.setId(searchAuthorResult.get(0).getId());
        newAuthorInfo.setAuthorName(authorName);
        newAuthorInfo.setCellNumber(cellNumber);
        newAuthorInfo.setPhoneNumber(phoneNumber);
        newAuthorInfo.setQQ(QQ);
        newAuthorInfo.setWeChat(weChat);
        newAuthorInfo.setEmailAddress(emailAddress);
        newAuthorInfo.setOrganizationName(organizationName);
        newAuthorInfo.setMailAddress(mailAddress);
        newAuthorInfo.setZipCode(zipCode);
        newAuthorInfo.setWorkCityName(workCityName);
        newAuthorInfo.setWorkProvinceName(workProvinceName);
        authorRepository.save(newAuthorInfo);
        List<Author> authorList = authorRepository.findByAuthorName(authorName);
        model.addAttribute("authors", authorList);

        return "authorInfo";
    }

    //thymeleaf-spring-data-dialect：proved to be useful when dealing with fixed repository class
    @RequestMapping("/searchTest")
    public String list(ModelMap model, @SortDefault("mainTitle") Pageable pageable){
        //model.addAttribute("page", articleRepository.findByArticleText("海军", pageable));
        model.addAttribute("page", authorService.resultsBySearchArticleContent("海军", pageable));
        return "testTable";
    }

    @RequestMapping("/searchByTime")
    public String searchByTime(ModelMap model, @SortDefault("mainTitle") Pageable pageable){
        //model.addAttribute("page", articleRepository.findByArticleText("海军", pageable));
        model.addAttribute("page", authorService.resultsBySearchArticleContent("海军", pageable));
        return "testTable";
    }

    /**
     *demo code
     */
    @RequestMapping(value = "/searchByPage", method = RequestMethod.GET)
    public String getRequestedArticles(@RequestParam(value="searchContent",required = false,defaultValue = "海军") String searchContent,
                                       @RequestParam("pageSize") Optional<Integer> pageSize,
                                       @RequestParam("page") Optional<Integer> page,
                                       Model model){
    int evalPageSize = pageSize.orElse(INIT_PAGE_SIZE);
    int evalPageIndex = (page.orElse(0) < 1) ? 0 : page.get() - 1;

    int fromValue = evalPageIndex * evalPageSize;
    PagedList<Article> pagedList = new PagedList<>(evalPageSize, evalPageIndex);

    Map<String, Object> searchDataResults = authorService.getArticlesBySearchContent(searchContent, fromValue, evalPageSize);
    List<Article> articleList = (List<Article>)searchDataResults.get("resultsOfWanted");

    int totalCount = Integer.parseInt(searchDataResults.get("totalNumOfResults").toString());

    pagedList.setItemList(articleList);
    pagedList.setTotalCount(totalCount);
    model.addAttribute("list", pagedList);
    model.addAttribute("pageSizes", PAGE_SIZES);
    model.addAttribute("searchContent", searchContent);

    return "articles";
    }

    @RequestMapping("/search")
    public String searchAction(@RequestParam(value="searchContent",required = false,defaultValue = "海军") String searchContent,
                               @RequestParam("pageSize") Optional<Integer> pageSize,
                               @RequestParam("page") Optional<Integer> page,
                               Model model){
        List<Author> searchAuthorResult = null;
        if(searchContent.matches("^[\\u4E00-\\u9FA5]{2,4}"))
            searchAuthorResult = authorInfoService.searchByAuthorName(searchContent);

        if(searchAuthorResult != null){
            model.addAttribute("author", searchAuthorResult);
        }

        authorService.setSearchContent(searchContent);

        int evalPageSize = pageSize.orElse(INIT_PAGE_SIZE);
        int evalPageIndex = (page.orElse(0) < 1) ? 0 : page.get() - 1;

        int fromValue = evalPageIndex * evalPageSize;
        PagedList<Article> pagedList = new PagedList<>(evalPageSize, evalPageIndex);

        Map<String, Object> searchDataResults = authorService.getArticlesBySearchContent(searchContent, fromValue, evalPageSize);
        List<Article> articleList = (List<Article>)searchDataResults.get("resultsOfWanted");

        int totalCount = Integer.parseInt(searchDataResults.get("totalNumOfResults").toString());

        pagedList.setItemList(articleList);
        pagedList.setTotalCount(totalCount);
        model.addAttribute("list", pagedList);
        model.addAttribute("pageSizes", PAGE_SIZES);
        model.addAttribute("searchContent", searchContent);
        model.addAttribute("searchedArticlesNum", searchDataResults.get("totalNumOfResults"));

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
        return "showResultPage";
    }
}
