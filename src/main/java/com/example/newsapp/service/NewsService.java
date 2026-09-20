package com.example.newsapp.service;

import com.example.newsapp.dto.NewsArticleDto;
import java.util.List;

/**
 * Service interface for fetching news data.
 */
public interface NewsService {

  /**
   * Fetches latest news articles.
   *
   * @return List of news article DTOs
   */
  List<NewsArticleDto> getLatestNews();
}
