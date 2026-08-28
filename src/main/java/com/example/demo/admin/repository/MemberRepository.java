package com.example.demo.admin.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.admin.domain.model.MemberDto;
import com.example.demo.common.repository.BaseRepository;
import com.example.demo.common.repository.BinderConsumer;
import com.example.demo.common.repository.MapperFunction;

@Repository
public class MemberRepository extends BaseRepository {

  final String baseSql = """
      SELECT
      id, name, birthday, bloodtype_id, prefecture_id, generation_id, is_active
      FROM m_member
      """;

  private MapperFunction<MemberDto> baseMapper = rs -> {

    return new MemberDto(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getDate("birthday").toLocalDate(),
        rs.getLong("bloodtype_id"),
        rs.getLong("prefecture_id"),
        rs.getLong("generation_id"),
        rs.getBoolean("is_active"));
  };

  public MemberDto findById(Long id) {

    final String sql = baseSql + " WHERE id = ?";

    BinderConsumer binder = ps -> {
      ps.setLong(1, id);
    };

    return findOne(sql, binder, baseMapper);
  }

  public List<MemberDto> findAll() {

    final String sql = baseSql + " ORDER BY id";

    BinderConsumer binder = ps -> {
    };

    return findAll(sql, binder, baseMapper);
  }

  public boolean update(MemberDto member) {

    final String sql = """
        UPDATE m_member
        SET name = ?, birthday = ?, bloodtype_id = ?, prefecture_id = ?,
            generation_id = ?, is_active = ?
        WHERE id = ?
           """;

    BinderConsumer binder = ps -> {
      ps.setString(1, member.name());
      ps.setObject(2, member.birthday());
      ps.setLong(3, member.bloodtypeId());
      ps.setLong(4, member.prefectureId());
      ps.setLong(5, member.generationId());
      ps.setBoolean(6, member.isActive());
      ps.setLong(7, member.id());
    };

    return update(sql, binder);
  }

  public boolean add(MemberDto member) {

    final String sql = """
        INSERT INTO m_member
            (name, birthday, bloodtype_id, prefecture_id, generation_id, is_active)
        VALUES (?, ?, ?, ?, ?, ?)
            """;

    BinderConsumer binder = ps -> {
      ps.setString(1, member.name());
      ps.setObject(2, member.birthday());
      ps.setLong(3, member.bloodtypeId());
      ps.setLong(4, member.prefectureId());
      ps.setLong(5, member.generationId());
      ps.setBoolean(6, member.isActive());
    };

    return update(sql, binder);
  }
}
