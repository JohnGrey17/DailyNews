package com.example.service.parser;

import com.example.dto.NewsRequestDto;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class NewsParserServiceImpl {

    private final String newsUrl = "https://www.pravda.com.ua/news/";


    public List<NewsRequestDto> parseNews() {

        List<NewsRequestDto> newsList = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            Document document = Jsoup.connect(newsUrl).get();
            Elements newsElements = document.select("body > div.main_content > div > div.container_sub_news_list > div.container_sub_news_list_wrapper.mode1 > div");

            for (Element newsElement : newsElements) {
                String headLine = newsElement.select("div.article_header > a").text();
                String publicationTime = newsElement.select("div.article_time").text();
                String description = newsElement.select("div.article_content").text();

                if (publicationTime.isEmpty()) {
                    continue;
                }

                try {
                    LocalTime time = LocalTime.parse(publicationTime, timeFormatter);
                    LocalDateTime parsedPublicationTime = LocalDateTime.of(LocalDate.now(), time);

                    NewsRequestDto requestDto = new NewsRequestDto();
                    requestDto.setHeadLine(headLine);
                    requestDto.setDescription(description);
                    requestDto.setPublicationTime(parsedPublicationTime);

                    newsList.add(requestDto);

                } catch (DateTimeParseException e) {
                    System.out.println("Помилка під час парсингу часу для новини: " + headLine);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return newsList;
    }
}
