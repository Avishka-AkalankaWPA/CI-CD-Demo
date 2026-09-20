package com.example.newsapp.dto;

/**
 * Data Transfer Object representing the source of a news article.
 *
 * @param id   Source ID
 * @param name Source publisher name
 */
public record NewsSourceDto(
    String id,
    String name
) {}
