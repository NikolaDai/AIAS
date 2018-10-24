package com.w3dai.aias.authorInformation.service;

import com.w3dai.aias.authorInformation.entity.Author;
import com.w3dai.aias.authorInformation.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class AuthorInfoService {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorInfoService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public void importDataToMySQL() throws IOException
    {
        File file = new File("authorNameList.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String authorName = null;
        while((authorName = bufferedReader.readLine()) != null){
            Author author = new Author();
            author.setAuthorName(authorName);
            authorRepository.save(author);
        }

    }

    public List<Author> searchByAuthorName(String authorName){

        return authorRepository.findByAuthorName(authorName);
    }
}
