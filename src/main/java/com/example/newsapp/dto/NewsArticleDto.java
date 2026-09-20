package com.example.newsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object representing a news article item.
 *
 * @param id          Article identifier
 * @param title       Article headline
 * @param url         Link to full article
 * @param imageUrl    Thumbnail image URL
 * @param summary     Short article summary
 * @param publishedAt Publication date string
 * @param newsSite    Source news publisher name
 */
public record NewsArticleDto(
    int id,
    String title,
    String url,
    @JsonProperty("image_url") String imageUrl,
    String summary,
    @JsonProperty("published_at") String publishedAt,
    @JsonProperty("news_site") String newsSite
) {}
