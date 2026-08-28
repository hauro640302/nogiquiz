package com.example.demo.quiz.repository;

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
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class ScoreRepositoryTest {
  
  @Autowired
  private ScoreRepository repository;
  
  @Test
  public void test1() {
    List<ScoreDto> result = repository.getScore("出身都道府県", true);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(11);
  }
  
  @Test
  public void test2() {
    HighScoreDto hs = new HighScoreDto(69, "superuser", LocalDateTime.now());
    boolean result = repository.addScore(hs, "出身都道府県", true);
    assertThat(result).isEqualTo(true);
    
    List<ScoreDto> result2 = repository.getScore("出身都道府県", true);
    log.info("TTTT result2: " + result2);
    assertThat(result2.size()).isEqualTo(12);
  }
  
  @Test
  public void test3() {
    HighScoreDto hs = new HighScoreDto(69, "superuser", LocalDateTime.now());
    boolean result = repository.updateScore(hs, "出身都道府県", true, 1L);
    assertThat(result).isEqualTo(true);
    
    List<ScoreDto> result2 = repository.getScore("出身都道府県", true);
    log.info("TTTT result2: " + result2);
    assertThat(result2.size()).isEqualTo(12);
  }

}
