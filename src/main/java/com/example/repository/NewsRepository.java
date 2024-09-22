package com.example.repository;

import com.example.dto.NewsResponseDto;
import com.example.model.News;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {


    Page<News> findAllByPublicationTimeBetween(
            LocalDateTime beginOfToday, LocalDateTime endOfToday, Pageable pageable);

    Optional<News> findByHeadLineAndDescriptionAndPublicationTime(
            String headLine, String description, LocalDateTime publicationTime);

    List<News> findByPublicationTimeBetween(
            LocalDateTime start, LocalDateTime end, Pageable pageable);

    void deleteByPublicationTimeBefore(LocalDateTime dateTime);
}
