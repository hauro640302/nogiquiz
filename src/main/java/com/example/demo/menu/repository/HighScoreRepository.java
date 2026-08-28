package com.example.demo.menu.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.common.repository.BaseRepository;
import com.example.demo.common.repository.BinderConsumer;
import com.example.demo.common.repository.MapperFunction;
import com.example.demo.menu.domain.model.HighScoreDto;

@Repository
public class HighScoreRepository extends BaseRepository {

  private final String baseSql = """
      SELECT s.score, s.played_at, p.name
      FROM t_score s
      INNER JOIN m_player p ON s.player_id = p.id
      INNER JOIN m_category c ON s.category_id = c.id
      WHERE c.name = ? and s.is_active = ?
      ORDER BY s.score DESC, s.played_at DESC
      """;

  private MapperFunction<HighScoreDto> baseMapper = rs -> {
    return new HighScoreDto(
        rs.getInt("score"),
        rs.getString("name"),
        rs.getObject("played_at", LocalDateTime.class));

  };

  public List<HighScoreDto> getHighScore(String categoryName, boolean isActive) {

    BinderConsumer binder = ps -> {
      ps.setString(1, categoryName);
      ps.setBoolean(2, isActive);
    };

    return findAll(baseSql, binder, baseMapper);
  }

  public List<HighScoreDto> getHighScoreByPage(String categoryName, boolean isActive, int page,
      int size) {

    final String sql = baseSql + """
        LIMIT ? OFFSET ?
        """;

    BinderConsumer binder = ps -> {
      ps.setString(1, categoryName);
      ps.setBoolean(2, isActive);
      ps.setInt(3, size);
      ps.setInt(4, page * size);
    };

    return findAll(sql, binder, baseMapper);
  }

  public int count(String categoryName, boolean isActive) {

    final String sql = """
        SELECT count(*)
        FROM t_score s
        INNER JOIN m_player p ON s.player_id = p.id
        INNER JOIN m_category c ON s.category_id = c.id
        WHERE c.name = ? and s.is_active = ?
            """;

    BinderConsumer binder = ps -> {
      ps.setString(1, categoryName);
      ps.setBoolean(2, isActive);
    };

    return count(sql, binder);
  }


}
