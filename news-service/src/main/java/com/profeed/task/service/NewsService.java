package com.profeed.task.service;


import com.profeed.task.model.dto.NewsDto;

import java.util.List;

public interface NewsService {

    public void getNews();
    public List<NewsDto> getNews(int page, int size, String source, String publishedDateStart, String publishedDateEnd, String titleContains, String country, String language);

}
