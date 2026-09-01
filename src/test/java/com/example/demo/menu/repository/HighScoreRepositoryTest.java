package com.example.demo.menu.repository;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.menu.domain.model.HighScoreDto;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class HighScoreRepositoryTest {

  @Autowired
  private HighScoreRepository repository;

  @Test
  public void test1() {
    List<HighScoreDto> result = repository.getHighScore("出身都道府県", true);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(29);
  }

  @Test
  public void test2() {
    List<HighScoreDto> result = repository.getHighScoreByPage("出身都道府県", true, 0, 10);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test3() {
    List<HighScoreDto> result = repository.getHighScoreByPage("出身都道府県", true, 3, 10);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(0);
  }
  
  @Test
  public void test4() {
    List<HighScoreDto> result = repository.getHighScoreByPage("出身都道府県", true, 4, 10);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(0);
  }
  
  @Test
  public void test5() {
    int result = repository.count("出身都道府県", true);
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(29);
  }
}
