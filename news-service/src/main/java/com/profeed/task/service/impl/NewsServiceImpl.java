package com.profeed.task.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.profeed.task.constants.UrlConstants;
import com.profeed.task.model.converter.NewsConverter;
import com.profeed.task.model.dto.NewsDto;
import com.profeed.task.model.entity.NewsEntity;
import com.profeed.task.repository.NewsRepository;
import com.profeed.task.service.NewsService;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.UriBuilder;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Value("${news.api.key}")
    private String NEWS_API_KEY;

    @Override
    @Scheduled(fixedRate = 6000000)
    public void fetchNews() {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        String fullUrl = UrlConstants.NEWS_API_URL+"?access_key="+NEWS_API_KEY +"&limit=100";

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(fullUrl))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            JsonArray data = jsonObject.getAsJsonArray("data");
            for(JsonElement element : data) {
                NewsEntity newsEntity = gson.fromJson(element, NewsEntity.class);
                try {
                    newsRepository.save(newsEntity);
                } catch (Exception e) {
                    System.out.println("News already exists");
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public List<NewsDto> filterNews(int page, int size, String source, Date publishedDateStart, Date publishedDateEnd, String titleContains, String country, String language) {

        List<NewsDto> filteredNews = new ArrayList<>();
        List<NewsDto> paginatedNews = new ArrayList<>();
        List<NewsEntity> allNews = newsRepository.findAll();

        if (page < 0 || size < 0) {
            return paginatedNews;
        }

        for (NewsEntity news : allNews) {
            if (source != null && !news.getSource().equals(source)) {
                continue;
            }
            if (publishedDateStart != null && news.getPublished_at().before(publishedDateStart)) {
                continue;
            }
            if (publishedDateEnd != null && news.getPublished_at().after(publishedDateEnd)) {
                continue;
            }
            if (titleContains != null && !news.getTitle().contains(titleContains)) {
                continue;
            }
            if (country != null && !news.getCountry().equals(country)) {
                continue;
            }
            if (language != null && !news.getLanguage().equals(language)) {
                continue;
            }
            filteredNews.add(NewsConverter.convertToDto(news));
        }

        int start = page * size;
        int end = start + size;

        if (start > filteredNews.size()) {
            return paginatedNews;
        }

        if (end > filteredNews.size()) {
            end = filteredNews.size();
        }

        paginatedNews = filteredNews.subList(start, end);

        return paginatedNews;
    }

}
