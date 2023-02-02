package com.profeed.task.controller;


import com.profeed.task.model.dto.NewsDto;
import com.profeed.task.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://news-service-4bszhyapna-ey.a.run.app", maxAge = 3600)
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<List<NewsDto>> getNews(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "size", required = true) int size, @RequestParam(value = "source", required = false) String source, @RequestParam(value = "publishedDateStart", required=false)  @DateTimeFormat(pattern="yyyy-MM-dd") Date publishedDateStart, @RequestParam(value = "publishedDateEnd", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date publishedDateEnd, @RequestParam(value="titleContains", required=false) String titleContains, @RequestParam(value="country", required=false) String country, @RequestParam(value = "language", required=false) String language) {
        return new ResponseEntity<>(newsService.filterNews(page, size, source, publishedDateStart, publishedDateEnd, titleContains, country, language), HttpStatus.OK);
    }

}
