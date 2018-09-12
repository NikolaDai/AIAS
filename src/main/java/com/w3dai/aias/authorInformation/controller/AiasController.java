package com.w3dai.aias.authorInformation.controller;

import com.w3dai.aias.authorInformation.entity.Author;
import com.w3dai.aias.authorInformation.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AiasController {
    private AuthorRepository authorRepository;

    @Autowired
    public AiasController(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @RequestMapping("/")
    public String aiasIndex(){
        return "index";
    }

    @RequestMapping("/search")
    public String searchAction(@RequestParam("searchContent") String searchContent, Model model){
        System.out.print(searchContent);
        List<Author> authorList = authorRepository.findByAuthorName(searchContent);
        if(authorList != null){
            model.addAttribute("authors", authorList);
        }
        return "searchResult";
    }
}
