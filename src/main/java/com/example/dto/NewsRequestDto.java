package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NewsRequestDto {

    @NotBlank
    @Size(min = 1, message = "Headline must not be empty")
    private String headLine;

    private String description;

    private LocalDateTime publicationTime;
}
