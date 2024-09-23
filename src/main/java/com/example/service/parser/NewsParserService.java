package com.example.service.parser;

import com.example.dto.NewsRequestDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface NewsParserService {

    List<NewsRequestDto> parseNews();
}
