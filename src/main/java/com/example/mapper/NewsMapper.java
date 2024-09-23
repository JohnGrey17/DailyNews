package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.NewsRequestDto;
import com.example.dto.NewsResponseDto;
import com.example.dto.NewsUpdateDto;
import com.example.model.News;
import java.time.LocalDate;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface NewsMapper {

    News toModel(NewsRequestDto newsRequestDto);

   default NewsResponseDto toDto(News news) {
        NewsResponseDto responseDto = new NewsResponseDto();
        responseDto.setHeadLine(news.getHeadLine());
        responseDto.setPublicationTime(news.getPublicationTime());
        responseDto.setDescription(news.getDescription());
        return responseDto;
    }

     default NewsUpdateDto toUpdatedModel(News news) {

         NewsUpdateDto updatedDto = new NewsUpdateDto();
         updatedDto.setHeadLine(news.getHeadLine());
         updatedDto.setDescription(news.getDescription());
         updatedDto.setUpdatedTime(news.getPublicationTime());
         return updatedDto;
   }
}
