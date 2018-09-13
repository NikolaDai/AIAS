package com.w3dai.aias.controller;

import com.w3dai.aias.authorInformation.entity.Author;
import com.w3dai.aias.authorInformation.repository.AuthorRepository;
import com.w3dai.aias.paperInformation.entity.Article;
import com.w3dai.aias.paperInformation.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AiasController {
    private AuthorRepository authorRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public AiasController(AuthorRepository authorRepository, ArticleRepository articleRepository){
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
    }

    @RequestMapping("/")
    public String aiasIndex(){
        return "index";
    }

    @RequestMapping("/search")
    public String searchAction(@RequestParam("searchContent") String searchContent, Model model){
        List<Author> authorList = authorRepository.findByAuthorName(searchContent);
        Page<Article> articleList = articleRepository.findByAuthorsName(searchContent, PageRequest.of(0, 10));
        if(authorList != null){
            model.addAttribute("authors", authorList);
            model.addAttribute("articles", articleList);
        }
        return "searchResult";
    }
}
