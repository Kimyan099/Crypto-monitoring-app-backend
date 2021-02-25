package com.szemereadam.cma;

import com.szemereadam.cma.HttpConnection.HttpConnection;
import com.szemereadam.cma.service.CurrencyService;
import com.szemereadam.cma.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CmaApplication {

    private static final String NEWS_URL = "https://data.messari.io/api/v1/news";

    private static final String CURRENCIES_URL = "https://data.messari.io/api/v1/assets";

    private static final Logger LOGGER = LoggerFactory.getLogger(CmaApplication.class);

    @Autowired
    private NewsService newsService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private HttpConnection httpConnection;

    public static void main(String[] args) {
        SpringApplication.run(CmaApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {

        return args -> {
            String newsJsonResponse = httpConnection.getContent(NEWS_URL); // for news
            String currenciesJsonResponse = httpConnection.getContent(CURRENCIES_URL); // for currencies

            newsService.persistObjects(newsJsonResponse); // for news
            currencyService.persistObjects(currenciesJsonResponse); // for currencies
        };
    }

    @PostConstruct
    public void afterInit() {
        LOGGER.info(httpConnection.toString());
    }


}
