package com.example.demo.admin.domain.service;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.common.domain.model.MemberDetailDto;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class MemberDetailServiceTest {

  @Autowired
  private MemberDetailService service;
  
  @Test
  public void test1() {
    Pageable pageable = PageRequest.of(0, 10);
    Page<MemberDetailDto> result = service.getMemberDetail(pageable);
    log.info("TTTT result: " + result.getContent().toString());
    assertThat(result.getContent().size()).isEqualTo(10);
  }
  
  @Test
  public void test2() {
    Pageable pageable = PageRequest.of(9, 10);
    Page<MemberDetailDto> result = service.getMemberDetail(pageable);
    log.info("TTTT result: " + result.getContent().toString());
    assertThat(result.getContent().size()).isEqualTo(10);
  }
  
  @Test
  public void test3() {
    Pageable pageable = PageRequest.of(10, 10);
    Page<MemberDetailDto> result = service.getMemberDetail(pageable);
    log.info("TTTT result: " + result.getContent().toString());
    assertThat(result.getContent().size()).isEqualTo(0);
  }
}
