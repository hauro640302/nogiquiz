package com.example.demo.common.repository;

import java.sql.SQLException;

@FunctionalInterface
public interface SqlFunction<T, R> {
  R apply(T t) throws SQLException;
}
