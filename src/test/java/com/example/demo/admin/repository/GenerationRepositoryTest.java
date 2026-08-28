package com.example.demo.admin.repository;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.admin.domain.model.GenerationDto;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class GenerationRepositoryTest {
  
  @Autowired
  private GenerationRepository repository;
  
  @Test
  public void test1() {
    List<GenerationDto> result = repository.findAll();
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(7);
  }

}
