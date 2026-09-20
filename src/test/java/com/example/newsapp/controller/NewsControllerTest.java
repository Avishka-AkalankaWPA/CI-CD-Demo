package com.example.newsapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.newsapp.dto.NewsArticleDto;
import com.example.newsapp.service.NewsService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NewsController.class)
class NewsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private NewsService newsService;

  @Test
  @DisplayName("Should render index view with articles model attributes")
  void getHomePage_Success() throws Exception {
    NewsArticleDto mockArticle = new NewsArticleDto(
        101,
        "SpaceX Starship Launch Test",
        "https://example.com/spacex",
        "https://example.com/starship.jpg",
        "Starship test flight succeeds.",
        "2026-09-20T10:00:00Z",
        "SpaceX News"
    );

    when(newsService.getLatestNews()).thenReturn(List.of(mockArticle));

    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("articles"))
        .andExpect(model().attribute("articleCount", 1))
        .andExpect(model().attributeExists("environment"))
        .andExpect(model().attributeExists("buildVersion"));
  }
}
