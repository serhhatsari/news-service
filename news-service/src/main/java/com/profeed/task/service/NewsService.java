package com.profeed.task.service;


import com.profeed.task.model.dto.NewsDto;

import java.util.Date;
import java.util.List;

public interface NewsService {

    public void fetchNews();
    public List<NewsDto> filterNews(int page, int size, String source, Date publishedDateStart, Date publishedDateEnd, String titleContains, String country, String language);

}
