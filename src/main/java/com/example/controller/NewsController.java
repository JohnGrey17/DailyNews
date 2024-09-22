package com.example.controller;


import com.example.dto.NewsRequestDto;
import com.example.dto.NewsResponseDto;
import com.example.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/news")
@Tag(name = "News Controller", description = "This controller handles requests "
        + "and responses related to news in the database")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create news", description = "Create news with parameters in DB")
    public NewsResponseDto createNews(@RequestBody @Valid NewsRequestDto requestDto) {
        return newsService.addNews(requestDto);
        //todo for Admin
    }
/*
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get news on time period" , description = "Get all news on time period")
    List<NewsResponseDto> getAllOnPeriod(
            LocalDateTime startOfPeriod, LocalDateTime endOfPeriod, Pageable pageable) {
        return newsService.getNewsByTimeRange(startOfPeriod, endOfPeriod, pageable);
    }
*/
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all news" , description = "get all news on today")
    public List<NewsResponseDto> getAllNews(Pageable pageable) {

        return newsService.getAllOnToday(pageable);
        //TODO foe admin And user
    }

}
