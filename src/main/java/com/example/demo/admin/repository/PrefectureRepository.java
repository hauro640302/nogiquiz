package com.example.demo.admin.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.admin.domain.model.PrefectureDto;
import com.example.demo.common.repository.BaseRepository;
import com.example.demo.common.repository.BinderConsumer;
import com.example.demo.common.repository.MapperFunction;

@Repository
public class PrefectureRepository extends BaseRepository {

  public List<PrefectureDto> findAll() {

    final String sql = """
        SELECT id, name FROM m_prefecture ORDER BY id
        """;

    BinderConsumer binder = ps -> {
    };

    MapperFunction<PrefectureDto> mapper = rs -> {
      return new PrefectureDto(
          rs.getLong("id"),
          rs.getString("name"));
    };

    return findAll(sql, binder, mapper);
  }
}
