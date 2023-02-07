package com.serhat.newsservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "news")
@Getter
@Setter
@ToString
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "author", length = 100)
    private String author;

    @Column(name = "title", length = 100000)
    private String title;

    @Column(name = "description", length = 100000)
    private String description;

    @Column(name = "url", length = 250, unique = true, nullable = false)
    private String url;

    @Column(name ="source", length = 10000)
    private String source;

    @Column(name = "image_url", length = 100000)
    private String image;

    @Column(name = "category", length = 100000)
    private String category;

    @Column(name = "country", length = 10)
    private String country;

    @Column(name = "language", length = 10)
    private String language;

    @Column(name = "published_date")
    private Date published_at;


}
