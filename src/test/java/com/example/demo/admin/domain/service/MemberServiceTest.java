package com.example.demo.admin.domain.service;

import static org.assertj.core.api.Assertions.*;
import java.time.LocalDate;
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
public class MemberServiceTest {

  @Autowired
  private MemberService service;

  @Test
  public void test1() {
    MemberDto result = service.getMember(1L);
    log.info("TTTT result: " + result);
    assertThat(result.name()).isEqualTo("秋元真夏");
  }

  @Test
  public void test2() {
    MemberDto result = service.getMember(100L);
    log.info("TTTT result: " + result);
    assertThat(result.name()).isEqualTo("増田三莉音");
  }

  @Test
  public void test3() {
    MemberDto result = service.getMember(101L);
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(null);
  }

  @Test
  public void test4() {
    MemberDto result = service.getNewMember();
    log.info("TTTT result: " + result);
    assertThat(result).isNotEqualTo(null);
  }

  @Test
  public void test5() {
    String newName = "秋元真冬";
    MemberDto result = service.getMember(1L);
    log.info("TTTT result: " + result);
    assertThat(result.name()).isEqualTo("秋元真夏");

    MemberDto newMember = new MemberDto(result.id(), newName, result.birthday(),
        result.bloodtypeId(), result.prefectureId(), result.generationId(), result.isActive());
    boolean result2 = service.updateMember(newMember);
    assertThat(result2).isEqualTo(true);

    MemberDto result3 = service.getMember(1L);
    log.info("TTTT result3: " + result3.toString());
    assertThat(result3.name()).isEqualTo(newName);
  }

  @Test
  public void test6() {
    String newName = "正源司陽子";
    MemberDto newMember = new MemberDto(0L, newName, LocalDate.now(), 1L, 1L, 1L, true);
    boolean result = service.addMember(newMember);
    assertThat(result).isEqualTo(true);

    MemberDto result2 = service.getMember(101L);
    log.info("TTTT result2: " + result2.toString());
    assertThat(result2.name()).isEqualTo(newName);
  }

}
