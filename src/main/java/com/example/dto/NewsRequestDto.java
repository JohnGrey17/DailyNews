package com.example.dto;

import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NewsRequestDto {

    private String headLine;

    private String description;

    private LocalDateTime publicationTime;
}
