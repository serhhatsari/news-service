package com.serhat.newsservice.repository;

import com.serhat.newsservice.model.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    Optional<List<NewsEntity>> findByCategory(String category);

    Optional<List<NewsEntity>> findByCountry(String country);

    Optional<List<NewsEntity>> findByLanguage(String language);

    Optional<List<NewsEntity>> findTop10ByOrderByPublished_atDesc();
}
