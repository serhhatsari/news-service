package com.serhat.newsservice.model.converter;

import com.profeed.task.model.dto.NewsDto;
import com.profeed.task.model.entity.NewsEntity;

public class NewsConverter {


    public static NewsDto convertToDto(NewsEntity entity) {
        return NewsDto.builder()
                .author(entity.getAuthor())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .url(entity.getUrl())
                .source(entity.getSource())
                .image(entity.getImage())
                .category(entity.getCategory())
                .country(entity.getCountry())
                .language(entity.getLanguage())
                .published_at(entity.getPublished_at())
                .build();
    }

}
