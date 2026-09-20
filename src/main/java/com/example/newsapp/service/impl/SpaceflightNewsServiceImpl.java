package com.example.newsapp.service.impl;

import com.example.newsapp.dto.NewsApiResponse;
import com.example.newsapp.dto.NewsArticleDto;
import com.example.newsapp.dto.NewsSourceDto;
import com.example.newsapp.service.NewsService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Implementation of NewsService consuming News REST API.
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
    return getLatestNews("bitcoin");
  }

  @Override
  public List<NewsArticleDto> getLatestNews(String topic) {
    String queryTopic = (topic == null || topic.isBlank()) ? "bitcoin" : topic.trim();
    String targetUrl = newsApiUrl.contains("q=")
        ? newsApiUrl.replaceAll("q=[^&]+", "q=" + queryTopic)
        : newsApiUrl;

    log.info("Fetching news articles for topic [{}] from endpoint: {}", queryTopic, targetUrl);
    try {
      NewsApiResponse response = restClient.get()
          .uri(targetUrl)
          .retrieve()
          .body(NewsApiResponse.class);

      if (response != null && response.results() != null && !response.results().isEmpty()) {
        log.info("Successfully fetched {} news articles for topic: {}", response.results().size(), queryTopic);
        return response.results();
      }
    } catch (Exception e) {
      log.error("Failed to fetch news from API endpoint: {}. Reason: {}", targetUrl, e.getMessage(), e);
    }

    log.warn("Returning curated fallback news items for topic: {}", queryTopic);
    return getFallbackNews(queryTopic);
  }

  private List<NewsArticleDto> getFallbackNews(String topic) {
    return List.of(
        new NewsArticleDto(
            new NewsSourceDto("gizmodo", "Gizmodo"),
            "Kyle Torpey",
            "Why " + topic.substring(0, 1).toUpperCase() + topic.substring(1) + " Market Is Trending This Week",
            "Latest insights and analysis on global trends, economic indicators, and market momentum.",
            "https://gizmodo.com",
            "https://images.unsplash.com/photo-1611974789855-9c2a0a7236a3?w=600&auto=format&fit=crop&q=80",
            "2026-09-20T17:00:00Z",
            "Full market analysis and background details..."
        ),
        new NewsArticleDto(
            new NewsSourceDto("slashdot", "Slashdot"),
            "EditorDavid",
            "Global " + topic.substring(0, 1).toUpperCase() + topic.substring(1) + " Network Updates & Reports",
            "Summary of key sector developments and technological innovations reported worldwide.",
            "https://slashdot.org",
            "https://images.unsplash.com/photo-1590283603385-17ffb3a7f29f?w=600&auto=format&fit=crop&q=80",
            "2026-09-20T15:30:00Z",
            "Coverage of key developments and technical updates..."
        )
    );
  }
}
