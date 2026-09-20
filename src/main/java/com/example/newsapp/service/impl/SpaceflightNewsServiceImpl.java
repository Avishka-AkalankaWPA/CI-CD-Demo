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

      if (response != null && response.results() != null && !response.results().isEmpty()) {
        log.info("Successfully fetched {} news articles", response.results().size());
        return response.results();
      }
    } catch (Exception e) {
      log.error("Failed to fetch news from API endpoint: {}. Reason: {}", newsApiUrl, e.getMessage(), e);
    }

    log.warn("Returning curated fallback news items due to API fetch issue");
    return getFallbackNews();
  }

  private List<NewsArticleDto> getFallbackNews() {
    return List.of(
        new NewsArticleDto(
            1,
            "NASA Artemis Mission Prepares for Next Moon Flight",
            "https://www.nasa.gov",
            "https://images.unsplash.com/photo-1517976487492-5750f3195933?w=600&auto=format&fit=crop&q=80",
            "Engineers complete final preparations for the upcoming lunar orbital mission, marking a new milestone in space exploration.",
            "2026-09-20T12:00:00Z",
            "NASA"
        ),
        new NewsArticleDto(
            2,
            "James Webb Space Telescope Observes Distant Exoplanet Atmosphere",
            "https://www.esa.int",
            "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=600&auto=format&fit=crop&q=80",
            "New spectroscopic data reveals water vapor and complex chemical signatures in the atmosphere of a super-Earth planet.",
            "2026-09-20T10:30:00Z",
            "ESA"
        ),
        new NewsArticleDto(
            3,
            "Commercial Space Station Modules Enter Final Testing Phase",
            "https://spaceflightnow.com",
            "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?w=600&auto=format&fit=crop&q=80",
            "Next-generation orbital habitats undergo vacuum chamber testing prior to scheduled commercial deployment.",
            "2026-09-20T08:15:00Z",
            "Spaceflight Now"
        )
    );
  }
}
