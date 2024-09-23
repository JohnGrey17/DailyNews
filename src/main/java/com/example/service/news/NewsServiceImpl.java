package com.example.service.news;

import com.example.dto.NewsRequestDto;
import com.example.dto.NewsResponseDto;
import com.example.dto.NewsUpdateDto;
import com.example.exception.NewsException;
import com.example.mapper.NewsMapper;
import com.example.model.News;
import com.example.repository.NewsRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
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
    public void addNews(NewsRequestDto requestDto) {
        if (nullChecker(requestDto) && !newsDuplicateChecker(requestDto)) {
            News news = newsMapper.toModel(requestDto);
            newsRepository.save(news);
        }
    }

    @Override
    public List<NewsResponseDto> getNewsByRange(
            LocalDateTime start, LocalDateTime end, Pageable pageable) {

        return newsRepository.findAllByPublicationTimeBetween(start, end, pageable)
                .stream()
                .map(newsMapper::toDto)
                .toList();
    }

    @Override
    public List<NewsResponseDto> getNewsByHoursRange(
            int startHour, int endHour, Pageable pageable) {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime start = currentDate.atTime(startHour, 0);
        LocalDateTime end = currentDate.atTime(endHour, 0);

        List<News> newsList = newsRepository.findByPublicationTimeBetween(start, end, pageable);

        return newsList.stream()
                .filter(e -> !e.isDeleted())
                .map(newsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsUpdateDto updateNews(Long id, NewsUpdateDto updateDto) {
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new NewsException("News with id " + id + "does not exist"));
        existingNews.setHeadLine(updateDto.getHeadLine());
        existingNews.setDescription(updateDto.getDescription());
        existingNews.setPublicationTime(LocalDateTime.now());

        News saved = newsRepository.save(existingNews);

        return newsMapper.toUpdatedModel(saved);
    }

    @Override
    public List<NewsResponseDto> getAllOnToday(Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beginOfToday = now.with(LocalTime.MIN);
        LocalDateTime endOfToday = now.with(LocalTime.MAX);

        List<News> newsList = newsRepository
                .findAllByPublicationTimeToday(beginOfToday, endOfToday, pageable);

        return newsList.stream()
                .map(newsMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.findById(id).orElseThrow(()
                -> new NewsException("News with id " + id + "does not exist"));
        newsRepository.deleteById(id);
    }

    @Override
    public void deleteOldNews() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beginOfToday = now.with(LocalTime.MIN);

        List<News> all = newsRepository.findAll();
        all.stream()
                .filter(e -> e.getPublicationTime().isBefore(beginOfToday))
                .forEach(e -> e.setDeleted(true));
        all.forEach(e -> newsRepository.save(e));
    }

    private boolean newsDuplicateChecker(NewsRequestDto requestDto) {
        return newsRepository.findByHeadLineAndDescriptionAndPublicationTime(
                requestDto.getHeadLine(), requestDto.getDescription(),
                requestDto.getPublicationTime()).isPresent();
    }

    private boolean nullChecker(NewsRequestDto newsRequestDto) {
        return newsRequestDto.getHeadLine() != null
                && newsRequestDto.getDescription() != null
                && newsRequestDto.getPublicationTime() != null;
    }
}
//todo зробити логіку на ранок , обіт ,вечір
