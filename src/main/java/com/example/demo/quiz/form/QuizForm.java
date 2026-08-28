package com.example.demo.quiz.form;

import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.quiz.domain.model.QuestionDto;
import lombok.Data;

@Data
public class QuizForm {
  List<QuestionDto> questions;
  List<String> answers;
  boolean hasAnswer;
  int score;

  public static QuizForm forQuestion(List<QuestionDto> dto) {
    QuizForm form = new QuizForm();
    form.setQuestions(dto);
    form.setAnswers(dto.stream().map(d -> "").collect(Collectors.toList()));
    form.setHasAnswer(false);
    form.setScore(0);
    return form;
  }

  public static QuizForm getDefault() {
    QuizForm form = new QuizForm();
    form.setQuestions(null);
    form.setAnswers(null);
    form.setHasAnswer(false);
    form.setScore(0);
    return form;
  }

}
