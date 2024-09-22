package com.example.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NewsUpdateDto {
    private String headLine;

    private String description;

    private LocalDateTime updatedTime;
}
