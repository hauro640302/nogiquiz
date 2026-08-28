package com.example.demo.quiz.domain.model;

import java.time.LocalDateTime;

public record ScoreDto(
    long id,
    boolean isActive,
    long categoryId,
    int score,
    long playerId,
    LocalDateTime playedAt) {
}
