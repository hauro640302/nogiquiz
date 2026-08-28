package com.example.demo.login.service;

import static org.assertj.core.api.Assertions.*;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.login.domain.service.PlayerService;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class PlayerServiceTest {

  @Autowired
  private PlayerService service;

  @Test
  public void test1() {
    boolean result = false;
    try {  
      service.addPlayer("test1", "test1");
      result = true;
    }
    catch(Exception e) {
      result = false;
    }
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(true);
  }
  
  @Test
  public void test2() {
    boolean result = false;
    try {  
      service.addPlayer("test", "test");
      result = true;
    }
    catch(Exception e) {
      result = false;
    }
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(false);
  }
  
  @Test
  public void test3() {
    Duration result = service.getLoginDuration("test");
    log.info("TTTT result: " + result.toSeconds());
    assertThat(true).isEqualTo(true);
  }
}
