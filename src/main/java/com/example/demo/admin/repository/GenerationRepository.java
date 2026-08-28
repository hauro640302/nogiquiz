package com.example.demo.admin.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.admin.domain.model.GenerationDto;
import com.example.demo.common.repository.BaseRepository;
import com.example.demo.common.repository.BinderConsumer;
import com.example.demo.common.repository.MapperFunction;

@Repository
public class GenerationRepository extends BaseRepository {

  public List<GenerationDto> findAll() {

    final String sql = """
        SELECT id, name FROM m_generation ORDER BY id
        """;

    BinderConsumer binder = ps -> {
    };

    MapperFunction<GenerationDto> mapper = rs -> {
      return new GenerationDto(
          rs.getLong("id"),
          rs.getString("name"));
    };

    return findAll(sql, binder, mapper);
  }

}
