package com.example.demo.quiz.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import com.example.demo.common.domain.model.MemberDetailDto;
import com.example.demo.common.repository.MemberDetailRepository;
import com.example.demo.quiz.domain.model.QuestionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

  private final MemberDetailRepository repository;
  // 出題数
  private final int N = 10;
  // 四者択一
  private final int S = 4;

  public List<QuestionDto> makeQuiz(String categoryName, boolean isActive) {
    // 対象メンバ詳細情報を取得
    List<MemberDetailDto> memberData =
        isActive ? repository.findAllByIsActiveTrue() : repository.findAll();

    // メンバの氏名とカテゴリに対応したデータを取得するマッパー
    Function<MemberDetailDto, String> dataMapper = switch (categoryName) {
      case "出身都道府県" -> MemberDetailDto::prefecture;
      case "誕生日" -> MemberDetailDto::birthdayMD;
      case "年齢" -> MemberDetailDto::age;
      case "血液型" -> MemberDetailDto::bloodtype;
      case "世代（期生）" -> MemberDetailDto::generation;
      default -> (md) -> "";
    };

    // 全メンバのデータから重複を除き、選択肢プールを作成する
    List<String> choicePool = memberData.stream().map(dataMapper).distinct().toList();

    // 対象メンバからランダムにN名を選ぶ
    List<MemberDetailDto> selected = new ArrayList<>(memberData);
    Collections.shuffle(selected);
    selected = selected.subList(0, N);

    // 問題を作成する
    return selected.stream()
        .map(md -> {
          String correct = dataMapper.apply(md);
          return new QuestionDto(md.name(), correct, makeChoices(correct, choicePool));
        })
        .toList();
  }

  private List<String> makeChoices(String correct, List<String> choicePool) {
    // 選択肢プールから正解を除く
    List<String> wrongPool = choicePool.stream()
        .filter(c -> !correct.equals(c))
        .collect(Collectors.toList());

    // シャッフル
    Collections.shuffle(wrongPool);

    // S-1個の誤答を選ぶ
    List<String> choices = wrongPool.subList(0, S - 1);

    // 正解を加えS個にする
    choices.add(correct);

    // シャッフル
    Collections.shuffle(choices);

    return choices;
  }

  public int scoring(List<String> corrects, List<String> answers) {
    return (answers == null || corrects == null)
        ? 0
        : (int) IntStream.range(0, answers.size())
            .filter(i -> corrects.get(i).equals(answers.get(i)))
            .count() * (100 / N);
  }
}
