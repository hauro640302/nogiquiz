package com.example.demo.admin.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.admin.domain.model.BloodtypeDto;
import com.example.demo.common.repository.BaseRepository;
import com.example.demo.common.repository.BinderConsumer;
import com.example.demo.common.repository.MapperFunction;

@Repository
public class BloodtypeRepository extends BaseRepository {

  public List<BloodtypeDto> findAll() {

    final String sql = """
        SELECT id, name FROM m_bloodtype ORDER BY id
        """;

    BinderConsumer binder = ps -> {
      ;
    };

    MapperFunction<BloodtypeDto> mapper = rs -> {
      return new BloodtypeDto(
          rs.getLong("id"),
          rs.getString("name"));
    };

    return findAll(sql, binder, mapper);
  }
}
