package com.profeed.task.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.profeed.task.constants.UrlConstants;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Value("${news.api.key}")
    private String newsApiKey;

    @Override
    @Scheduled(fixedRate = 10000)
    public void getNews() {

        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(UrlConstants.NEWS_API_URL+"?access_key="+newsApiKey))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            JsonArray data = jsonObject.getAsJsonArray("data");
            for(JsonElement element : data) {
                NewsEntity newsEntity = gson.fromJson(element, NewsEntity.class);
                newsRepository.save(newsEntity);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NewsDto> getNews(int page, int size, String source, String publishedDateStart, String publishedDateEnd, String titleContains, String country, String language) {
        return null;
    }
}
