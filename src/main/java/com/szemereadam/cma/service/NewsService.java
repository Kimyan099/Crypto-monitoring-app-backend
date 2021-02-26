package com.szemereadam.cma.service;

import com.szemereadam.cma.Logger.ExceptionLog;
import com.szemereadam.cma.model.News;
import com.szemereadam.cma.repository.NewsRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    private final ExceptionLog exceptionLog = new ExceptionLog();

    public void persistObjects(String content) {

        try {
            JSONObject jsonContent = new JSONObject(content);
            JSONArray currContent = jsonContent.getJSONArray("data");

            for (int i = 0; i < currContent.length(); i++) {

                JSONObject currentObj = currContent.getJSONObject(i);
                News news = News.builder()
                        .title(currentObj.getString("title"))
                        .content(currentObj.getString("content"))
                        .publishedAt(currentObj.getString("published_at"))
                        .author(currentObj.getJSONObject("author").getString("name"))
                        .url(currentObj.getString("url"))
                        .build();

                newsRepository.save(news);
            }

            newsRepository.flush();

        } catch (JSONException e) {

            exceptionLog.log(e);
            throw new IllegalArgumentException("Content format is not valid");
        }
    }
}

