package com.example.controller;

import com.example.dto.NewsRequestDto;
import com.example.dto.NewsResponseDto;
import com.example.dto.NewsUpdateDto;
import com.example.service.news.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public void createNews(@RequestBody @Valid NewsRequestDto requestDto) {
        newsService.addNews(requestDto);
    }

    @GetMapping("/today")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all news", description = "get all news on today")
    public List<NewsResponseDto> getAllNews(Pageable pageable) {
        return newsService.getAllOnToday(pageable);
    }

    @GetMapping("/range")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get news in time range", description = "Get news in specified range")
    List<NewsResponseDto> getAllInRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH") LocalDateTime end,
            Pageable pageable) {
        return newsService.getNewsByRange(start, end, pageable);
        //TODO ADMIN`s Controller
    }

    @GetMapping("/range/time")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get news in hour range",
            description = "Get news in specified hour range")
    public List<NewsResponseDto> getAllInHourRange(
            @RequestParam int startHour,
            @RequestParam int endHour,
            Pageable pageable) {
        return newsService.getNewsByHoursRange(startHour, endHour, pageable);
    }
    //todo для юзера і адміна

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update news", description = "Found news by id and update")
    public NewsUpdateDto updateNews(@PathVariable("id") Long id,
                                    @RequestBody NewsUpdateDto updateDto) {
        return newsService.updateNews(id, updateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete news", description = "Delete news by id")
    void deleteNewsById(@PathVariable("id") Long id) {
        newsService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete all old news",
            description = "Delete (mark as delete) all news from yesterday")
    public void deleteYesterdayNews() {
        newsService.deleteOldNews();
    }
}
