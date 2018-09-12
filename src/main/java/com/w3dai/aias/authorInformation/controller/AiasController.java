package com.w3dai.aias.authorInformation.controller;

import com.w3dai.aias.authorInformation.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
