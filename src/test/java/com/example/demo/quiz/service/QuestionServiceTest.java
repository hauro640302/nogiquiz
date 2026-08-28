package com.example.demo.quiz.service;

import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.quiz.domain.model.QuestionDto;
import com.example.demo.quiz.domain.service.QuestionService;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class QuestionServiceTest {

  @Autowired
  private QuestionService service;

  @Test
  public void test1() {
    List<QuestionDto> result = service.makeQuiz("出身都道府県", true);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test2() {
    List<QuestionDto> result = service.makeQuiz("出身都道府県", false);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test3() {
    List<QuestionDto> result = service.makeQuiz("誕生日", true);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test4() {
    List<QuestionDto> result = service.makeQuiz("誕生日", false);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test5() {
    List<QuestionDto> result = service.makeQuiz("年齢", true);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test6() {
    List<QuestionDto> result = service.makeQuiz("年齢", false);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test7() {
    List<QuestionDto> result = service.makeQuiz("血液型", true);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test8() {
    List<QuestionDto> result = service.makeQuiz("血液型", false);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test9() {
    List<QuestionDto> result = service.makeQuiz("世代（期生）", true);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test10() {
    List<QuestionDto> result = service.makeQuiz("世代（期生）", false);
    log.info("TTTT result: " + result);
    assertThat(result.size()).isEqualTo(10);
  }

  @Test
  public void test11() {
    List<String> answer =
        List.of("大阪府", "神奈川県", "愛知県", "秋田県", "兵庫県",
            "沖縄県", "北海道", "千葉県", "北海道", "京都府");
    List<String> corect =
        List.of("大阪府", "神奈川県", "愛知県", "秋田県", "兵庫県",
            "沖縄県", "北海道", "千葉県", "北海道", "京都府");   
    int result = service.scoring(corect, answer);
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(100);
  }
  
  @Test
  public void test12() {
    List<String> answer =
        List.of("北海道", "神奈川県", "愛知県", "秋田県", "兵庫県",
            "沖縄県", "北海道", "千葉県", "北海道", "京都府");
    List<String> corect =
        List.of("大阪府", "神奈川県", "愛知県", "秋田県", "兵庫県",
            "沖縄県", "北海道", "千葉県", "北海道", "京都府");   
    int result = service.scoring(corect, answer);
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(90);
  }
  
  @Test
  public void test13() {
    List<String> answer =
        List.of("大阪府", "神奈川県", "愛知県", "秋田県", "兵庫県",
            "沖縄県", "北海道");
    List<String> corect =
        List.of("大阪府", "神奈川県", "愛知県", "秋田県", "兵庫県",
            "沖縄県", "北海道", "千葉県", "北海道", "京都府");   
    int result = service.scoring(corect, answer);
    log.info("TTTT result: " + result);
    assertThat(result).isEqualTo(70);
  }

}
