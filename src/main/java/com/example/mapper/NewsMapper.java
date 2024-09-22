package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.NewsRequestDto;
import com.example.dto.NewsResponseDto;
import com.example.dto.NewsUpdateDto;
import com.example.model.News;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface NewsMapper {

    News toModel(NewsRequestDto newsRequestDto);

    NewsResponseDto toDto(News news);

    News toUpdatedModel(NewsUpdateDto newsUpdateDto);

}
