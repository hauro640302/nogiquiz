package com.example.demo.menu.domain.service;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.menu.domain.model.HighScoreDto;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class HighScoreServiceTest {
  
  @Autowired
  private HighScoreService service;
  
  @Test
  public void test1() {
    Pageable pageable = PageRequest.of(0, 10);
    Page<HighScoreDto> result = service.getHighScore("出身都道府県", true, pageable);
    log.info("TTTT result: " + result);
    assertThat(result.getContent().size()).isEqualTo(10);
  }
  
  @Test
  public void test2() {
    Pageable pageable = PageRequest.of(1, 10);
    Page<HighScoreDto> result = service.getHighScore("出身都道府県", true, pageable);
    log.info("TTTT result: " + result);
    assertThat(result.getContent().size()).isEqualTo(10);
  }

  @Test
  public void test3() {
    Pageable pageable = PageRequest.of(2, 10);
    Page<HighScoreDto> result = service.getHighScore("出身都道府県", true, pageable);
    log.info("TTTT result: " + result);
    assertThat(result.getContent().size()).isEqualTo(9);
  }
  
  @Test
  public void test4() {
    Pageable pageable = PageRequest.of(3, 10);
    Page<HighScoreDto> result = service.getHighScore("出身都道府県", true, pageable);
    log.info("TTTT result: " + result);
    assertThat(result.getContent().size()).isEqualTo(0);
  }
}
