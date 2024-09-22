package com.example.service;

import com.example.dto.NewsRequestDto;
import com.example.dto.NewsResponseDto;
import com.example.dto.NewsUpdateDto;
import com.example.exception.NewsException;
import com.example.mapper.NewsMapper;
import com.example.model.News;
import com.example.repository.NewsRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public NewsResponseDto addNews(NewsRequestDto requestDto) {
        newsDuplicateChecker(requestDto);
        News news = newsMapper.toModel(requestDto);
        News saved = newsRepository.save(news);
        return newsMapper.toDto(saved);
    }


    public List<NewsResponseDto> getNewsByTimeRange(
            LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return newsRepository.findByPublicationTimeBetween(start, end).stream()
                .map(newsMapper::toDto)
                .toList();
    }

    public NewsResponseDto updateNews(Long id, NewsUpdateDto updateDto) {
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new NewsException("News with id " + id + "does not exist"));
        News savedNews = newsRepository.save(existingNews);
        return newsMapper.toDto(savedNews);
    }

    @Override
    public List<NewsResponseDto> getAllOnToday(Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfToday = now.with(LocalTime.MAX);
        LocalDateTime beginOfToday = now.with(LocalTime.MIN);

        return newsRepository.findAll().stream()
                .filter(e -> e.getPublicationTime().isBefore(endOfToday)
                        && e.getPublicationTime().isAfter(beginOfToday))
                .map(newsMapper::toDto)
                .toList();

    }


    public void deleteNews(Long id) {
        newsRepository.findById(id).orElseThrow(()
                -> new NewsException("News with id " + id + "does not exist"));
        newsRepository.deleteById(id);
    }

    public void deleteOldNews() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        newsRepository.deleteByPublicationTimeBefore(yesterday);
    }

    private void newsDuplicateChecker(NewsRequestDto requestDto) {
        Optional<News> existingNews = newsRepository.findByHeadLineAndDescriptionAndPublicationTime(
                requestDto.getHeadLine(), requestDto.getDescription(), requestDto.getPublicationTime());

        if (existingNews.isPresent()) {
            throw new NewsException("That news already exists");
        }
    }
}
