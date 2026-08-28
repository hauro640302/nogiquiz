package com.example.demo.login.repository;

import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.login.domain.model.PlayerDto;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class PlayerRepositoryTest {
  
  @Autowired
  private PlayerRepository repository;
  
  @Test
  public void test1() {
    PlayerDto result = repository.findByPlayerName("test");
    log.info("TTTT result: " + result.toString());
    assertThat(result).isNotNull();
  }
  
  @Test
  public void test2() {
    PlayerDto result = repository.findByPlayerName("superuser");
    log.info("TTTT result: " + result.toString());
    assertThat(result).isNotNull();
  }

  @Test
  public void test3() {
    PlayerDto player = new PlayerDto(null, "test100", "test100", null, "GENERAL");
    boolean result = repository.addPlayer(player);
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(true);
    
    PlayerDto result2 = repository.findByPlayerName("test100");
    log.info("TTTT result2: " + result2.toString());
    assertThat(result2).isNotNull();
  }

  @Test
  public void test4() {
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    boolean result = repository.updatePlayerLoggedinAt("test", now);
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(true);  
    
    PlayerDto result2 = repository.findByPlayerName("test");
    log.info("TTTT result2: " + result2.toString());
    log.info("TTTT now: " + now.toString());
    assertThat(result2.loggedinAt().isEqual(now)).isEqualTo(true);
  }
}
