package com.example.newsapp.dto;

import java.util.List;

/**
 * Data Transfer Object representing the API response wrapper.
 *
 * @param count    Total count of items
 * @param next     Next page URL if any
 * @param results  List of news articles
 */
public record NewsApiResponse(
    int count,
    String next,
    List<NewsArticleDto> results
) {}
