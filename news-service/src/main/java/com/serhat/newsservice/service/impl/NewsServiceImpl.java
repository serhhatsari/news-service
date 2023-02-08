package com.serhat.newsservice.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.serhat.newsservice.constants.UrlConstants;
import com.serhat.newsservice.model.converter.NewsConverter;
import com.serhat.newsservice.model.dto.NewsDto;
import com.serhat.newsservice.model.entity.NewsEntity;
import com.serhat.newsservice.repository.NewsRepository;
import com.serhat.newsservice.service.NewsService;
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
import java.util.stream.Collectors;

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
        HttpRequest request = buildRequest();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            JsonArray data = jsonObject.getAsJsonArray("data");
            saveNews(data);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest buildRequest() {
        String fullUrl = UrlConstants.NEWS_API_URL+"?access_key="+NEWS_API_KEY +"&limit=100";

        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(fullUrl))
                .build();
    }

    private void saveNews(JsonArray news) {
        Gson gson = new Gson();
        for(JsonElement element : news) {
            NewsEntity newsEntity = gson.fromJson(element, NewsEntity.class);
            try {
                newsRepository.save(newsEntity);
            } catch (Exception e) {
                System.out.println("News already exists");
            }
        }
    }

    @Override
    public List<NewsDto> filterNews(int page, int size, String source, Date publishedDateStart, Date publishedDateEnd, String titleContains, String country, String language) {
        List<NewsDto> filteredNews = getFilteredNews(source, publishedDateStart, publishedDateEnd, titleContains, country, language);
        return getPage(filteredNews, page, size);
    }

    @Override
    public List<NewsDto> getNewsByCategory(int page, int size, String category) {
        return null;
    }

    @Override
    public List<NewsDto> getNewsByCountry(int page, int size, String country) {
        return null;
    }

    @Override
    public List<NewsDto> getNewsByLanguage(int page, int size, String language) {
        return null;
    }

    @Override
    public List<NewsDto> getLatestNews() {
        return null;
    }

    private List<NewsDto> getFilteredNews(String source, Date publishedDateStart, Date publishedDateEnd, String titleContains, String country, String language) {
        List<NewsEntity> allNews = newsRepository.findAll();

        return allNews.stream()
                .filter(n -> (source == null || n.getSource().equals(source)) &&
                        (publishedDateStart == null || !n.getPublished_at().before(publishedDateStart)) &&
                        (publishedDateEnd == null || !n.getPublished_at().after(publishedDateEnd)) &&
                        (titleContains == null || n.getTitle().contains(titleContains)) &&
                        (country == null || n.getCountry().equals(country)) &&
                        (language == null || n.getLanguage().equals(language)))
                .map(NewsConverter::convertToDto)
                .collect(Collectors.toList());
    }

    private List<NewsDto> getPage(List<NewsDto> filteredNews, int page, int size) {
        int start = page * size;
        int end = start + size;

        if (start > filteredNews.size() || page < 0 || size < 0) {
            return new ArrayList<>();
        }

        if (end > filteredNews.size()) {
            end = filteredNews.size();
        }

        return filteredNews.subList(start, end);
    }

}
