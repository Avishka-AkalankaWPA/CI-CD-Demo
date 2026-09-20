package com.example.newsapp.controller;

import com.example.newsapp.dto.NewsArticleDto;
import com.example.newsapp.service.NewsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling web requests and binding Thymeleaf views.
 */
@Controller
public class NewsController {

  private final NewsService newsService;
  private final String environment;
  private final String buildVersion;

  /**
   * Constructor injection for NewsController.
   *
   * @param newsService  Service for news retrieval
   * @param environment  Active runtime environment name
   * @param buildVersion Application build version
   */
  public NewsController(
      NewsService newsService,
      @Value("${app.environment:Production}") String environment,
      @Value("${app.build-version:1.0.0-SNAPSHOT}") String buildVersion) {
    this.newsService = newsService;
    this.environment = environment;
    this.buildVersion = buildVersion;
  }

  /**
   * Renders the home page dashboard with news headlines.
   *
   * @param topic Filter topic parameter
   * @param model Spring MVC Model
   * @return View name "index"
   */
  @GetMapping("/")
  public String getHomePage(
      @RequestParam(name = "topic", defaultValue = "bitcoin") String topic,
      Model model) {
    List<NewsArticleDto> articles = newsService.getLatestNews(topic);

    model.addAttribute("articles", articles);
    model.addAttribute("activeTopic", topic);
    model.addAttribute("environment", environment);
    model.addAttribute("buildVersion", buildVersion);
    model.addAttribute("articleCount", articles.size());

    return "index";
  }
}
