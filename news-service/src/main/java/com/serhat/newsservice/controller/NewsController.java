package com.serhat.newsservice.controller;


import com.serhat.newsservice.model.dto.NewsDto;
import com.serhat.newsservice.service.NewsService;
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
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<List<NewsDto>> getNews(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "size", required = true) int size) {
        return new ResponseEntity<>(newsService.getNews(page, size), HttpStatus.OK);
    }

    @GetMapping("/news/filter")
    public ResponseEntity<List<NewsDto>> getNews(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "size", required = true) int size, @RequestParam(value = "source", required = false) String source, @RequestParam(value = "publishedDateStart", required=false)  @DateTimeFormat(pattern="yyyy-MM-dd") Date publishedDateStart, @RequestParam(value = "publishedDateEnd", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date publishedDateEnd, @RequestParam(value="titleContains", required=false) String titleContains, @RequestParam(value="country", required=false) String country, @RequestParam(value = "language", required=false) String language) {
        return new ResponseEntity<>(newsService.filterNews(page, size, source, publishedDateStart, publishedDateEnd, titleContains, country, language), HttpStatus.OK);
    }

    @GetMapping("/news/category/{category}")
    public ResponseEntity<List<NewsDto>> getNewsByCategory(@PathVariable("category") String category, @RequestParam(value = "page", required = true) int page, @RequestParam(value = "size", required = true) int size) {
        return new ResponseEntity<>(newsService.getNewsByCategory(page, size, category), HttpStatus.OK);
    }

    @GetMapping("/news/country/{country}")
    public ResponseEntity<List<NewsDto>> getNewsByCountry(@PathVariable("country") String country, @RequestParam(value = "page", required = true) int page, @RequestParam(value = "size", required = true) int size) {
        return new ResponseEntity<>(newsService.getNewsByCountry(page, size, country), HttpStatus.OK);
    }

    @GetMapping("/news/lang/{language}")
    public ResponseEntity<List<NewsDto>> getNewsByLanguage(@PathVariable("language") String language, @RequestParam(value = "page", required = true) int page, @RequestParam(value = "size", required = true) int size) {
        return new ResponseEntity<>(newsService.getNewsByLanguage(page, size, language), HttpStatus.OK);
    }

    @GetMapping("/news/latest")
    public ResponseEntity<List<NewsDto>> getLatestNews() {
        return new ResponseEntity<>(newsService.getLatestNews(), HttpStatus.OK);
    }

}
