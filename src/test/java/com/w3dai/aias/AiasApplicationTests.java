package com.w3dai.aias;

import com.w3dai.aias.authorInformation.service.AuthorInfoService;
import com.w3dai.aias.authorInformation.entity.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AiasApplicationTests {

    @Autowired
    AuthorInfoService authorInfoService;

    @Test
    public void contextLoads() throws IOException {
        authorInfoService.importDataToMySQL();
    }

}
