package com.example.demo.login.repository;

import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;
import com.example.demo.common.repository.BaseRepository;
import com.example.demo.common.repository.BinderConsumer;
import com.example.demo.common.repository.MapperFunction;
import com.example.demo.login.domain.model.PlayerDto;

@Repository
public class PlayerRepository extends BaseRepository {

  public PlayerDto findByPlayerName(String name) {

    final String sql = """
        SELECT *
        FROM m_player
        WHERE name = ?
            """;

    BinderConsumer binder = ps -> {
      ps.setString(1, name);
    };

    MapperFunction<PlayerDto> mapper = rs -> {
      return new PlayerDto(
          rs.getLong("id"),
          rs.getString("name"),
          rs.getString("password"),
          rs.getObject("loggedin_at", LocalDateTime.class),
          rs.getString("role"));
    };

    return findOne(sql, binder, mapper);

  }

  public boolean addPlayer(PlayerDto player) {

    final String sql = """
        INSERT INTO m_player (name, password, role, loggedin_at)
        VALUES (?, ?, ?, ?)
            """;

    BinderConsumer binder = ps -> {
      ps.setString(1, player.name());
      ps.setString(2, player.password());
      ps.setString(3, "GENERAL");
      ps.setTimestamp(4, null);
    };

    return update(sql, binder);

  }

  public boolean updatePlayerLoggedinAt(String name, LocalDateTime dt) {

    final String sql = """
        UPDATE m_player
        SET loggedin_at = ?
        WHERE name = ?
            """;

    BinderConsumer binder = ps -> {
      ps.setObject(1, dt);
      ps.setString(2, name);
    };

    return update(sql, binder);
  }

}
