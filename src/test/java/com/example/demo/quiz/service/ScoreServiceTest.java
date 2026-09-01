package com.example.demo.quiz.service;

import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.menu.domain.model.HighScoreDto;
import com.example.demo.quiz.domain.model.ScoreDto;
import com.example.demo.quiz.domain.service.ScoreService;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class ScoreServiceTest {

  @Autowired
  private ScoreService service;
  
  @Test
  public void test1() {  
    HighScoreDto hs = new HighScoreDto(70, "superuser", LocalDateTime.now());
    service.updateScore("出身都道府県", true, hs);
    List<ScoreDto> result = service.getScore("出身都道府県", true);
    assertThat(result.get(8).playerId()).isEqualTo(1);
  }
  
  @Test
  public void test2() {  
    HighScoreDto hs = new HighScoreDto(70, "superuser", LocalDateTime.now());
    service.updateScore("出身都道府県", false, hs);
    List<ScoreDto> result = service.getScore("出身都道府県", false);
    log.info("TTTT result " + result.toString());
    assertThat(result.get(0).playerId()).isEqualTo(1);
  }
  
  @Test
  public void test3() {  
    HighScoreDto hs = new HighScoreDto(5, "superuser", LocalDateTime.now());
    service.updateScore("出身都道府県", true, hs);
    List<ScoreDto> result = service.getScore("出身都道府県", true);
    assertThat(result.stream().filter(s -> s.score() == 5).count()).isEqualTo(0);
  }
  
}
