package com.serhat.newsservice.repository;

import com.serhat.newsservice.model.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {


}
