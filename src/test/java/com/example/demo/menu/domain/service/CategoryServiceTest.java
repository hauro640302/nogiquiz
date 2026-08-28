package com.example.demo.menu.domain.service;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class CategoryServiceTest {

  @Autowired
  private CategoryService service;

  @Test
  public void test1() {
    List<String> result = service.getCategories();
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(5);
  }

  @Test
  public void test2() {
    String result = service.getDefaultCategory();
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo("出身都道府県");
  }

  @Test
  public void test3() {
    assertThat(service.hasCategory("血液型")).isEqualTo(true);
  }

  @Test
  public void test4() {
    assertThat(service.hasCategory("血液")).isEqualTo(false);
  }
}
