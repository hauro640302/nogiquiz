package com.example.demo.quiz.domain.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.menu.domain.model.HighScoreDto;
import com.example.demo.quiz.domain.model.ScoreDto;
import com.example.demo.quiz.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreService {

  private final ScoreRepository repository;

  // カテゴリ + 現役生/卒業生 当たりのスコア保持件数
  private final int N = 30;

  public void updateScore(String categoryName, boolean isActive, HighScoreDto hs) {
    
    if (hs.score() <= 0) {
    // 0点は記載しない
      return;
    }
    
    List<ScoreDto> scores = repository.getScore(categoryName, isActive);
    
    if (scores.size() < N) {
      // スコア保持件数を超えていなので、追加する
      repository.addScore(hs, categoryName, isActive);
    }
    else {
    // スコア保持件数を超えた場合
      if (scores.get(N - 1).score() > hs.score()) {
      // 自分以下のスコアはなかった
        return;
      }
      // 最下位のスコアと入れ替える
      repository.updateScore(hs, categoryName, isActive, scores.get(N - 1).id());
    }
  }
  
  public List<ScoreDto> getScore(String categoryName, boolean isActive) {
    return repository.getScore(categoryName, isActive);    
  }
}
