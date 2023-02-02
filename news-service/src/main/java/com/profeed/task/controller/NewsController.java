package com.profeed.task.controller;


import com.profeed.task.model.dto.NewsDto;
import com.profeed.task.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<List<NewsDto>> getNews(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "size", required = true) int size, @RequestParam(value = "source", required = false) String source, @RequestParam(value = "publishedDateStart", required=false) String publishedDateStart, @RequestParam(value = "publishedDateEnd", required=false) String publishedDateEnd, @RequestParam(value="titleContains", required=false) String titleContains, @RequestParam(value="country", required=false) String country, @RequestParam(value = "language", required=false) String language) {
        return new ResponseEntity<>(newsService.getNews(page, size, source, publishedDateStart, publishedDateEnd, titleContains, country, language), HttpStatus.OK);
    }

}
