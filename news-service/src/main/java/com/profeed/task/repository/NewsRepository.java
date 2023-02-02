package com.profeed.task.repository;

import com.profeed.task.model.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {


}
