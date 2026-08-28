package com.example.demo.quiz.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.common.repository.BaseRepository;
import com.example.demo.common.repository.BinderConsumer;
import com.example.demo.common.repository.MapperFunction;
import com.example.demo.menu.domain.model.HighScoreDto;
import com.example.demo.quiz.domain.model.ScoreDto;

@Repository
public class ScoreRepository extends BaseRepository {

  public List<ScoreDto> getScore(String categoryName, boolean isActive) {

    final String sql = """
        SELECT s.id, s.is_active, s.category_id, s.score, s.player_id, s.played_at
        FROM t_score s
        INNER JOIN m_player p ON s.player_id = p.id
        INNER JOIN m_category c ON s.category_id = c.id
        WHERE c.name = ? and s.is_active = ?
        ORDER BY s.score DESC, s.played_at DESC
            """;

    BinderConsumer binder = ps -> {
      ps.setString(1, categoryName);
      ps.setBoolean(2, isActive);
    };

    MapperFunction<ScoreDto> mapper = rs -> {
      return new ScoreDto(
          rs.getLong("id"),
          rs.getBoolean("is_active"),
          rs.getLong("category_id"),
          rs.getInt("score"),
          rs.getLong("player_id"),
          rs.getObject("played_at", LocalDateTime.class));
    };

    return findAll(sql, binder, mapper);
  }

  public boolean addScore(HighScoreDto hs, String categoryName, boolean isActive) {

    final String sql = """
        INSERT INTO t_score (is_active, category_id, score, player_id,
            played_at)
        SELECT ?, c.id, ?, p.id, ?
        FROM m_player p
        LEFT JOIN m_category c ON c.name = ?
        WHERE p.name = ?
           """;

    BinderConsumer binder = ps -> {
      ps.setBoolean(1, isActive);
      ps.setInt(2, hs.score());
      ps.setObject(3, hs.playedAt());
      ps.setString(4, categoryName);
      ps.setString(5, hs.name());
    };

    return update(sql, binder);
  }

  public boolean updateScore(HighScoreDto hs, String categoryName, boolean isActive, long id) {


    final String sql = """
        UPDATE t_score s
        SET s.is_active = ?,
            s.score = ?,
            s.played_at = ?,
            s.player_id = (SELECT p.id FROM m_player p WHERE p.name = ?),
            s.category_id = (SELECT c.id FROM m_category c WHERE c.name = ?)
            WHERE s.id = ?
        """;

    BinderConsumer binder = ps -> {
      ps.setBoolean(1, isActive);
      ps.setInt(2, hs.score());
      ps.setObject(3, hs.playedAt());
      ps.setString(4, hs.name());
      ps.setString(5, categoryName);
      ps.setLong(6, id);
    };

    return update(sql, binder);
  }
}
