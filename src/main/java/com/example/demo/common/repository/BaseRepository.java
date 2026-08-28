package com.example.demo.common.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BaseRepository {

  @Autowired
  protected DataSource dataSource;

  protected <R> R findOne(String sql, BinderConsumer binder,
      MapperFunction<R> rowMapper) {

    R result = null;

    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      binder.accept(ps);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          result = rowMapper.apply(rs);
        }
      }

    }
    catch (SQLException e) {
      throw new RuntimeException("データベースエラーが発生しました", e);
    }

    return result;
  }

  protected <R> List<R> findAll(String sql, BinderConsumer binder,
      MapperFunction<R> rowMapper) {

    List<R> result = new ArrayList<>();

    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      binder.accept(ps);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          result.add(rowMapper.apply(rs));
        }
      }

    }
    catch (SQLException e) {
      throw new RuntimeException("データベースエラーが発生しました", e);
    }

    return result;
  }

  public boolean update(String sql, BinderConsumer binder) {

    boolean result = false;

    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      binder.accept(ps);

      result = ps.executeUpdate() > 0;

    }
    catch (SQLException e) {
      throw new RuntimeException("データベースエラーが発生しました", e);
    }

    return result;
  }

  public int count(String sql, BinderConsumer binder) {

    int ret = 0;

    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      binder.accept(ps);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ret = rs.getInt(1);
        }
      }

    }
    catch (SQLException e) {
      throw new RuntimeException("データベースエラーが発生しました", e);
    }

    return ret;
  }
}

