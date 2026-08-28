package com.example.demo.common.repository;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.common.domain.model.MemberDetailDto;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class MemberDetailRepositoryTest {

  @Autowired
  private MemberDetailRepository repository;
  
  @Test
  public void test1() {
    List<MemberDetailDto> result = repository.findAll();
    log.info("TTTT result[0]: " + result.get(0).toString());
    log.info("TTTT result[99]: " + result.get(99).toString());
    assertThat(result.size()).isEqualTo(100);
  }
  
  @Test
  public void test2() {
    List<MemberDetailDto> result = repository.findAllByPage(0, 10);
    log.info("TTTT result[0]: " + result.get(0).toString());
    log.info("TTTT result[9]: " + result.get(9).toString());
    assertThat(result.size()).isEqualTo(10);
  }
  
  @Test
  public void test3() {
    List<MemberDetailDto> result = repository.findAllByPage(9, 10);
    log.info("TTTT result[0]: " + result.get(0).toString());
    log.info("TTTT result[9]: " + result.get(9).toString());
    assertThat(result.size()).isEqualTo(10);
  }
  
  @Test
  public void test4() {
    int result = repository.count();
    assertThat(result).isEqualTo(100);
  }
  
  @Test
  public void test5() {
    List<MemberDetailDto> result = repository.findAllByIsActiveTrue();
    log.info("TTTT result[0]: " + result.get(0).toString());
    log.info("TTTT result[99]: " + result.get(10).toString());
    assertThat(result.size()).isEqualTo(33);
  }
}
