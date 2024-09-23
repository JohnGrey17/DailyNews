package com.example.repository;

import com.example.model.News;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("SELECT n FROM News n WHERE n.publicationTime BETWEEN :start AND :end AND n.isDeleted = false ORDER BY n.publicationTime DESC")
    Page<News> findAllByPublicationTimeBetween(
            LocalDateTime start, LocalDateTime end, Pageable pageable);

    Optional<News> findByHeadLineAndDescriptionAndPublicationTime(
            String headLine, String description, LocalDateTime publicationTime);

    @Query("SELECT n FROM News n WHERE n.publicationTime BETWEEN :start AND :end ORDER BY n.publicationTime DESC")
    List<News> findByPublicationTimeBetween(
            LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.publicationTime BETWEEN :startOfDay AND :endOfDay AND n.isDeleted = false ORDER BY n.publicationTime DESC")
    List<News> findAllByPublicationTimeToday(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            Pageable pageable);

}
