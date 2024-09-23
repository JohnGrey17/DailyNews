package com.example.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NewsRequestDto {

    private String headLine;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime publicationTime;
}
