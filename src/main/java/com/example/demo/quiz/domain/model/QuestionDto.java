package com.example.demo.quiz.domain.model;

import java.util.List;

public record QuestionDto(
    String memberName,
    String correct,
    List<String> choices) {
  
  public static QuestionDto withoutCorrect(QuestionDto dto) {
    return new QuestionDto(dto.memberName, "", dto.choices());
  }
}
