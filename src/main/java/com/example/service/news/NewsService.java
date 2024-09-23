package com.example.service.news;

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

    void addNews(NewsRequestDto requestDto);

    List<NewsResponseDto> getNewsByRange(
            LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<NewsResponseDto> getNewsByHoursRange(int startHour, int endHour, Pageable pageable);

    NewsResponseDto updateNews(Long id, NewsUpdateDto updatedNews);

    void deleteNews(Long id);

    void deleteOldNews();
}
