package com.example.demo.admin.repository;

import static org.assertj.core.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.admin.domain.model.MemberDto;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class MemberRepositoryTest {

  @Autowired
  private MemberRepository repository;
  
  @Test
  public void test1() {
    Long id = 1L;
    MemberDto result = repository.findById(id);
    log.info("TTTT result: " + result);
    assertThat(result).isNotNull();
  }
  
  @Test
  public void test2() {
    List<MemberDto> result = repository.findAll();
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(100);
  }
  
  @Test
  public void test3() {
    Long one = 1L;
    MemberDto member = new MemberDto(one, "小坂奈緒", LocalDate.now(), one, one, one, true);
    boolean result = repository.update(member);
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(true);
    
    Long id = 1L;
    MemberDto result2 = repository.findById(id);
    log.info("TTTT result2: " + result2.toString());
    assertThat(result2).isNotNull();
  }
  
  @Test
  public void test4() {
    Long one = 1L;
    MemberDto member = new MemberDto(one, "正源司洋子", LocalDate.now(), one, one, one, true);
    boolean result = repository.add(member);
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(true);
    
    Long id = 101L;
    MemberDto result2 = repository.findById(id);
    log.info("TTTT result2: " + result2.toString());
    assertThat(result2).isNotNull();
  }
}
