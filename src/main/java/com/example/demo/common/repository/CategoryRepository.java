package com.example.demo.common.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.common.domain.model.CategoryDto;

@Repository
public class CategoryRepository extends BaseRepository {

  public List<CategoryDto> findAll() {

    final String sql = """
        SELECT id, name FROM m_category ORDER BY id
        """;

    BinderConsumer binder = ps -> {
    };

    MapperFunction<CategoryDto> mapper = rs -> {
      return new CategoryDto(
          rs.getLong("id"),
          rs.getString("name"));
    };

    return findAll(sql, binder, mapper);
  }
}
