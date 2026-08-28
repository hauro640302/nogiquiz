package com.example.demo.admin.domain.service;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.admin.domain.model.BloodtypeDto;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class BloodtypeServiceTest {

  @Autowired
  private BloodtypeService service;
  
  @Test
  public void test1() {
    List<BloodtypeDto> result = service.getBloodtypeList();
    log.info("TTTT result: " + result.toString());
    assertThat(result.size()).isEqualTo(5);
  }
}
