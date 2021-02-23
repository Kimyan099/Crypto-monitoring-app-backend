package com.szemereadam.cma.repository;

import com.szemereadam.cma.model.News;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsRepositoryTest {

    @Autowired
    private NewsRepository repository;

    @Test
    public void saveOneSimpleShouldReturnSizeOne() {

        repository.deleteAll();

        News news1 = News.builder()
                .title("First News")
                .content("First news content.")
                .publishedAt("2021-02-23")
                .author("Me")
                .url("url")
                .build();

        repository.save(news1);

        List<News> allNews = repository.findAll();

        assertEquals(1, allNews.size());

    }

    @Test
    public void persistedDataShouldReturnWithValidFields() {

        repository.deleteAll();

        News news2 = News.builder()
                .title("Second News")
                .content("Second news content.")
                .publishedAt("2021-02-23")
                .author("Me")
                .url("url")
                .build();

        repository.save(news2);
        List<News> allNews = repository.findAll();

        assertEquals("Second News", allNews.get(0).getTitle());
        assertEquals("Second news content.", allNews.get(0).getContent());
    }
}