package com.example.demo.common.repository;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.common.domain.model.MemberDetailDto;

@Repository
public class MemberDetailRepository extends BaseRepository {

  final String baseSql = """
      SELECT m.id, m.name, m.birthday, b.name as bloodtype, p.name as prefecture,
               g.name as generation, m.is_active
      FROM m_member m
      INNER JOIN m_prefecture p ON m.prefecture_id = p.id
      INNER JOIN m_bloodtype b ON m.bloodtype_id = b.id
      INNER JOIN m_generation g ON m.generation_id = g.id
      """;

  private BinderConsumer baseBinder = ps -> {
    ;
  };

  private MapperFunction<MemberDetailDto> baseMapper = rs -> {

    LocalDate today = LocalDate.now();
    DateTimeFormatter formatterYMD = DateTimeFormatter.ofPattern("yyyy年M月d日");
    DateTimeFormatter formatterMD = DateTimeFormatter.ofPattern("M月d日");
    LocalDate ld = rs.getDate("birthday").toLocalDate();
    String ymd = ld.format(formatterYMD);
    String md = ld.format(formatterMD);
    String age = Period.between(ld, today).getYears() + " 歳";

    return new MemberDetailDto(
        rs.getLong("id"),
        rs.getString("name"),
        ymd,
        md,
        age,
        rs.getString("bloodtype"),
        rs.getString("prefecture"),
        rs.getString("generation"),
        rs.getBoolean("is_active"),
        rs.getBoolean("is_active") ? "現役生" : "卒業生");
  };

  public List<MemberDetailDto> findAll() {
    final String sql = baseSql + " ORDER BY m.id";
    return findAll(sql, baseBinder, baseMapper);
  }

  public List<MemberDetailDto> findAllByIsActiveTrue() {
    final String sql = baseSql + " WHERE m.is_active = true";
    return findAll(sql, baseBinder, baseMapper);
  }

  public int count() {
    return count(baseSql, baseBinder);
  }

  public List<MemberDetailDto> findAllByPage(int page, int size) {

    final String sql = baseSql + " " + """
        ORDER BY m.id
        LIMIT ? OFFSET ?
        """;

    BinderConsumer binder = ps -> {
      ps.setInt(1, size);
      ps.setInt(2, page * size);
    };

    return findAll(sql, binder, baseMapper);
  }
}
