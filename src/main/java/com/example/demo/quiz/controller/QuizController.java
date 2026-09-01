package com.example.demo.quiz.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import com.example.demo.common.domain.model.SessionData;
import com.example.demo.menu.domain.model.HighScoreDto;
import com.example.demo.quiz.domain.model.QuestionDto;
import com.example.demo.quiz.domain.service.QuestionService;
import com.example.demo.quiz.domain.service.ScoreService;
import com.example.demo.quiz.form.QuizForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@SessionAttributes("quizForm")
@Slf4j
public class QuizController {

  private final QuestionService service;
  private final SessionData sessionData;
  private final ScoreService scoreService;

  @ModelAttribute("quizForm")
  public QuizForm setDefaultContext() {
    return QuizForm.getDefault();
  }

  @GetMapping("/quiz")
  public String getQuiz(@ModelAttribute QuizForm quizForm, Model model) {

    if (quizForm.getQuestions() == null) {
      // 設問の場合
      List<QuestionDto> qs = service.makeQuiz(
          sessionData.getCategory(), sessionData.isActive());
      quizForm = QuizForm.forQuestion(qs);
      model.addAttribute("quizForm", quizForm);
    }

    return "quiz/quiz";
  }

  @PostMapping("/quiz/answer")
  public String postQuizAnswer(@ModelAttribute QuizForm quizForm, Model model,
      @AuthenticationPrincipal UserDetails user) {
    
    // 全問回答していない場合、最後に回答した以降の回答がないので詰める
    int questionSize = quizForm.getQuestions().size();
    int answerSize = quizForm.getAnswers().size();
    if (questionSize > answerSize) {
      for (int i = 0; i < (questionSize - answerSize); i++) {
        quizForm.getAnswers().add("");
      }
    }
    
    // 採点
    int score = service.scoring(
        quizForm.getQuestions().stream().map(QuestionDto::correct).toList(),
        quizForm.getAnswers());
    quizForm.setScore(score);
    quizForm.setHasAnswer(true);
    
    // ハイスコアであればDBに書く
    HighScoreDto hs = new HighScoreDto(score, user.getUsername(), LocalDateTime.now());
    scoreService.updateScore(sessionData.getCategory(), sessionData.isActive(), hs);
    
    return "redirect:/quiz";
  }

  @PostMapping("/quiz/retry")
  public String postQuizRetry(SessionStatus sessionStatus) {
    sessionStatus.setComplete();

    return "redirect:/quiz";
  }

  @PostMapping("/quiz/exit")
  public String postQuizExit(SessionStatus sessionStatus) {
    sessionStatus.setComplete();
    
    return "redirect:/menu";
  }
}
