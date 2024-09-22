package com.example.service;

import com.example.dto.NewsRequestDto;
import com.example.dto.NewsResponseDto;
import com.example.dto.NewsUpdateDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface NewsService {

    List<NewsResponseDto> getAllOnToday(Pageable pageable);

    NewsResponseDto addNews(NewsRequestDto requestDto);

    List<NewsResponseDto> getNewsByTimeRange(
            LocalDateTime start, LocalDateTime end, Pageable pageable);

    NewsResponseDto updateNews(Long id, NewsUpdateDto updatedNews);

    void deleteNews(Long id);

    void deleteOldNews();
}
