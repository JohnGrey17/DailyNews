package com.example.service.parser;

import com.example.dto.NewsRequestDto;
import com.example.service.news.NewsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NewsParsingScheduler {

    @Autowired
    private NewsParserService newsParserService;

    @Autowired
    private NewsService newsService;

    @Scheduled(cron = "0 * * * * *")
    public void parseAndSaveNews() {
        List<NewsRequestDto> parsedNews = newsParserService.parseNews();
        if (parsedNews != null && !parsedNews.isEmpty()) {
            parsedNews.forEach(newsService::addNews);
        } else {
            System.out.println("No news found to parse and save.");
        }
    }

    public void deleteYesterdayNews() {

    }
    //todo налаштувати на 20 хвилин інтервал
}
