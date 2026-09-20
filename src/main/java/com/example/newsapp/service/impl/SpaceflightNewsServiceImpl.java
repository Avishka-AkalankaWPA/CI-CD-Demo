package com.example.newsapp.service.impl;

import com.example.newsapp.dto.NewsApiResponse;
import com.example.newsapp.dto.NewsArticleDto;
import com.example.newsapp.service.NewsService;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Implementation of NewsService consuming the Spaceflight News REST API.
 */
@Service
public class SpaceflightNewsServiceImpl implements NewsService {

  private static final Logger log = LoggerFactory.getLogger(SpaceflightNewsServiceImpl.class);

  private final RestClient restClient;
  private final String newsApiUrl;

  /**
   * Constructor injection for service dependencies.
   *
   * @param restClient Configured RestClient instance
   * @param newsApiUrl Configured API endpoint URL
   */
  public SpaceflightNewsServiceImpl(
      RestClient restClient,
      @Value("${news.api.url}") String newsApiUrl) {
    this.restClient = restClient;
    this.newsApiUrl = newsApiUrl;
  }

  @Override
  public List<NewsArticleDto> getLatestNews() {
    log.info("Fetching latest news from endpoint: {}", newsApiUrl);
    try {
      NewsApiResponse response = restClient.get()
          .uri(newsApiUrl)
          .retrieve()
          .body(NewsApiResponse.class);

      if (response != null && response.results() != null) {
        log.info("Successfully fetched {} news articles", response.results().size());
        return response.results();
      }
    } catch (Exception e) {
      log.error("Failed to fetch news from API endpoint: {}. Reason: {}", newsApiUrl, e.getMessage());
    }

    log.warn("Returning fallback empty news list due to API fetch failure");
    return Collections.emptyList();
  }
}
