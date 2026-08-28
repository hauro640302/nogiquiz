package com.example.demo.menu.domain.model;

import java.time.LocalDateTime;

public record HighScoreDto(
    int score,
    String name,
    LocalDateTime playedAt) {
}
