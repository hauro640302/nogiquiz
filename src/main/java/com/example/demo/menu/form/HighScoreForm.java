package com.example.demo.menu.form;

import java.time.format.DateTimeFormatter;
import com.example.demo.menu.domain.model.HighScoreDto;

public record HighScoreForm(
    String score,
    String name,
    String playedAt) {

  public static HighScoreForm fromDto(HighScoreDto dto) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    return new HighScoreForm(
        String.valueOf(dto.score() + " 点"),
        dto.name(),
        dto.playedAt().format(formatter));
  }
}
