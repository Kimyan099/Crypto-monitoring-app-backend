package com.szemereadam.cma.controller;

import com.szemereadam.cma.model.News;
import com.szemereadam.cma.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    private static final String ORIGIN = "http://localhost:3000";

    @Autowired
    private NewsRepository newsRepository;

    @CrossOrigin(origins = ORIGIN)
    @GetMapping("/news/category/allnews/orderby/desc")
    public List<News> newsListDESC() {
        return newsRepository.getAllNewsDESC();
    }

    @CrossOrigin(origins = ORIGIN)
    @GetMapping("/news/category/allnews/orderby/asc")
    public List<News> newsListASC() {
        return newsRepository.getAllNewsASC();
    }
}
