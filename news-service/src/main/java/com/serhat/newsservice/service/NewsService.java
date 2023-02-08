package com.serhat.newsservice.service;


import com.serhat.newsservice.model.dto.NewsDto;

import java.util.Date;
import java.util.List;

public interface NewsService {

    public void fetchNews();
    public List<NewsDto> filterNews(int page, int size, String source, Date publishedDateStart, Date publishedDateEnd, String titleContains, String country, String language);
    public List<NewsDto> getNewsByCategory(int page, int size, String category);

    public List<NewsDto> getNewsByCountry(int page, int size, String country);

    public List<NewsDto> getNewsByLanguage(int page, int size, String language);

    public List<NewsDto> getLatestNews();
    public List<NewsDto> getNews(int page, int size);
}
