package com.example.newsapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.newsapp.dto.NewsApiResponse;
import com.example.newsapp.dto.NewsArticleDto;
import com.example.newsapp.dto.NewsSourceDto;
import com.example.newsapp.service.impl.SpaceflightNewsServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class SpaceflightNewsServiceTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private RestClient restClient;

  private SpaceflightNewsServiceImpl newsService;

  private final String testUrl = "https://api.spaceflightnewsapi.net/v4/articles/";

  @BeforeEach
  void setUp() {
    newsService = new SpaceflightNewsServiceImpl(restClient, testUrl);
  }

  @Test
  @DisplayName("Should return news articles when REST API call succeeds")
  void getLatestNews_Success() {
    NewsArticleDto article = new NewsArticleDto(
        new NewsSourceDto("nasa", "NASA Spaceflight"),
        "Author Name",
        "NASA Artemis Mission Update",
        "Summary of NASA Artemis mission",
        "https://example.com/article1",
        "https://example.com/image1.jpg",
        "2026-09-20T12:00:00Z",
        "Content snippet"
    );
    NewsApiResponse mockResponse = new NewsApiResponse("ok", 1, List.of(article));

    when(restClient.get().uri(anyString()).retrieve().body(NewsApiResponse.class))
        .thenReturn(mockResponse);

    List<NewsArticleDto> result = newsService.getLatestNews();

    assertThat(result).hasSize(1);
    assertThat(result.get(0).title()).isEqualTo("NASA Artemis Mission Update");
    assertThat(result.get(0).getNewsSite()).isEqualTo("NASA Spaceflight");
  }

  @Test
  @DisplayName("Should return empty list gracefully when API call throws exception")
  void getLatestNews_FallbackOnException() {
    when(restClient.get().uri(anyString()).retrieve().body(NewsApiResponse.class))
        .thenThrow(new RuntimeException("API Connection Refused"));

    List<NewsArticleDto> result = newsService.getLatestNews();

    assertThat(result).isNotEmpty();
    assertThat(result.get(0).title()).contains("Bitcoin");
  }
}
