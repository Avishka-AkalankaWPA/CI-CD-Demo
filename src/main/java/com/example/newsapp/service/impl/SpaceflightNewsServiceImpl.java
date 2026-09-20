package com.example.newsapp.service.impl;

import com.example.newsapp.dto.NewsApiResponse;
import com.example.newsapp.dto.NewsArticleDto;
import com.example.newsapp.dto.NewsSourceDto;
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
            new NewsSourceDto("gizmodo", "Gizmodo"),
            "Kyle Torpey",
            "Why Bitcoin's Price Is Spiking This Week",
            "Bitcoin's latest rally comes as Treasury debt buybacks and expanding U.S. sanctions highlight the risks of a dollar-dominated financial system.",
            "https://gizmodo.com/why-bitcoins-price-is-spiking-this-week-2000803801",
            "https://gizmodo.com/app/uploads/2026/08/why-bitcoin-price-is-spiking-1200x675.jpg",
            "2026-08-27T17:50:23Z",
            "Bitcoin is experiencing an epic rebound..."
        ),
        new NewsArticleDto(
            new NewsSourceDto("slashdot", "Slashdot"),
            "EditorDavid",
            "Bitcoin-based Liquid Network Says $320 Million Withdrawn in Hack",
            "Liquid Network, a Bitcoin-based payments and settlement network, said about $320 million was withdrawn from its federation wallet in a hack.",
            "https://yro.slashdot.org/story/26/09/07/0727220/bitcoin-based-liquid-network-says-320-million-withdrawn-in-hack",
            "https://a.fsdn.com/sd/topics/bitcoin_64.png",
            "2026-09-07T07:30:00Z",
            "The Fine Print: The following comments are owned by whoever posted them..."
        )
    );
  }
}
