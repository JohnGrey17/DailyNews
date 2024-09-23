package com.example.dto;

import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NewsResponseDto {

    private String headLine;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime publicationTime;
}
