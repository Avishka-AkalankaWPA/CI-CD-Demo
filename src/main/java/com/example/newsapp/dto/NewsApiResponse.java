package com.example.newsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Data Transfer Object representing the NewsAPI response wrapper.
 *
 * @param status       Response status ("ok" or "error")
 * @param totalResults Total count of matching articles
 * @param results      List of news articles
 */
public record NewsApiResponse(
    String status,
    int totalResults,
    @JsonProperty("articles") List<NewsArticleDto> results
) {}
