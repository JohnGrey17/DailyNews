package com.example.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NewsResponseDto {

    private String headLine;

    private String content;

    private LocalDateTime publicationTime;
}
