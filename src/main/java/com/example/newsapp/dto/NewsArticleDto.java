package com.example.newsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object representing a news article item from NewsAPI.
 *
 * @param source      Source info record
 * @param author      Article author name
 * @param title       Article headline
 * @param description Article description excerpt
 * @param url         Link to full article
 * @param imageUrl    Thumbnail image URL
 * @param publishedAt Publication timestamp
 * @param content     Full article content snippet
 */
public record NewsArticleDto(
    NewsSourceDto source,
    String author,
    String title,
    String description,
    String url,
    @JsonProperty("urlToImage") String imageUrl,
    @JsonProperty("publishedAt") String publishedAt,
    String content
) {

  /**
   * Helper accessor returning news source publisher name.
   *
   * @return News source name or default fallback
   */
  public String getNewsSite() {
    return source != null && source.name() != null ? source.name() : "News";
  }

  /**
   * Helper accessor returning short summary or content.
   *
   * @return Summary text
   */
  public String getSummary() {
    return description != null ? description : (content != null ? content : "");
  }
}
